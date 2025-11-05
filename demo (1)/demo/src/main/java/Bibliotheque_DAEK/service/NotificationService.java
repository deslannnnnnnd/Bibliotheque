package Bibliotheque_DAEK.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Bibliotheque_DAEK.Model.Book;
import Bibliotheque_DAEK.Model.Emprunt;
import Bibliotheque_DAEK.Model.Notification;
import Bibliotheque_DAEK.Model.User;
import Bibliotheque_DAEK.Repository.NotificationRepository;
import Bibliotheque_DAEK.Repository.UserRepository;

@Service
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * BE-NOT-04 : Récupérer les notifications d'un utilisateur
     */
    public List<Notification> getNotificationsByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return notificationRepository.findByUserOrderByDateEnvoiDesc(user);
    }
    
    /**
     * Récupérer les notifications non lues
     */
    public List<Notification> getNotificationsNonLues(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return notificationRepository.findByUserAndLue(user, false);
    }
    
    /**
     * Compter les notifications non lues
     */
    public Long countNotificationsNonLues(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return notificationRepository.countByUserAndLue(user, false);
    }
    
    /**
     * BE-NOT-05 : Marquer une notification comme lue
     */
    @Transactional
    public Notification marquerCommeLue(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
        
        notification.marquerCommeLue();
        return notificationRepository.save(notification);
    }
    
    /**
     * Marquer toutes les notifications comme lues
     */
    @Transactional
    public void marquerToutesCommeLues(Long userId) {
        List<Notification> notifications = getNotificationsNonLues(userId);
        for (Notification notification : notifications) {
            notification.marquerCommeLue();
        }
        notificationRepository.saveAll(notifications);
    }
    
    /**
     * Créer une notification d'emprunt
     */
    @Transactional
    public Notification creerNotificationEmprunt(User user, Book book, Emprunt emprunt) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType("EMPRUNT");
        notification.setTitre("Emprunt confirmé");
        notification.setMessage("Vous avez emprunté le livre \"" + book.getTitre() + "\". Date de retour prévue : " + emprunt.getDateRetourPrevue());
        notification.setEmprunt(emprunt);
        return notificationRepository.save(notification);
    }
    
    /**
     * BE-NOT-02 : Créer une notification de rappel de retour
     */
    @Transactional
    public Notification creerNotificationRappelRetour(User user, Book book, LocalDate dateRetour) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType("RAPPEL_RETOUR");
        notification.setTitre("Rappel : Date de retour proche");
        notification.setMessage("Le livre \"" + book.getTitre() + "\" doit être retourné le " + dateRetour + ".");
        return notificationRepository.save(notification);
    }
    
    /**
     * BE-NOT-03 : Créer une notification de retard
     */
    @Transactional
    public Notification creerNotificationRetard(User user, Book book, long joursRetard) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType("RETARD");
        notification.setTitre("⚠️ Livre en retard");
        notification.setMessage("Le livre \"" + book.getTitre() + "\" est en retard de " + joursRetard + " jour(s). Des amendes s'appliquent.");
        return notificationRepository.save(notification);
    }
    
    /**
     * Créer une notification de prolongation
     */
    @Transactional
    public Notification creerNotificationProlongation(User user, Book book, LocalDate nouvelleDateRetour) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType("PROLONGATION");
        notification.setTitre("Emprunt prolongé");
        notification.setMessage("L'emprunt du livre \"" + book.getTitre() + "\" a été prolongé. Nouvelle date de retour : " + nouvelleDateRetour);
        return notificationRepository.save(notification);
    }
    
    /**
     * Créer une notification d'amende
     */
    @Transactional
    public Notification creerNotificationAmende(User user, BigDecimal montant, long joursRetard) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType("AMENDE");
        notification.setTitre("Amende appliquée");
        notification.setMessage("Une amende de " + montant + " € a été appliquée pour un retard de " + joursRetard + " jour(s).");
        return notificationRepository.save(notification);
    }
    
    /**
     * Créer une notification de paiement d'amende
     */
    @Transactional
    public Notification creerNotificationPaiementAmende(User user, BigDecimal montant) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType("PAIEMENT");
        notification.setTitre("Paiement confirmé");
        notification.setMessage("Votre paiement de " + montant + " € a été enregistré avec succès.");
        return notificationRepository.save(notification);
    }
}

