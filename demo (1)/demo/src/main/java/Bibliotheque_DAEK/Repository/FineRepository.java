package Bibliotheque_DAEK.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Bibliotheque_DAEK.Model.Fine;
import Bibliotheque_DAEK.Model.User;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findByUser(User user);
    List<Fine> findByUserAndStatut(User user, String statut);
    List<Fine> findByStatut(String statut);
}

