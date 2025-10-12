package Bibliotheque_DAEK.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Bibliotheque_DAEK.Model.User; 
public interface UserRepository  extends JpaRepository<User,Long>{
    List<User>findByNomContainingIgnoredCase(String nom);
    Optional<User>findByEmail(String email);
    Optional<User>findbyCodeAdmin(String codeAdmin);

  Optional<User> findByNumeroEmploye(String numeroEmploye);
    boolean existsByEmail(String email);
}
