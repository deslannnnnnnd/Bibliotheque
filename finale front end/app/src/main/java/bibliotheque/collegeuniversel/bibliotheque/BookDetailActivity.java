package bibliotheque.collegeuniversel.bibliotheque;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvAuthor, tvIsbn, tvCategory, tvType, tvStatus;
    private TextView tvPages, tvPublisher, tvLanguage, tvPublicationDate, tvDateAdded;
    private Button btnBack, btnEmprunter;
    private Book currentBook;
    private Long currentUserId = 1L; // TODO: Récupérer l'ID de l'utilisateur connecté

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Initialisation des vues
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvAuthor = findViewById(R.id.tvDetailAuthor);
        tvIsbn = findViewById(R.id.tvDetailIsbn);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvType = findViewById(R.id.tvDetailType);
        tvStatus = findViewById(R.id.tvDetailStatus);
        tvPages = findViewById(R.id.tvDetailPages);
        tvPublisher = findViewById(R.id.tvDetailPublisher);
        tvLanguage = findViewById(R.id.tvDetailLanguage);
        tvPublicationDate = findViewById(R.id.tvDetailPublicationDate);
        tvDateAdded = findViewById(R.id.tvDetailDateAdded);
        btnBack = findViewById(R.id.btnBackToList);
        btnEmprunter = findViewById(R.id.btnEmprunter);

        // Récupérer l'ID du livre passé depuis LibraryActivity
        Long bookId = getIntent().getLongExtra("BOOK_ID", -1);

        if (bookId != -1) {
            loadBookDetails(bookId);
        } else {
            Toast.makeText(this, "Erreur: Livre non trouvé", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnBack.setOnClickListener(v -> finish());
        btnEmprunter.setOnClickListener(v -> showEmpruntDialog());
    }

    private void loadBookDetails(Long bookId) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<Book> call = apiService.getBookById(bookId);

        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Book book = response.body();
                    displayBookDetails(book);
                } else {
                    Toast.makeText(BookDetailActivity.this, 
                        "Erreur lors du chargement: " + response.code(), 
                        Toast.LENGTH_SHORT).show();
                    Log.e("BOOK_DETAIL", "Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, 
                    "Échec de connexion: " + t.getMessage(), 
                    Toast.LENGTH_SHORT).show();
                Log.e("BOOK_DETAIL", "Failure: " + t.getMessage(), t);
            }
        });
    }

    private void displayBookDetails(Book book) {
        currentBook = book; // Stocker le livre actuel
        
        tvTitle.setText(book.getTitre() != null ? book.getTitre() : "Non renseigné");
        tvAuthor.setText("Auteur: " + (book.getAuteur() != null ? book.getAuteur() : "Non renseigné"));
        tvIsbn.setText("ISBN: " + (book.getIsbn() != null ? book.getIsbn() : "Non renseigné"));
        tvCategory.setText("Catégorie: " + (book.getCategorie() != null ? book.getCategorie() : "Non renseignée"));
        tvType.setText("Type: " + (book.getType() != null ? book.getType() : "Non renseigné"));
        
        // Statut avec indicateur coloré ET nombre de copies
        String statut = book.getStatutDisponibilite() != null ? book.getStatutDisponibilite() : "inconnu";
        Integer copiesDisponibles = book.getCopiesDisponibles() != null ? book.getCopiesDisponibles() : 0;
        Integer copiesTotal = book.getNombreCopiesTotal() != null ? book.getNombreCopiesTotal() : 0;
        
        String dot = "🟢";
        if (statut.toLowerCase().contains("emprunté") || statut.toLowerCase().contains("emprunte")) {
            dot = "🟠";
        } else if (statut.toLowerCase().contains("réservé") || statut.toLowerCase().contains("reserve")) {
            dot = "🔴";
        }
        
        String statusText = "Statut: " + dot + " " + statut + "\n📚 Copies disponibles: " + copiesDisponibles + "/" + copiesTotal;
        tvStatus.setText(statusText);
        
        tvPages.setText("Pages: " + (book.getPages() != null ? book.getPages() + " pages" : "Non renseigné"));
        tvPublisher.setText("Éditeur: " + (book.getPublisher() != null ? book.getPublisher() : "Non renseigné"));
        tvLanguage.setText("Langue: " + (book.getLanguage() != null ? book.getLanguage() : "Non renseignée"));
        tvPublicationDate.setText("Date de publication: " + (book.getPublicationDate() != null ? book.getPublicationDate() : "Non renseignée"));
        tvDateAdded.setText("Ajouté le: " + (book.getDateAjout() != null ? book.getDateAjout() : "Non renseigné"));
        
        // Désactiver le bouton si le livre n'est pas disponible
        updateEmpruntButton(statut);
    }
    
    /**
     * Mettre à jour le bouton Emprunter selon les copies disponibles
     */
    private void updateEmpruntButton(String statut) {
        Integer copiesDisponibles = currentBook != null && currentBook.getCopiesDisponibles() != null ? 
                                     currentBook.getCopiesDisponibles() : 0;
        
        if (copiesDisponibles > 0) {
            btnEmprunter.setEnabled(true);
            btnEmprunter.setText("📚 Emprunter ce livre (" + copiesDisponibles + " disponibles)");
        } else {
            btnEmprunter.setEnabled(false);
            btnEmprunter.setText("❌ Aucune copie disponible");
        }
    }
    
    /**
     * FE-EMP-02 : Afficher la modale de confirmation d'emprunt
     */
    private void showEmpruntDialog() {
        if (currentBook == null) {
            Toast.makeText(this, "Erreur: Livre non chargé", Toast.LENGTH_SHORT).show();
            return;
        }
        
        new AlertDialog.Builder(this)
            .setTitle("Confirmer l'emprunt")
            .setMessage("Voulez-vous emprunter le livre \"" + currentBook.getTitre() + "\" ?\n\n" +
                       "Durée de prêt : 14 jours\n" +
                       "Prolongations possibles : 2 fois")
            .setPositiveButton("Emprunter", (dialog, which) -> emprunterLivre())
            .setNegativeButton("Annuler", null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show();
    }
    
    /**
     * FE-EMP-03 & FE-EMP-04 : Emprunter un livre avec gestion des erreurs
     */
    private void emprunterLivre() {
        if (currentBook == null || currentBook.getId() == null) {
            Toast.makeText(this, "Erreur: Livre non valide", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Désactiver le bouton pendant le chargement
        btnEmprunter.setEnabled(false);
        btnEmprunter.setText("⏳ Emprunt en cours...");
        
        // Préparer la requête
        Map<String, Long> request = new HashMap<>();
        request.put("userId", currentUserId);
        request.put("bookId", currentBook.getId());
        
        ApiService apiService = RetrofitClient.getApiService();
        Call<Loan> call = apiService.emprunterLivre(request);
        
        call.enqueue(new Callback<Loan>() {
            @Override
            public void onResponse(Call<Loan> call, Response<Loan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Loan loan = response.body();
                    
                    // FE-EMP-03 : Afficher les détails de l'emprunt
                    Toast.makeText(BookDetailActivity.this,
                        "✅ Emprunt réussi ! À retourner le " + loan.getDateRetourPrevue(),
                        Toast.LENGTH_LONG).show();
                    
                    // FE-EMP-05 : Mettre à jour l'UI
                    currentBook.setStatutDisponibilite("emprunté");
                    updateEmpruntButton("emprunté");
                    
                    // Recharger les détails du livre
                    loadBookDetails(currentBook.getId());
                    
                } else {
                    // FE-EMP-04 : Gérer les erreurs
                    String errorMsg = "Erreur d'emprunt";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        errorMsg = "Erreur " + response.code();
                    }
                    Toast.makeText(BookDetailActivity.this,
                        "❌ " + errorMsg,
                        Toast.LENGTH_LONG).show();
                    
                    btnEmprunter.setEnabled(true);
                    btnEmprunter.setText("📚 Emprunter ce livre");
                }
            }
            
            @Override
            public void onFailure(Call<Loan> call, Throwable t) {
                // FE-EMP-04 : Gérer les erreurs de connexion
                Toast.makeText(BookDetailActivity.this,
                    "❌ Échec de connexion: " + t.getMessage(),
                    Toast.LENGTH_LONG).show();
                
                btnEmprunter.setEnabled(true);
                btnEmprunter.setText("📚 Emprunter ce livre");
                
                Log.e("EMPRUNT", "Failure: " + t.getMessage(), t);
            }
        });
    }
}

