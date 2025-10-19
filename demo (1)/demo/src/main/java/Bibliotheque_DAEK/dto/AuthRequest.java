package Bibliotheque_DAEK.dto;

public class AuthRequest {
    private String identifier; // email or numeroEmploye or codeAdmin depending on userType
    private String motDePasse;

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}
