package bibliotheque.collegeuniversel.bibliotheque;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryActivity extends AppCompatActivity {

    // Vues
    private RecyclerView recyclerViewBooks;
    private EditText etSearch;
    private Button btnFilterAll, btnFilterRoman, btnFilterSciFi, btnFilterFantasy;
    private Button btnTypeAll, btnTypePrint, btnTypeEbook, btnTypeAudio;

    // Adapter et données
    private BooksAdapter booksAdapter;
    private List<Book> allBooks = new ArrayList<>();
    private String currentCategory = "ALL";
    private String currentType = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // Initialiser les vues
        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        etSearch = findViewById(R.id.etSearch);

        btnFilterAll = findViewById(R.id.btnFilterAll);
        btnFilterRoman = findViewById(R.id.btnFilterRoman);
        btnFilterSciFi = findViewById(R.id.btnFilterSciFi);
        btnFilterFantasy = findViewById(R.id.btnFilterFantasy);

        btnTypeAll = findViewById(R.id.btnTypeAll);
        btnTypePrint = findViewById(R.id.btnTypePrint);
        btnTypeEbook = findViewById(R.id.btnTypeEbook);
        btnTypeAudio = findViewById(R.id.btnTypeAudio);

        // Configurer le RecyclerView
        setupRecyclerView();

        // Charger les livres depuis l'API backend
        loadBooksFromAPI();

        // Filtres catégories
        btnFilterAll.setOnClickListener(v -> { currentCategory = "ALL"; filterBooks(); });
        btnFilterRoman.setOnClickListener(v -> { currentCategory = "ROMAN"; filterBooks(); });
        btnFilterSciFi.setOnClickListener(v -> { currentCategory = "SCI-FI"; filterBooks(); });
        btnFilterFantasy.setOnClickListener(v -> { currentCategory = "FANTASY"; filterBooks(); });

        // Filtres type
        btnTypeAll.setOnClickListener(v -> { currentType = "ALL"; filterBooks(); });
        btnTypePrint.setOnClickListener(v -> { currentType = "Imprimé"; filterBooks(); });
        btnTypeEbook.setOnClickListener(v -> { currentType = "E-book"; filterBooks(); });
        btnTypeAudio.setOnClickListener(v -> { currentType = "Audio"; filterBooks(); });

        // Recherche (Enter)
        etSearch.setOnEditorActionListener((tv, actionId, event) -> { filterBooks(); return true; });

        // Bouton retour
        findViewById(R.id.btnBackToAuth).setOnClickListener(v -> finish());
    }

    /**
     * Configurer le RecyclerView avec LayoutManager et Adapter
     */
    private void setupRecyclerView() {
        // Créer le LayoutManager (LinearLayoutManager pour affichage vertical)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBooks.setLayoutManager(layoutManager);

        // Créer l'adapter avec listener pour les clics
        booksAdapter = new BooksAdapter(book -> {
            // Ouvrir l'activité de détails du livre
            Intent intent = new Intent(LibraryActivity.this, BookDetailActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            startActivity(intent);
        });

        // Lier l'adapter au RecyclerView
        recyclerViewBooks.setAdapter(booksAdapter);

        // (Optionnel) Ajouter une décoration (séparateur entre items)
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerViewBooks.getContext(),
                layoutManager.getOrientation()
        );
        recyclerViewBooks.addItemDecoration(dividerItemDecoration);
    }

    /**
     * Filtrer les livres selon les critères de recherche et catégories
     */
    private void filterBooks() {
        String query = etSearch.getText().toString().trim().toLowerCase();

        // Filtrer la liste avec Java 8 Streams
        List<Book> filteredBooks = allBooks.stream()
                .filter(book -> matchesQuery(book, query))
                .filter(book -> matchesCategory(book))
                .filter(book -> matchesType(book))
                .collect(Collectors.toList());

        // Mettre à jour l'adapter avec la liste filtrée
        booksAdapter.setBooks(filteredBooks);

        // Afficher un message si aucun livre n'est trouvé
        if (filteredBooks.isEmpty()) {
            Toast.makeText(this, "Aucun livre trouvé.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Vérifie si un livre correspond à la requête de recherche
     */
    private boolean matchesQuery(Book book, String query) {
        if (query.isEmpty()) {
            return true;
        }
        return (book.getTitre() != null && book.getTitre().toLowerCase().contains(query))
                || (book.getAuteur() != null && book.getAuteur().toLowerCase().contains(query));
    }

    /**
     * Vérifie si un livre correspond à la catégorie sélectionnée
     */
    private boolean matchesCategory(Book book) {
        if (currentCategory.equals("ALL")) {
            return true;
        }
        return book.getCategorie() != null && book.getCategorie().equalsIgnoreCase(currentCategory);
    }

    /**
     * Vérifie si un livre correspond au type sélectionné
     */
    private boolean matchesType(Book book) {
        if (currentType.equals("ALL")) {
            return true;
        }
        return book.getType() != null && book.getType().equalsIgnoreCase(currentType);
    }

    /**
     * Charger les livres depuis l'API backend
     */
    private void loadBooksFromAPI() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Book>> call = apiService.getAllBooks();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allBooks = response.body();
                    Toast.makeText(LibraryActivity.this, 
                        "✅ Chargé " + allBooks.size() + " livres depuis le serveur", 
                        Toast.LENGTH_LONG).show();
                    Log.d("API_SUCCESS", "Livres chargés: " + allBooks.size());
                    
                    // Afficher tous les livres initialement
                    filterBooks();
                } else {
                    Toast.makeText(LibraryActivity.this, 
                        "❌ Erreur serveur: " + response.code(), 
                        Toast.LENGTH_LONG).show();
                    Log.e("API_ERROR", "Code d'erreur: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(LibraryActivity.this, 
                    "❌ Connexion échouée: " + t.getMessage(), 
                    Toast.LENGTH_LONG).show();
                Log.e("API_FAILURE", "Erreur de connexion: " + t.getMessage(), t);
            }
        });
    }
}
