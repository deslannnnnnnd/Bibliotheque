package bibliotheque.collegeuniversel.bibliotheque;

import java.math.BigDecimal;

public class Loan {
    private Long id;
    private User user;
    private Book book;
    private String dateEmprunt;
    private String dateRetourEffective;
    private String dateRetourPrevue;
    private String statut;
    private BigDecimal amende;
    private Integer nombreProlongations;
    private Integer prolongationsRestantes;
    
    // Constructeurs
    public Loan() {
    }
    
    public Loan(Long id, User user, Book book, String dateEmprunt, String dateRetourPrevue, String statut) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.statut = statut;
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
    
    public String getDateEmprunt() {
        return dateEmprunt;
    }
    
    public void setDateEmprunt(String dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    
    public String getDateRetourEffective() {
        return dateRetourEffective;
    }
    
    public void setDateRetourEffective(String dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }
    
    public String getDateRetourPrevue() {
        return dateRetourPrevue;
    }
    
    public void setDateRetourPrevue(String dateRetourPrevue) {
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
    
    // Méthodes utilitaires
    public boolean peutEtreProlonge() {
        return prolongationsRestantes != null && prolongationsRestantes > 0 && "EN_COURS".equals(statut);
    }
    
    public boolean estEnCours() {
        return "EN_COURS".equals(statut);
    }
    
    public boolean estRetourne() {
        return "RETOURNE".equals(statut);
    }
    
    public boolean estEnRetard() {
        return "EN_RETARD".equals(statut);
    }
}

