package Bibliotheque_DAEK.service;

import java.sql.Date;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Bibliotheque_DAEK.Model.User;
import Bibliotheque_DAEK.Repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String register(User user) {
        if (user.getRole() == null || user.getMotDePasse() == null) return "role and motDePasse required";
        user.setDateInscription(new Date(Instant.now().toEpochMilli()));
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        switch (user.getRole().toUpperCase()) {
            case "GUEST":
                if (user.getEmail() == null || user.getNom() == null) return "nom and email required";
                if (userRepository.existsByEmail(user.getEmail())) return "EMAIL_EXISTS";
                break;
            case "EMPLOYEE":
                if (user.getNumeroEmploye() == null) return "numeroEmploye required";
                if (userRepository.existsByNumeroEmploye(user.getNumeroEmploye())) return "EMP_EXISTS";
                break;
            case "ADMIN":
                if (user.getCodeAdmin() == null) return "codeAdmin required";
                if (userRepository.existsByCodeAdmin(user.getCodeAdmin())) return "ADMIN_EXISTS";
                break;
            default:
                return "unknown role";
        }
        userRepository.save(user);
        return "OK";
    }

    public User authenticate(User user) {
        if (user.getMotDePasse() == null) return null;
        User u = null;
        if (user.getEmail() != null) {
            u = userRepository.findByEmail(user.getEmail()).filter(x -> passwordEncoder.matches(user.getMotDePasse(), x.getMotDePasse())).orElse(null);
        } else if (user.getNumeroEmploye() != null) {
            u = userRepository.findByNumeroEmploye(user.getNumeroEmploye()).filter(x -> passwordEncoder.matches(user.getMotDePasse(), x.getMotDePasse())).orElse(null);
        } else if (user.getCodeAdmin() != null) {
            u = userRepository.findByCodeAdmin(user.getCodeAdmin()).filter(x -> passwordEncoder.matches(user.getMotDePasse(), x.getMotDePasse())).orElse(null);
        }
        return u;
    }
}
