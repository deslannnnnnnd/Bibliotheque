package Bibliotheque_DAEK.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

 
public class Reservation {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
 
private Long id;
private String userId;
private String bookId;
private LocalDate dateReservation;
private LocalDate dateExpiration;
private String status;
private LocalDate dateNotification;
public Long getId() {
    return id;
}
public void setId(Long id) {
    this.id = id;
}
public String getUserId() {
    return userId;
}
public void setUserId(String userId) {
    this.userId = userId;
}
public String getBookId() {
    return bookId;
}
public void setBookId(String bookId) {
    this.bookId = bookId;
}
public LocalDate getDateReservation() {
    return dateReservation;
}
public void setDateReservation(LocalDate dateReservation) {
    this.dateReservation = dateReservation;
}
public LocalDate getDateExpiration() {
    return dateExpiration;
}
public void setDateExpiration(LocalDate dateExpiration) {
    this.dateExpiration = dateExpiration;
}
public String getStatus() {
    return status;
}
public void setStatus(String status) {
    this.status = status;
}
public LocalDate getDateNotification() {
    return dateNotification;
}
public void setDateNotification(LocalDate dateNotification) {
    this.dateNotification = dateNotification;
}

    
}
