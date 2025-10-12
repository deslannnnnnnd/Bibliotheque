package Bibliotheque_DAEK.Model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity     

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String nom ;
    private String email ; 
    private String motDePasse ; 
    private String numeroEmploye ; 
    private String role ; 
    private String codeAdmin ; 
    private Date dateInscription ;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public String getNumeroEmploye() {
        return numeroEmploye;
    }
    public void setNumeroEmploye(String numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }
    public String getCodeAdmin() {
        return codeAdmin;
    }
    public void setCodeAdmin(String codeAdmin) {
        this.codeAdmin = codeAdmin;
    }
    public Date getDateInscription() {
        return dateInscription;
    }
    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    } 

    
}
