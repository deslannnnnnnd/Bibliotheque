package Bibliotheque_DAEK.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Bibliotheque_DAEK.Model.User;
import Bibliotheque_DAEK.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        // Utilise directement l'objet User pour l'inscription
        String res = authService.register(user);
        if ("OK".equals(res)) return ResponseEntity.ok("registered");
        return ResponseEntity.badRequest().body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // Utilise directement l'objet User pour la connexion
        User u = authService.authenticate(user);
        if (u == null) return ResponseEntity.status(401).body("invalid credentials");
        return ResponseEntity.ok("authenticated as " + u.getRole());
    }

}
