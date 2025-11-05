package bibliotheque.collegeuniversel.bibliotheque;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLoansActivity extends AppCompatActivity implements LoansAdapter.OnLoanActionListener {

    private RecyclerView recyclerViewLoans;
    private LoansAdapter loansAdapter;
    private Button btnFilterAll, btnFilterEnCours, btnFilterRetourne, btnBackToMenu;
    
    private List<Loan> allLoans = new ArrayList<>();
    private String currentFilter = "ALL";
    private Long currentUserId = 1L; // TODO: Récupérer l'ID de l'utilisateur connecté

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_loans);

        // Initialiser les vues
        recyclerViewLoans = findViewById(R.id.recyclerViewLoans);
        btnFilterAll = findViewById(R.id.btnFilterAll);
        btnFilterEnCours = findViewById(R.id.btnFilterEnCours);
        btnFilterRetourne = findViewById(R.id.btnFilterRetourne);
        btnBackToMenu = findViewById(R.id.btnBackToMenu);

        // Configurer le RecyclerView
        setupRecyclerView();

        // Charger les emprunts
        loadLoans();

        // Filtres
        btnFilterAll.setOnClickListener(v -> filterLoans("ALL"));
        btnFilterEnCours.setOnClickListener(v -> filterLoans("EN_COURS"));
        btnFilterRetourne.setOnClickListener(v -> filterLoans("RETOURNE"));

        // Bouton retour
        btnBackToMenu.setOnClickListener(v -> finish());
    }

    /**
     * Configurer le RecyclerView
     */
    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewLoans.setLayoutManager(layoutManager);

        loansAdapter = new LoansAdapter(this);
        recyclerViewLoans.setAdapter(loansAdapter);
    }

    /**
     * FE-CON-01 : Charger les emprunts de l'utilisateur
     */
    private void loadLoans() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Loan>> call = apiService.getLoansByUser(currentUserId);

        call.enqueue(new Callback<List<Loan>>() {
            @Override
            public void onResponse(Call<List<Loan>> call, Response<List<Loan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allLoans = response.body();
                    Toast.makeText(MyLoansActivity.this,
                        "✅ " + allLoans.size() + " emprunt(s) chargé(s)",
                        Toast.LENGTH_SHORT).show();
                    filterLoans(currentFilter);
                } else {
                    Toast.makeText(MyLoansActivity.this,
                        "❌ Erreur serveur: " + response.code(),
                        Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Loan>> call, Throwable t) {
                Toast.makeText(MyLoansActivity.this,
                    "❌ Connexion échouée: " + t.getMessage(),
                    Toast.LENGTH_SHORT).show();
                Log.e("MY_LOANS", "Failure: " + t.getMessage(), t);
            }
        });
    }

    /**
     * FE-CON-03 : Filtrer les emprunts par statut
     */
    private void filterLoans(String filter) {
        currentFilter = filter;
        
        // Mettre à jour les boutons
        resetFilterButtons();
        switch (filter) {
            case "ALL":
                btnFilterAll.setBackgroundTintList(getColorStateList(android.R.color.holo_blue_dark));
                break;
            case "EN_COURS":
                btnFilterEnCours.setBackgroundTintList(getColorStateList(android.R.color.holo_blue_dark));
                break;
            case "RETOURNE":
                btnFilterRetourne.setBackgroundTintList(getColorStateList(android.R.color.holo_blue_dark));
                break;
        }

        // Filtrer la liste
        List<Loan> filteredLoans;
        if ("ALL".equals(filter)) {
            filteredLoans = allLoans;
        } else {
            filteredLoans = allLoans.stream()
                    .filter(loan -> filter.equals(loan.getStatut()))
                    .collect(Collectors.toList());
        }

        loansAdapter.setLoans(filteredLoans);
        
        if (filteredLoans.isEmpty()) {
            Toast.makeText(this, "Aucun emprunt trouvé.", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFilterButtons() {
        int grayColor = android.R.color.darker_gray;
        btnFilterAll.setBackgroundTintList(getColorStateList(grayColor));
        btnFilterEnCours.setBackgroundTintList(getColorStateList(grayColor));
        btnFilterRetourne.setBackgroundTintList(getColorStateList(grayColor));
    }

    /**
     * FE-PRO-03 : Prolonger un emprunt avec confirmation
     */
    @Override
    public void onExtendLoan(Loan loan) {
        new AlertDialog.Builder(this)
            .setTitle("Prolonger l'emprunt")
            .setMessage("Voulez-vous prolonger l'emprunt de \"" + 
                       (loan.getBook() != null ? loan.getBook().getTitre() : "ce livre") + "\" ?\n\n" +
                       "Prolongation de 7 jours supplémentaires\n" +
                       "Prolongations restantes : " + loan.getProlongationsRestantes())
            .setPositiveButton("Prolonger", (dialog, which) -> extendLoan(loan))
            .setNegativeButton("Annuler", null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show();
    }

    /**
     * BE-PRO-01 : Appeler l'API pour prolonger l'emprunt
     */
    private void extendLoan(Loan loan) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<Loan> call = apiService.prolongerEmprunt(loan.getId());

        call.enqueue(new Callback<Loan>() {
            @Override
            public void onResponse(Call<Loan> call, Response<Loan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Loan updatedLoan = response.body();
                    
                    // FE-PRO-05 : Mettre à jour l'affichage
                    Toast.makeText(MyLoansActivity.this,
                        "✅ Emprunt prolongé ! Nouvelle date de retour : " + updatedLoan.getDateRetourPrevue(),
                        Toast.LENGTH_LONG).show();
                    
                    // Recharger les emprunts
                    loadLoans();
                } else {
                    String errorMsg = "Impossible de prolonger";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        errorMsg = "Erreur " + response.code();
                    }
                    Toast.makeText(MyLoansActivity.this,
                        "❌ " + errorMsg,
                        Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Loan> call, Throwable t) {
                Toast.makeText(MyLoansActivity.this,
                    "❌ Échec de connexion: " + t.getMessage(),
                    Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * FE-RET-03 : Retourner un livre avec confirmation
     */
    @Override
    public void onReturnLoan(Loan loan) {
        new AlertDialog.Builder(this)
            .setTitle("Retourner le livre")
            .setMessage("Confirmez-vous le retour de \"" + 
                       (loan.getBook() != null ? loan.getBook().getTitre() : "ce livre") + "\" ?")
            .setPositiveButton("Retourner", (dialog, which) -> returnLoan(loan))
            .setNegativeButton("Annuler", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    /**
     * BE-RET-01 : Appeler l'API pour retourner le livre
     */
    private void returnLoan(Loan loan) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<Loan> call = apiService.retournerLivre(loan.getId());

        call.enqueue(new Callback<Loan>() {
            @Override
            public void onResponse(Call<Loan> call, Response<Loan> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Loan returnedLoan = response.body();
                    
                    // FE-RET-04 : Afficher les amendes si retard
                    String message = "✅ Livre retourné avec succès !";
                    if (returnedLoan.getAmende() != null && returnedLoan.getAmende().doubleValue() > 0) {
                        message += "\n⚠️ Amende de " + returnedLoan.getAmende() + " € appliquée pour retard.";
                    }
                    
                    Toast.makeText(MyLoansActivity.this, message, Toast.LENGTH_LONG).show();
                    
                    // FE-RET-05 : Recharger la liste
                    loadLoans();
                } else {
                    Toast.makeText(MyLoansActivity.this,
                        "❌ Erreur lors du retour: " + response.code(),
                        Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Loan> call, Throwable t) {
                Toast.makeText(MyLoansActivity.this,
                    "❌ Échec de connexion: " + t.getMessage(),
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Clic sur un emprunt
     */
    @Override
    public void onLoanClick(Loan loan) {
        // TODO: Afficher les détails de l'emprunt
        Toast.makeText(this, "Emprunt: " + 
            (loan.getBook() != null ? loan.getBook().getTitre() : "N/A"), 
            Toast.LENGTH_SHORT).show();
    }
}

