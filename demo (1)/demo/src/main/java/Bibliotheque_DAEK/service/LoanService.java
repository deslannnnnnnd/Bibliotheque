package Bibliotheque_DAEK.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Bibliotheque_DAEK.Model.Book;
import Bibliotheque_DAEK.Model.Emprunt;
import Bibliotheque_DAEK.Model.Fine;
import Bibliotheque_DAEK.Model.User;
import Bibliotheque_DAEK.Repository.BookRepository;
import Bibliotheque_DAEK.Repository.EmpruntRepository;
import Bibliotheque_DAEK.Repository.FineRepository;
import Bibliotheque_DAEK.Repository.UserRepository;

@Service
public class LoanService {
    
    @Autowired
    private EmpruntRepository empruntRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FineRepository fineRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    // Constantes
    private static final int DUREE_EMPRUNT_JOURS = 14; // 2 semaines
    private static final int DUREE_PROLONGATION_JOURS = 7; // 1 semaine
    private static final int MAX_EMPRUNTS_SIMULTANEUS = 5;
    private static final int MAX_PROLONGATIONS = 2;
    private static final BigDecimal AMENDE_PAR_JOUR = new BigDecimal("1.00"); // 1€ par jour
    
    /**
     * BE-EMP-01 & BE-EMP-02 : Créer un nouvel emprunt avec vérification de disponibilité
     */
    @Transactional
    public Emprunt emprunterLivre(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        
        // BE-EMP-02 : Vérifier la disponibilité (au moins une copie disponible)
        if (!book.aDesCopiesdisponibles()) {
            throw new RuntimeException("Aucune copie disponible pour ce livre");
        }
        
        // BE-EMP-03 : Valider les règles d'emprunt
        validateEmprunt(user);
        
        // Créer l'emprunt
        Emprunt emprunt = new Emprunt();
        emprunt.setUser(user);
        emprunt.setBook(book);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(calculerDateRetourPrevue(LocalDate.now())); // BE-EMP-04
        emprunt.setStatut("EN_COURS");
        emprunt.setNombreProlongations(0);
        emprunt.setProlongationsRestantes(MAX_PROLONGATIONS);
        
        // BE-EMP-05 : Décrémenter le nombre de copies disponibles
        book.emprunterUneCopie();
        bookRepository.save(book);
        
        Emprunt savedEmprunt = empruntRepository.save(emprunt);
        
        // Envoyer une notification
        notificationService.creerNotificationEmprunt(user, book, savedEmprunt);
        
        return savedEmprunt;
    }
    
    /**
     * BE-EMP-03 : Validation des règles d'emprunt
     */
    private void validateEmprunt(User user) {
        // Vérifier le nombre max d'emprunts
        Long nombreEmpruntsActifs = empruntRepository.countByUserAndStatut(user, "EN_COURS");
        if (nombreEmpruntsActifs >= MAX_EMPRUNTS_SIMULTANEUS) {
            throw new RuntimeException("Vous avez atteint le nombre maximum d'emprunts simultanés (" + MAX_EMPRUNTS_SIMULTANEUS + ")");
        }
        
        // Vérifier s'il y a des amendes impayées
        List<Fine> amendesImpayees = fineRepository.findByUserAndStatut(user, "IMPAYEE");
        if (!amendesImpayees.isEmpty()) {
            throw new RuntimeException("Vous avez des amendes impayées. Veuillez les régler avant d'emprunter un nouveau livre");
        }
    }
    
    /**
     * BE-EMP-04 : Calculer la date de retour prévue
     */
    private LocalDate calculerDateRetourPrevue(LocalDate dateEmprunt) {
        return dateEmprunt.plusDays(DUREE_EMPRUNT_JOURS);
    }
    
    /**
     * BE-PRO-01 à BE-PRO-05 : Prolonger un emprunt
     */
    @Transactional
    public Emprunt prolongerEmprunt(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
            .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));
        
        // BE-PRO-02 : Vérifier les conditions
        if (!"EN_COURS".equals(emprunt.getStatut())) {
            throw new RuntimeException("Cet emprunt ne peut pas être prolongé (statut: " + emprunt.getStatut() + ")");
        }
        
        // BE-PRO-05 : Limiter le nombre de prolongations
        if (emprunt.getProlongationsRestantes() <= 0) {
            throw new RuntimeException("Nombre maximum de prolongations atteint");
        }
        
        // Vérifier que le livre n'est pas en retard
        if (emprunt.estEnRetard()) {
            throw new RuntimeException("Impossible de prolonger un emprunt en retard");
        }
        
        // BE-PRO-03 : Vérifier l'absence de réservation (à implémenter si module réservation existe)
        
        // BE-PRO-04 : Calculer la nouvelle date
        LocalDate nouvelleDateRetour = emprunt.getDateRetourPrevue().plusDays(DUREE_PROLONGATION_JOURS);
        emprunt.setDateRetourPrevue(nouvelleDateRetour);
        emprunt.setNombreProlongations(emprunt.getNombreProlongations() + 1);
        emprunt.setProlongationsRestantes(emprunt.getProlongationsRestantes() - 1);
        
        Emprunt savedEmprunt = empruntRepository.save(emprunt);
        
        // Notification
        notificationService.creerNotificationProlongation(emprunt.getUser(), emprunt.getBook(), nouvelleDateRetour);
        
        return savedEmprunt;
    }
    
    /**
     * BE-RET-01 à BE-RET-05 : Retourner un livre
     */
    @Transactional
    public Emprunt retournerLivre(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
            .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));
        
        if (!"EN_COURS".equals(emprunt.getStatut())) {
            throw new RuntimeException("Cet emprunt est déjà retourné");
        }
        
        LocalDate dateRetourEffective = LocalDate.now();
        emprunt.setDateRetourEffective(dateRetourEffective);
        emprunt.setStatut("RETOURNE");
        
        // BE-RET-02 : Calculer les amendes si retard
        if (dateRetourEffective.isAfter(emprunt.getDateRetourPrevue())) {
            long joursRetard = ChronoUnit.DAYS.between(emprunt.getDateRetourPrevue(), dateRetourEffective);
            BigDecimal montantAmende = AMENDE_PAR_JOUR.multiply(new BigDecimal(joursRetard));
            
            // Créer l'amende
            Fine fine = new Fine();
            fine.setEmprunt(emprunt);
            fine.setUser(emprunt.getUser());
            fine.setMontant(montantAmende);
            fine.setStatut("IMPAYEE");
            fine.setRaison("Retard de " + joursRetard + " jour(s)");
            fine.setNombreJoursRetard((int) joursRetard);
            fineRepository.save(fine);
            
            emprunt.setAmende(montantAmende);
            
            // Notification d'amende
            notificationService.creerNotificationAmende(emprunt.getUser(), montantAmende, joursRetard);
        }
        
        // BE-RET-03 : Incrémenter le nombre de copies disponibles
        Book book = emprunt.getBook();
        book.retournerUneCopie();
        bookRepository.save(book);
        
        // BE-RET-04 : Notifier si réservation (à implémenter)
        
        return empruntRepository.save(emprunt);
    }
    
    /**
     * BE-CON-01 : Récupérer les emprunts d'un utilisateur
     */
    public List<Emprunt> getEmpruntsByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return empruntRepository.findByUserOrderByDateEmpruntDesc(user);
    }
    
    /**
     * BE-CON-02 : Récupérer tous les emprunts (bibliothécaire)
     */
    public List<Emprunt> getAllEmprunts() {
        return empruntRepository.findAll();
    }
    
    /**
     * BE-CON-03 : Filtrer par statut
     */
    public List<Emprunt> getEmpruntsByStatut(String statut) {
        return empruntRepository.findByStatut(statut);
    }
    
    /**
     * Récupérer les emprunts en retard
     */
    public List<Emprunt> getEmpruntsEnRetard() {
        return empruntRepository.findEmpruntsEnRetard();
    }
}

