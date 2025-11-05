package Bibliotheque_DAEK.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Bibliotheque_DAEK.Model.Emprunt;
import Bibliotheque_DAEK.service.LoanService;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "*")
public class EmpruntController {

    @Autowired
    private LoanService loanService;

    /**
     * BE-CON-02 : Récupérer tous les emprunts (bibliothécaire)
     */
    @GetMapping
    public ResponseEntity<List<Emprunt>> getAllEmprunts() {
        try {
            List<Emprunt> emprunts = loanService.getAllEmprunts();
            return ResponseEntity.ok(emprunts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * BE-CON-01 : Récupérer les emprunts d'un utilisateur
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Emprunt>> getEmpruntsByUser(@PathVariable Long userId) {
        try {
            List<Emprunt> emprunts = loanService.getEmpruntsByUser(userId);
            return ResponseEntity.ok(emprunts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * BE-CON-03 : Filtrer les emprunts par statut
     */
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Emprunt>> getEmpruntsByStatut(@PathVariable String statut) {
        try {
            List<Emprunt> emprunts = loanService.getEmpruntsByStatut(statut);
            return ResponseEntity.ok(emprunts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer les emprunts en retard
     */
    @GetMapping("/retard")
    public ResponseEntity<List<Emprunt>> getEmpruntsEnRetard() {
        try {
            List<Emprunt> emprunts = loanService.getEmpruntsEnRetard();
            return ResponseEntity.ok(emprunts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * BE-EMP-01 : Emprunter un livre
     * POST /api/loans
     * Body: { "userId": 1, "bookId": 5 }
     */
    @PostMapping
    public ResponseEntity<?> emprunterLivre(@RequestBody Map<String, Long> request) {
        try {
            Long userId = request.get("userId");
            Long bookId = request.get("bookId");
            Emprunt emprunt = loanService.emprunterLivre(userId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED).body(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur serveur"));
        }
    }

    /**
     * BE-PRO-01 : Prolonger un emprunt
     * PUT /api/loans/{id}/extend
     */
    @PutMapping("/{id}/extend")
    public ResponseEntity<?> prolongerEmprunt(@PathVariable Long id) {
        try {
            Emprunt emprunt = loanService.prolongerEmprunt(id);
            return ResponseEntity.ok(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur serveur"));
        }
    }

    /**
     * BE-RET-01 : Retourner un livre
     * PUT /api/loans/{id}/return
     */
    @PutMapping("/{id}/return")
    public ResponseEntity<?> retournerLivre(@PathVariable Long id) {
        try {
            Emprunt emprunt = loanService.retournerLivre(id);
            return ResponseEntity.ok(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur serveur"));
        }
    }
}

