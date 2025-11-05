package Bibliotheque_DAEK.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Bibliotheque_DAEK.Model.Notification;
import Bibliotheque_DAEK.Model.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
    List<Notification> findByUserAndLue(User user, Boolean lue);
    List<Notification> findByUserOrderByDateEnvoiDesc(User user);
    Long countByUserAndLue(User user, Boolean lue);
}

