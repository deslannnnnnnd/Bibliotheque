package Bibliotheque_DAEK.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Bibliotheque_DAEK.Model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{

    List<Reservation> findByUserId(String userId);
    List<Reservation> findByBookId(String bookId);
    List<Reservation> findByStatus(String status);
    List<Reservation> findByDateExpirationBefore(LocalDate date);
    Optional<Reservation> findByBookIdAndStatus(String bookId, String status);
}
