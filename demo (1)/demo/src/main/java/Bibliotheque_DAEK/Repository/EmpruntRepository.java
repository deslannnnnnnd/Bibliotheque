package Bibliotheque_DAEK.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Bibliotheque_DAEK.Model.Emprunt;


public interface EmpruntRepository extends JpaRepository<Emprunt ,Long>{

List<Emprunt> findByUserId(String userId);
List<Emprunt> findByBookId(String bookId);
List<Emprunt> findByStatut(String statut);
List<Emprunt> findByDateRetourPrevueBefore(LocalDate date);
Optional<Emprunt> findByBookIdAndStatut(String bookId, String statut);

}

