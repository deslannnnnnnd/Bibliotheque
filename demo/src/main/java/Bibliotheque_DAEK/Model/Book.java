package Bibliotheque_DAEK.Model;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
public class Book {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)

   private Long id;
   private String titre;
   private String auteur;
   private String isbn ; 
   private String categorie ; 
   private String type ; 
   private String statutDisponibilite;
   private Date dateAjout ;
   public Long getId() {
    return id;
   }
   public void setId(Long id) {
    this.id = id;
   }
   public String getTitre() {
    return titre;
   }
   public void setTitre(String titre) {
    this.titre = titre;
   }
   public String getAuteur() {
    return auteur;
   }
   public void setAuteur(String auteur) {
    this.auteur = auteur;
   }
   public String getIsbn() {
    return isbn;
   }
   public void setIsbn(String isbn) {
    this.isbn = isbn;
   }
   public String getCategorie() {
    return categorie;
   }
   public void setCategorie(String categorie) {
    this.categorie = categorie;
   }
   public String getType() {
    return type;
   }
   public void setType(String type) {
    this.type = type;
   }
   public String getStatutDisponibilite() {
    return statutDisponibilite;
   }
   public void setStatutDisponibilite(String statutDisponibilite) {
    this.statutDisponibilite = statutDisponibilite;
   }
   public Date getDateAjout() {
    return dateAjout;
   }
   public void setDateAjout(Date dateAjout) {
    this.dateAjout = dateAjout;
   } 


   

}
