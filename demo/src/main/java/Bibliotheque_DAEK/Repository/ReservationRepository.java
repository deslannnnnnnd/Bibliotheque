package Bibliotheque_DAEK.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Bibliotheque_DAEK.Model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{

    List<Reservation>findByUserId(Long userId);
    List<Reservation>findBookId(Long bookId);
    List<Reservation>fingByStatut(Long statut);
    List<Reservation>findByDateRetourPrevueBefore(LocalDate date);
     Optional<Reservation> findByBookIdAndStatut(Long bookId, String statut);
}
