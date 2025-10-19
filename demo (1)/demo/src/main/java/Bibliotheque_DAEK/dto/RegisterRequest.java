package Bibliotheque_DAEK.dto;

public class RegisterRequest {
    private String userType; // GUEST, EMPLOYEE, ADMIN
    private String nom;
    private String email;
    private String motDePasse;
    private String numeroEmploye;
    private String codeAdmin;

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getNumeroEmploye() { return numeroEmploye; }
    public void setNumeroEmploye(String numeroEmploye) { this.numeroEmploye = numeroEmploye; }
    public String getCodeAdmin() { return codeAdmin; }
    public void setCodeAdmin(String codeAdmin) { this.codeAdmin = codeAdmin; }
}
