package Bibliotheque_DAEK.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "fines")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emprunt_id", nullable = false)
    private Emprunt emprunt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;
    
    @Column(nullable = false)
    private String statut; // IMPAYEE, PAYEE, ANNULEE
    
    @Column(nullable = false)
    private LocalDate dateCreation;
    
    private LocalDate datePaiement;
    
    @Column(columnDefinition = "TEXT")
    private String raison; // Raison de l'amende
    
    @Column(name = "nombre_jours_retard")
    private Integer nombreJoursRetard;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (dateCreation == null) {
            dateCreation = LocalDate.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Emprunt getEmprunt() {
        return emprunt;
    }
    
    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public BigDecimal getMontant() {
        return montant;
    }
    
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
    
    public String getStatut() {
        return statut;
    }
    
    public void setStatut(String statut) {
        this.statut = statut;
    }
    
    public LocalDate getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public LocalDate getDatePaiement() {
        return datePaiement;
    }
    
    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }
    
    public String getRaison() {
        return raison;
    }
    
    public void setRaison(String raison) {
        this.raison = raison;
    }
    
    public Integer getNombreJoursRetard() {
        return nombreJoursRetard;
    }
    
    public void setNombreJoursRetard(Integer nombreJoursRetard) {
        this.nombreJoursRetard = nombreJoursRetard;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Méthodes utilitaires
    public boolean estPayee() {
        return "PAYEE".equals(statut);
    }
}

