package bibliotheque.collegeuniversel.bibliotheque;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<Book> booksList;
    private OnBookClickListener listener;

    // Interface pour gérer les clics sur les livres
    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    // Constructeur
    public BooksAdapter(OnBookClickListener listener) {
        this.booksList = new ArrayList<>();
        this.listener = listener;
    }

    // Créer un nouveau ViewHolder
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    // Lier les données au ViewHolder
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = booksList.get(position);
        holder.bind(book, listener);
    }

    // Retourner le nombre d'éléments
    @Override
    public int getItemCount() {
        return booksList.size();
    }

    // Mettre à jour la liste des livres
    public void setBooks(List<Book> books) {
        this.booksList = books != null ? books : new ArrayList<>();
        notifyDataSetChanged();
    }

    // Obtenir la liste actuelle
    public List<Book> getBooks() {
        return booksList;
    }

    // ViewHolder : contient les vues de chaque élément
    static class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBookTitle;
        private TextView tvBookAuthor;
        private TextView tvBookMeta;
        private TextView tvBookStatus;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookAuthor = itemView.findViewById(R.id.tvBookAuthor);
            tvBookMeta = itemView.findViewById(R.id.tvBookMeta);
            tvBookStatus = itemView.findViewById(R.id.tvBookStatus);
        }

        public void bind(Book book, OnBookClickListener listener) {
            // Titre avec émoji
            tvBookTitle.setText("📘  " + (book.getTitre() != null ? book.getTitre() : "Sans titre") + "    ⭐");
            
            // Auteur avec émoji
            tvBookAuthor.setText((book.getAuteur() != null ? book.getAuteur() : "Auteur inconnu") + "    ♡");
            
            // Catégorie et type
            String categorie = book.getCategorie() != null ? book.getCategorie() : "N/A";
            String type = book.getType() != null ? book.getType() : "N/A";
            tvBookMeta.setText(categorie + " • " + type);
            
            // Statut avec indicateur de couleur ET nombre de copies disponibles
            String dot = "🟢";
            String statut = book.getStatutDisponibilite() != null ? book.getStatutDisponibilite() : "inconnu";
            Integer copiesDisponibles = book.getCopiesDisponibles() != null ? book.getCopiesDisponibles() : 0;
            Integer copiesTotal = book.getNombreCopiesTotal() != null ? book.getNombreCopiesTotal() : 0;
            
            if (statut.toLowerCase().contains("emprunté") || statut.toLowerCase().contains("emprunte")) {
                dot = "🟠";
            } else if (statut.toLowerCase().contains("réservé") || statut.toLowerCase().contains("reserve")) {
                dot = "🔴";
            }
            
            // Afficher le statut avec le nombre de copies
            String statusText = dot + " " + statut + " (" + copiesDisponibles + "/" + copiesTotal + " disponibles)";
            tvBookStatus.setText(statusText);
            
            // Gérer le clic sur l'élément
            itemView.setOnClickListener(v -> {
                if (listener != null && book.getId() != null) {
                    listener.onBookClick(book);
                }
            });
        }
    }
}

