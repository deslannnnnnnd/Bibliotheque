package bibliotheque.collegeuniversel.bibliotheque;

import com.google.gson.annotations.SerializedName;

public class Book {
    private Long id;
    @SerializedName("titre") private String titre;
    @SerializedName("auteur") private String auteur;
    @SerializedName("isbn") private String isbn;
    @SerializedName("categorie") private String categorie;
    @SerializedName("type") private String type;
    @SerializedName("statutDisponibilite") private String statutDisponibilite;
    @SerializedName("dateAjout") private String dateAjout;
    @SerializedName("publicationDate") private String publicationDate;
    @SerializedName("pages") private Integer pages;
    @SerializedName("coverImageUrl") private String coverImageUrl;
    @SerializedName("publisher") private String publisher;
    @SerializedName("language") private String language;
    
    // Gestion des copies
    @SerializedName("nombreCopiesTotal") private Integer nombreCopiesTotal;
    @SerializedName("copiesDisponibles") private Integer copiesDisponibles;

    public Book() {}

    // Getters
    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public String getIsbn() { return isbn; }
    public String getCategorie() { return categorie; }
    public String getType() { return type; }
    public String getStatutDisponibilite() { return statutDisponibilite; }
    public String getDateAjout() { return dateAjout; }
    public String getPublicationDate() { return publicationDate; }
    public Integer getPages() { return pages; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public String getPublisher() { return publisher; }
    public String getLanguage() { return language; }
    public Integer getNombreCopiesTotal() { return nombreCopiesTotal; }
    public Integer getCopiesDisponibles() { return copiesDisponibles; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setType(String type) { this.type = type; }
    public void setStatutDisponibilite(String s) { this.statutDisponibilite = s; }
    public void setDateAjout(String dateAjout) { this.dateAjout = dateAjout; }
    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }
    public void setPages(Integer pages) { this.pages = pages; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setLanguage(String language) { this.language = language; }
    public void setNombreCopiesTotal(Integer nombreCopiesTotal) { this.nombreCopiesTotal = nombreCopiesTotal; }
    public void setCopiesDisponibles(Integer copiesDisponibles) { this.copiesDisponibles = copiesDisponibles; }
}