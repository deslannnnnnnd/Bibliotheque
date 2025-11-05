package bibliotheque.collegeuniversel.bibliotheque;

import java.math.BigDecimal;

public class Fine {
    private Long id;
    private Loan emprunt;
    private User user;
    private BigDecimal montant;
    private String statut; // IMPAYEE, PAYEE, ANNULEE
    private String dateCreation;
    private String datePaiement;
    private String raison;
    private Integer nombreJoursRetard;
    
    // Constructeurs
    public Fine() {
    }
    
    public Fine(Long id, BigDecimal montant, String statut, String raison) {
        this.id = id;
        this.montant = montant;
        this.statut = statut;
        this.raison = raison;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Loan getEmprunt() {
        return emprunt;
    }
    
    public void setEmprunt(Loan emprunt) {
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
    
    public String getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public String getDatePaiement() {
        return datePaiement;
    }
    
    public void setDatePaiement(String datePaiement) {
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
    
    // Méthodes utilitaires
    public boolean estPayee() {
        return "PAYEE".equals(statut);
    }
    
    public boolean estImpayee() {
        return "IMPAYEE".equals(statut);
    }
}

