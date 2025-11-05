package Bibliotheque_DAEK.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Bibliotheque_DAEK.Model.Fine;
import Bibliotheque_DAEK.service.FineService;

@RestController
@RequestMapping("/api/fines")
@CrossOrigin(origins = "*")
public class FineController {

    @Autowired
    private FineService fineService;

    /**
     * BE-AMN-01 : Récupérer les amendes d'un utilisateur
     * GET /api/fines/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Fine>> getFinesByUser(@PathVariable Long userId) {
        try {
            List<Fine> fines = fineService.getFinesByUser(userId);
            return ResponseEntity.ok(fines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer les amendes impayées d'un utilisateur
     * GET /api/fines/user/{userId}/impayees
     */
    @GetMapping("/user/{userId}/impayees")
    public ResponseEntity<List<Fine>> getFinesImpayeesByUser(@PathVariable Long userId) {
        try {
            List<Fine> fines = fineService.getFinesImpayeesByUser(userId);
            return ResponseEntity.ok(fines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer toutes les amendes impayées (bibliothécaire)
     * GET /api/fines/impayees
     */
    @GetMapping("/impayees")
    public ResponseEntity<List<Fine>> getAllFinesImpayees() {
        try {
            List<Fine> fines = fineService.getAllFinesImpayees();
            return ResponseEntity.ok(fines);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * BE-AMN-04 : Payer une amende
     * POST /api/fines/{id}/pay
     */
    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payerAmende(@PathVariable Long id) {
        try {
            Fine fine = fineService.payerAmende(id);
            return ResponseEntity.ok(fine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur serveur"));
        }
    }

    /**
     * Annuler une amende (bibliothécaire)
     * POST /api/fines/{id}/cancel
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> annulerAmende(@PathVariable Long id) {
        try {
            Fine fine = fineService.annulerAmende(id);
            return ResponseEntity.ok(fine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur serveur"));
        }
    }
}

