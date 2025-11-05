package Bibliotheque_DAEK.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "emprunts")
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @Column(nullable = false)
    private LocalDate dateEmprunt;
    
    private LocalDate dateRetourEffective;
    
    @Column(nullable = false)
    private LocalDate dateRetourPrevue;
    
    @Column(nullable = false)
    private String statut; // EN_COURS, RETOURNE, EN_RETARD
    
    @Column(precision = 10, scale = 2)
    private BigDecimal amende;
    
    @Column(name = "nombre_prolongations")
    private Integer nombreProlongations = 0;
    
    @Column(name = "prolongations_restantes")
    private Integer prolongationsRestantes = 2; // Max 2 prolongations
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }
    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }
    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }
    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
    public BigDecimal getAmende() {
        return amende;
    }
    
    public void setAmende(BigDecimal amende) {
        this.amende = amende;
    }
    
    public Integer getNombreProlongations() {
        return nombreProlongations;
    }
    
    public void setNombreProlongations(Integer nombreProlongations) {
        this.nombreProlongations = nombreProlongations;
    }
    
    public Integer getProlongationsRestantes() {
        return prolongationsRestantes;
    }
    
    public void setProlongationsRestantes(Integer prolongationsRestantes) {
        this.prolongationsRestantes = prolongationsRestantes;
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
    public boolean peutEtreProlonge() {
        return prolongationsRestantes > 0 && "EN_COURS".equals(statut);
    }
    
    public boolean estEnRetard() {
        if (dateRetourEffective != null) {
            return false;
        }
        return LocalDate.now().isAfter(dateRetourPrevue);
    }
}
