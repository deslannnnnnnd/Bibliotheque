package Bibliotheque_DAEK.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Bibliotheque_DAEK.Model.Book;
import Bibliotheque_DAEK.Repository.BookRepository;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*") // Permettre l'accès depuis Android
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("")
    public List<Book> list() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        Optional<Book> b = bookRepository.findById(id);
        return b.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String query) {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .filter(b -> (b.getTitre() != null && b.getTitre().toLowerCase().contains(query.toLowerCase()))
                        || (b.getAuteur() != null && b.getAuteur().toLowerCase().contains(query.toLowerCase())))
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{category}")
    public List<Book> getByCategory(@PathVariable String category) {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .filter(b -> b.getCategorie() != null && b.getCategorie().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @GetMapping("/type/{type}")
    public List<Book> getByType(@PathVariable String type) {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .filter(b -> b.getType() != null && b.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @PostMapping("")
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitre(bookDetails.getTitre());
            book.setAuteur(bookDetails.getAuteur());
            book.setIsbn(bookDetails.getIsbn());
            book.setCategorie(bookDetails.getCategorie());
            book.setType(bookDetails.getType());
            book.setStatutDisponibilite(bookDetails.getStatutDisponibilite());
            return ResponseEntity.ok(bookRepository.save(book));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Initialiser les copies pour tous les livres (5 copies par défaut)
     */
    @PostMapping("/init-copies")
    public ResponseEntity<String> initializerCopies() {
        List<Book> allBooks = bookRepository.findAll();
        int count = 0;
        
        for (Book book : allBooks) {
            if (book.getNombreCopiesTotal() == null || book.getCopiesDisponibles() == null) {
                book.setNombreCopiesTotal(5);
                book.setCopiesDisponibles(5);
                book.setStatutDisponibilite("disponible");
                bookRepository.save(book);
                count++;
            }
        }
        
        return ResponseEntity.ok("Copies initialisées pour " + count + " livre(s)");
    }
}
