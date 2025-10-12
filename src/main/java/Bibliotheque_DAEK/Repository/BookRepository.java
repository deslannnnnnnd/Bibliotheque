package Bibliotheque_DAEK.Repository;

import Bibliotheque_DAEK.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {


List<Book> findByTitreContainingIgnoreCase(String titre);
List<Book> findByAuteurContainingIgnoredCase(String auteur);
 List<Book> findByCategorie(String categorie);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByStatutDisponibilite(String statut);

}
