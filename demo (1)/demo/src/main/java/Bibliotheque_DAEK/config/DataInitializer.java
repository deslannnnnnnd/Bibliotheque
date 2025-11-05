package Bibliotheque_DAEK.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import Bibliotheque_DAEK.Model.Book;
import Bibliotheque_DAEK.Repository.BookRepository;
import org.springframework.stereotype.Component;

import Bibliotheque_DAEK.Model.User;
import Bibliotheque_DAEK.Repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {

        //   admin prédéfini si il n'existe pas

        if (!userRepository.existsByCodeAdmin("Admin1234")) {
            User admin = new User();
            admin.setCodeAdmin("Admin1234");
            admin.setMotDePasse(passwordEncoder.encode("Admin1234"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Admin créé");
        }

        //  bibliothécaire prédéfini si il n'existe pas
        
        if (!userRepository.existsByNumeroEmploye("empl1234")) {
            User employee = new User();
            employee.setNumeroEmploye("empl1234");
            employee.setMotDePasse(passwordEncoder.encode("empl1234"));
            employee.setRole("EMPLOYEE");
            userRepository.save(employee);
            System.out.println("Bibliothécaire créé");
        }

        // Seed 10 books if not present
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();

        addBookIfNotExists(new Book(), "Le Petit Prince", "Antoine de Saint-Exupéry", "978-2-07-040525-1", "Littérature", "physique", "disponible", now, parseDate("1943-04-06", sdf), 96, "https://images/couverture1.jpg", "Gallimard", "Français");
        addBookIfNotExists(new Book(), "1984", "George Orwell", "978-2-07-036822-8", "Science-Fiction", "physique", "disponible", now, parseDate("1949-06-08", sdf), 328, "https://images/couverture2.jpg", "Gallimard", "Français");
        addBookIfNotExists(new Book(), "Harry Potter à l'école des sorciers", "J.K. Rowling", "978-2-07-054120-0", "Fantasy", "physique", "disponible", now, parseDate("1997-06-26", sdf), 320, "https://images/couverture3.jpg", "Gallimard Jeunesse", "Français");
        addBookIfNotExists(new Book(), "Les Misérables", "Victor Hugo", "978-2-07-040013-3", "Classique", "physique", "disponible", now, parseDate("1862-03-30", sdf), 1232, "https://images/couverture4.jpg", "Penguin Classics", "Français");
        addBookIfNotExists(new Book(), "L'Étranger", "Albert Camus", "978-2-07-036002-4", "Philosophie", "physique", "disponible", now, parseDate("1942-05-19", sdf), 185, "https://images/couverture5.jpg", "Gallimard", "Français");
        addBookIfNotExists(new Book(), "Da Vinci Code", "Dan Brown", "978-2-253-16081-7", "Thriller", "physique", "disponible", now, parseDate("2003-03-18", sdf), 454, "https://images/couverture6.jpg", "Jean-Claude Lattès", "Français");
        addBookIfNotExists(new Book(), "Autant en emporte le vent", "Margaret Mitchell", "978-2-266-28656-6", "Romance", "physique", "disponible", now, parseDate("1936-06-30", sdf), 1037, "https://images/couverture7.jpg", "Gone with the Wind", "Français");
        addBookIfNotExists(new Book(), "Germinal", "Émile Zola", "978-2-07-040850-4", "Classique", "physique", "disponible", now, parseDate("1885-03-25", sdf), 554, "https://images/couverture8.jpg", "Flammarion", "Français");
        addBookIfNotExists(new Book(), "Le Seigneur des Anneaux : La Communauté de l'Anneau", "J.R.R. Tolkien", "978-2-26-702821-2", "Fantasy", "physique", "disponible", now, parseDate("1954-07-29", sdf), 527, "https://images/couverture9.jpg", "Christian Bourgois", "Français");
        addBookIfNotExists(new Book(), "Orgueil et Préjugés", "Jane Austen", "978-2-07-037830-2", "Romance", "physique", "disponible", now, parseDate("1813-01-28", sdf), 432, "https://images/couverture10.jpg", "Penguin Classics", "Français");
    }

    private Date parseDate(String s, SimpleDateFormat sdf) {
        try {
            return sdf.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    private void addBookIfNotExists(Book b, String titre, String auteur, String isbn, String categorie, String type, String statut, Date dateAjout, Date publicationDate, Integer pages, String coverImageUrl, String publisher, String language) {
        if (bookRepository.findByIsbn(isbn).isPresent()) return;
        b.setTitre(titre);
        b.setAuteur(auteur);
        b.setIsbn(isbn);
        b.setCategorie(categorie);
        b.setType(type);
        b.setStatutDisponibilite(statut);
        b.setDateAjout(new java.sql.Date(dateAjout.getTime()));
        b.setPublicationDate(new java.sql.Date(publicationDate.getTime()));
        b.setPages(pages);
        b.setCoverImageUrl(coverImageUrl);
        b.setPublisher(publisher);
        b.setLanguage(language);
        bookRepository.save(b);
    }

    private void addBookIfNotExists(Book b, String titre, String auteur, String isbn, String categorie, String type, String statut, Date dateAjout, Date publicationDate, int pages, String coverImageUrl, String publisher, String language) {
        addBookIfNotExists(b, titre, auteur, isbn, categorie, type, statut, dateAjout, publicationDate, Integer.valueOf(pages), coverImageUrl, publisher, language);
    }
}