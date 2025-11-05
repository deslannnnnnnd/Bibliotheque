package Bibliotheque_DAEK.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Bibliotheque_DAEK.Model.Notification;
import Bibliotheque_DAEK.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * BE-NOT-04 : Récupérer les notifications d'un utilisateur
     * GET /api/notifications/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable Long userId) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByUser(userId);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Récupérer les notifications non lues d'un utilisateur
     * GET /api/notifications/user/{userId}/unread
     */
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getNotificationsNonLues(@PathVariable Long userId) {
        try {
            List<Notification> notifications = notificationService.getNotificationsNonLues(userId);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Compter les notifications non lues
     * GET /api/notifications/user/{userId}/count-unread
     */
    @GetMapping("/user/{userId}/count-unread")
    public ResponseEntity<Map<String, Long>> countNotificationsNonLues(@PathVariable Long userId) {
        try {
            Long count = notificationService.countNotificationsNonLues(userId);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * BE-NOT-05 : Marquer une notification comme lue
     * PUT /api/notifications/{id}/mark-read
     */
    @PutMapping("/{id}/mark-read")
    public ResponseEntity<?> marquerCommeLue(@PathVariable Long id) {
        try {
            Notification notification = notificationService.marquerCommeLue(id);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur serveur"));
        }
    }

    /**
     * Marquer toutes les notifications comme lues
     * PUT /api/notifications/user/{userId}/mark-all-read
     */
    @PutMapping("/user/{userId}/mark-all-read")
    public ResponseEntity<?> marquerToutesCommeLues(@PathVariable Long userId) {
        try {
            notificationService.marquerToutesCommeLues(userId);
            return ResponseEntity.ok(Map.of("message", "Toutes les notifications ont été marquées comme lues"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur serveur"));
        }
    }
}

