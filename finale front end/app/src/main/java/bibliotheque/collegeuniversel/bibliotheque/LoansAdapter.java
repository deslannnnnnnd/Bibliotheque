package bibliotheque.collegeuniversel.bibliotheque;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class LoansAdapter extends RecyclerView.Adapter<LoansAdapter.LoanViewHolder> {

    private List<Loan> loansList;
    private OnLoanActionListener listener;

    // Interface pour gérer les actions sur les emprunts
    public interface OnLoanActionListener {
        void onExtendLoan(Loan loan);
        void onReturnLoan(Loan loan);
        void onLoanClick(Loan loan);
    }

    // Constructeur
    public LoansAdapter(OnLoanActionListener listener) {
        this.loansList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loan, parent, false);
        return new LoanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanViewHolder holder, int position) {
        Loan loan = loansList.get(position);
        holder.bind(loan, listener);
    }

    @Override
    public int getItemCount() {
        return loansList.size();
    }

    // Mettre à jour la liste des emprunts
    public void setLoans(List<Loan> loans) {
        this.loansList = loans != null ? loans : new ArrayList<>();
        notifyDataSetChanged();
    }

    // ViewHolder
    static class LoanViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBookTitle;
        private TextView tvBookAuthor;
        private TextView tvDateEmprunt;
        private TextView tvDateRetourPrevue;
        private TextView tvStatut;
        private TextView tvProlongations;
        private Button btnProlonger;
        private Button btnRetourner;

        public LoanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvLoanBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvLoanBookAuthor);
            tvDateEmprunt = itemView.findViewById(R.id.tvLoanDateEmprunt);
            tvDateRetourPrevue = itemView.findViewById(R.id.tvLoanDateRetourPrevue);
            tvStatut = itemView.findViewById(R.id.tvLoanStatut);
            tvProlongations = itemView.findViewById(R.id.tvLoanProlongations);
            btnProlonger = itemView.findViewById(R.id.btnProlonger);
            btnRetourner = itemView.findViewById(R.id.btnRetourner);
        }

        public void bind(Loan loan, OnLoanActionListener listener) {
            // Afficher les informations du livre
            if (loan.getBook() != null) {
                tvBookTitle.setText(loan.getBook().getTitre() != null ? loan.getBook().getTitre() : "Sans titre");
                tvBookAuthor.setText(loan.getBook().getAuteur() != null ? loan.getBook().getAuteur() : "Auteur inconnu");
            } else {
                tvBookTitle.setText("Livre non disponible");
                tvBookAuthor.setText("");
            }

            // Dates
            tvDateEmprunt.setText(loan.getDateEmprunt() != null ? loan.getDateEmprunt() : "N/A");
            tvDateRetourPrevue.setText(loan.getDateRetourPrevue() != null ? loan.getDateRetourPrevue() : "N/A");

            // Statut avec icône
            String statut = loan.getStatut() != null ? loan.getStatut() : "inconnu";
            String statutIcon = getStatutIcon(statut);
            tvStatut.setText(statutIcon + " " + formatStatut(statut));

            // Prolongations
            Integer prolongationsRestantes = loan.getProlongationsRestantes() != null ? loan.getProlongationsRestantes() : 0;
            tvProlongations.setText("🔄 Prolongations restantes: " + prolongationsRestantes);

            // FE-PRO-02 : Afficher conditionnellement le bouton de prolongation
            if (loan.peutEtreProlonge()) {
                btnProlonger.setVisibility(View.VISIBLE);
                btnProlonger.setEnabled(true);
            } else {
                btnProlonger.setVisibility(View.GONE);
            }

            // Afficher le bouton retourner seulement si l'emprunt est en cours
            if ("EN_COURS".equals(statut)) {
                btnRetourner.setVisibility(View.VISIBLE);
                btnRetourner.setEnabled(true);
            } else {
                btnRetourner.setVisibility(View.GONE);
            }

            // Actions
            btnProlonger.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onExtendLoan(loan);
                }
            });

            btnRetourner.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onReturnLoan(loan);
                }
            });

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLoanClick(loan);
                }
            });
        }

        private String getStatutIcon(String statut) {
            switch (statut) {
                case "EN_COURS":
                    return "🟢";
                case "RETOURNE":
                    return "✅";
                case "EN_RETARD":
                    return "🔴";
                default:
                    return "⚪";
            }
        }

        private String formatStatut(String statut) {
            switch (statut) {
                case "EN_COURS":
                    return "En cours";
                case "RETOURNE":
                    return "Retourné";
                case "EN_RETARD":
                    return "En retard";
                default:
                    return statut;
            }
        }
    }
}

