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
   // New fields
   private Date publicationDate;
   private Integer pages;
   private String coverImageUrl;
   private String publisher;
   private String language;
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

   public Date getPublicationDate() {
    return publicationDate;
   }

   public void setPublicationDate(Date publicationDate) {
    this.publicationDate = publicationDate;
   }

   public Integer getPages() {
    return pages;
   }

   public void setPages(Integer pages) {
    this.pages = pages;
   }

   public String getCoverImageUrl() {
    return coverImageUrl;
   }

   public void setCoverImageUrl(String coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
   }

   public String getPublisher() {
    return publisher;
   }

   public void setPublisher(String publisher) {
    this.publisher = publisher;
   }

   public String getLanguage() {
    return language;
   }

   public void setLanguage(String language) {
    this.language = language;
   }


   

}
