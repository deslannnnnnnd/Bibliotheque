package Bibliotheque_DAEK.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Bibliotheque_DAEK.Model.Reservation;
import Bibliotheque_DAEK.Repository.ReservationRepository;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    // Récupérer toutes les réservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> reservations = reservationRepository.findAll();
            if (reservations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Récupérer une réservation par ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id) {
        Optional<Reservation> reservationData = reservationRepository.findById(id);
        if (reservationData.isPresent()) {
            return new ResponseEntity<>(reservationData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Créer une nouvelle réservation
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation newReservation = reservationRepository.save(reservation);
            return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mettre à jour une réservation
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
        Optional<Reservation> reservationData = reservationRepository.findById(id);
        if (reservationData.isPresent()) {
            Reservation existingReservation = reservationData.get();
            existingReservation.setUserId(reservation.getUserId());
            existingReservation.setBookId(reservation.getBookId());
            existingReservation.setDateReservation(reservation.getDateReservation());
            existingReservation.setDateExpiration(reservation.getDateExpiration());
            existingReservation.setStatus(reservation.getStatus());
            existingReservation.setDateNotification(reservation.getDateNotification());
            return new ResponseEntity<>(reservationRepository.save(existingReservation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer une réservation
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Supprimer toutes les réservations
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllReservations() {
        try {
            reservationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

