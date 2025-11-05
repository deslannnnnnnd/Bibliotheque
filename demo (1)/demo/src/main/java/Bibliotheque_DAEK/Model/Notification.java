package Bibliotheque_DAEK.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String type; // RAPPEL_RETOUR, RETARD, AMENDE, RESERVATION_DISPONIBLE
    
    @Column(nullable = false)
    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    @Column(nullable = false)
    private Boolean lue = false;
    
    @Column(name = "date_envoi", nullable = false)
    private LocalDateTime dateEnvoi;
    
    @Column(name = "date_lecture")
    private LocalDateTime dateLecture;
    
    // Référence optionnelle à l'emprunt concerné
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emprunt_id")
    private Emprunt emprunt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (dateEnvoi == null) {
            dateEnvoi = LocalDateTime.now();
        }
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Boolean getLue() {
        return lue;
    }
    
    public void setLue(Boolean lue) {
        this.lue = lue;
    }
    
    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }
    
    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
    
    public LocalDateTime getDateLecture() {
        return dateLecture;
    }
    
    public void setDateLecture(LocalDateTime dateLecture) {
        this.dateLecture = dateLecture;
    }
    
    public Emprunt getEmprunt() {
        return emprunt;
    }
    
    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // Méthodes utilitaires
    public void marquerCommeLue() {
        this.lue = true;
        this.dateLecture = LocalDateTime.now();
    }
}

