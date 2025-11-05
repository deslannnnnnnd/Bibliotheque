package Bibliotheque_DAEK.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Bibliotheque_DAEK.Model.Book;
import Bibliotheque_DAEK.Model.Emprunt;
import Bibliotheque_DAEK.Model.User;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    
    // Recherche par utilisateur
    List<Emprunt> findByUser(User user);
    List<Emprunt> findByUserAndStatut(User user, String statut);
    List<Emprunt> findByUserOrderByDateEmpruntDesc(User user);
    
    // Recherche par livre
    List<Emprunt> findByBook(Book book);
    Optional<Emprunt> findByBookAndStatut(Book book, String statut);
    
    // Recherche par statut
    List<Emprunt> findByStatut(String statut);
    
    // Recherche par date
    List<Emprunt> findByDateRetourPrevueBefore(LocalDate date);
    List<Emprunt> findByDateRetourPrevueBeforeAndStatut(LocalDate date, String statut);
    
    // Emprunts en retard
    @Query("SELECT e FROM Emprunt e WHERE e.statut = 'EN_COURS' AND e.dateRetourPrevue < CURRENT_DATE")
    List<Emprunt> findEmpruntsEnRetard();
    
    // Compter les emprunts actifs d'un utilisateur
    Long countByUserAndStatut(User user, String statut);
}

