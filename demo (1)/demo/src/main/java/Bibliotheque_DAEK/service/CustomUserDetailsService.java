package Bibliotheque_DAEK.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Bibliotheque_DAEK.Model.User;
import Bibliotheque_DAEK.Repository.UserRepository;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        
        // Essayer de trouver par email
        user = userRepository.findByEmail(username).orElse(null);
        
        // Si pas trouvé, essayer par numeroEmploye
        if (user == null) {
            user = userRepository.findByNumeroEmploye(username).orElse(null);
        }
        
        // Si pas trouvé, essayer par codeAdmin
        if (user == null) {
            user = userRepository.findByCodeAdmin(username).orElse(null);
        }
        
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getMotDePasse())
                .authorities(new ArrayList<>())
                .build();
    }
}

