package Bibliotheque_DAEK.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Bibliotheque_DAEK.Model.Fine;
import Bibliotheque_DAEK.Model.User;
import Bibliotheque_DAEK.Repository.FineRepository;
import Bibliotheque_DAEK.Repository.UserRepository;

@Service
public class FineService {
    
    @Autowired
    private FineRepository fineRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    /**
     * BE-AMN-01 : Récupérer les amendes d'un utilisateur
     */
    public List<Fine> getFinesByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return fineRepository.findByUser(user);
    }
    
    /**
     * Récupérer les amendes impayées d'un utilisateur
     */
    public List<Fine> getFinesImpayeesByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return fineRepository.findByUserAndStatut(user, "IMPAYEE");
    }
    
    /**
     * Récupérer toutes les amendes impayées (bibliothécaire)
     */
    public List<Fine> getAllFinesImpayees() {
        return fineRepository.findByStatut("IMPAYEE");
    }
    
    /**
     * BE-AMN-04 : Payer une amende
     */
    @Transactional
    public Fine payerAmende(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
            .orElseThrow(() -> new RuntimeException("Amende non trouvée"));
        
        if ("PAYEE".equals(fine.getStatut())) {
            throw new RuntimeException("Cette amende a déjà été payée");
        }
        
        fine.setStatut("PAYEE");
        fine.setDatePaiement(LocalDate.now());
        
        Fine savedFine = fineRepository.save(fine);
        
        // Notification de paiement
        notificationService.creerNotificationPaiementAmende(fine.getUser(), fine.getMontant());
        
        return savedFine;
    }
    
    /**
     * Annuler une amende (bibliothécaire)
     */
    @Transactional
    public Fine annulerAmende(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
            .orElseThrow(() -> new RuntimeException("Amende non trouvée"));
        
        if ("PAYEE".equals(fine.getStatut())) {
            throw new RuntimeException("Impossible d'annuler une amende déjà payée");
        }
        
        fine.setStatut("ANNULEE");
        return fineRepository.save(fine);
    }
}

