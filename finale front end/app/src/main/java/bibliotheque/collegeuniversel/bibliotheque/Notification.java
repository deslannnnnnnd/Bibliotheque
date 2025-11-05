package bibliotheque.collegeuniversel.bibliotheque;

public class Notification {
    private Long id;
    private User user;
    private String type; // RAPPEL_RETOUR, RETARD, AMENDE, RESERVATION_DISPONIBLE, EMPRUNT, PROLONGATION, PAIEMENT
    private String titre;
    private String message;
    private Boolean lue;
    private String dateEnvoi;
    private String dateLecture;
    private Loan emprunt;
    
    // Constructeurs
    public Notification() {
    }
    
    public Notification(Long id, String type, String titre, String message, Boolean lue) {
        this.id = id;
        this.type = type;
        this.titre = titre;
        this.message = message;
        this.lue = lue;
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
    
    public String getDateEnvoi() {
        return dateEnvoi;
    }
    
    public void setDateEnvoi(String dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
    
    public String getDateLecture() {
        return dateLecture;
    }
    
    public void setDateLecture(String dateLecture) {
        this.dateLecture = dateLecture;
    }
    
    public Loan getEmprunt() {
        return emprunt;
    }
    
    public void setEmprunt(Loan emprunt) {
        this.emprunt = emprunt;
    }
    
    // Méthodes utilitaires
    public boolean estLue() {
        return lue != null && lue;
    }
    
    public String getIcone() {
        switch (type) {
            case "RAPPEL_RETOUR":
                return "⏰";
            case "RETARD":
                return "⚠️";
            case "AMENDE":
                return "💰";
            case "EMPRUNT":
                return "📚";
            case "PROLONGATION":
                return "🔄";
            case "PAIEMENT":
                return "✅";
            default:
                return "📬";
        }
    }
}

