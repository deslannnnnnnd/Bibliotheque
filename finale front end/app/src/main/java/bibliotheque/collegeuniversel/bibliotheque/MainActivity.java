package bibliotheque.collegeuniversel.bibliotheque; // <-- adapte au tien

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bibliotheque.collegeuniversel.bibliotheque.LibraryActivity;
import bibliotheque.collegeuniversel.bibliotheque.R;

public class MainActivity extends AppCompatActivity {

    // Sections
    private LinearLayout sectionRole, sectionVisitor, sectionLibrarian, sectionAdmin, sectionRegister, sectionForgot;

    private void showSection(View target) {
        View[] all = new View[]{sectionRole, sectionVisitor, sectionLibrarian, sectionAdmin, sectionRegister, sectionForgot};
        for (View v : all) v.setVisibility(v == target ? View.VISIBLE : View.GONE);
    }
    private void toast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout unique d'authentification

        // Récup sections
        sectionRole      = findViewById(R.id.sectionRole);
        sectionVisitor   = findViewById(R.id.sectionVisitor);
        sectionLibrarian = findViewById(R.id.sectionLibrarian);
        sectionAdmin     = findViewById(R.id.sectionAdmin);
        sectionRegister  = findViewById(R.id.sectionRegister);
        sectionForgot    = findViewById(R.id.sectionForgot);

        // Choix du rôle
        ((Button)findViewById(R.id.btnVisiteur)).setOnClickListener(v -> showSection(sectionVisitor));
        ((Button)findViewById(R.id.btnBibliothecaire)).setOnClickListener(v -> showSection(sectionLibrarian));
        ((Button)findViewById(R.id.btnAdmin)).setOnClickListener(v -> showSection(sectionAdmin));
        
        // Bouton Mes Emprunts
        ((Button)findViewById(R.id.btnMyLoans)).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyLoansActivity.class);
            startActivity(intent);
        });

        // === VISITEUR
        EditText etVisitorName     = findViewById(R.id.etVisitorName);
        EditText etVisitorEmail    = findViewById(R.id.etVisitorEmail);
        EditText etVisitorPassword = findViewById(R.id.etVisitorPassword);

        findViewById(R.id.btnVisitorLogin).setOnClickListener(v -> {
            String name = etVisitorName.getText().toString().trim();
            String email = etVisitorEmail.getText().toString().trim();
            String pass = etVisitorPassword.getText().toString();
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) toast("Remplis tous les champs");
            else if (!email.contains("@")) toast("Email invalide");
            else {
                toast("Connexion visiteur");
                startActivity(new Intent(MainActivity.this, LibraryActivity.class));
            }
        });
        findViewById(R.id.btnVisitorRegister).setOnClickListener(v -> showSection(sectionRegister));
        findViewById(R.id.btnVisitorForgot).setOnClickListener(v -> showSection(sectionForgot));
        findViewById(R.id.btnVisitorBack).setOnClickListener(v -> showSection(sectionRole));

        // === BIBLIOTHÉCAIRE
        EditText etLibrarianName   = findViewById(R.id.etLibrarianName);
        EditText etLibrarianNumber = findViewById(R.id.etLibrarianNumber);

        findViewById(R.id.btnLibrarianLogin).setOnClickListener(v -> {
            String name = etLibrarianName.getText().toString().trim();
            String num  = etLibrarianNumber.getText().toString().trim();
            if (name.isEmpty() || num.isEmpty()) toast("Remplis nom et numéro");
            else {
                toast("Connexion bibliothécaire");
                startActivity(new Intent(MainActivity.this, LibraryActivity.class));
            }
        });
        findViewById(R.id.btnLibrarianBack).setOnClickListener(v -> showSection(sectionRole));

        // === ADMIN
        EditText etAdminCode     = findViewById(R.id.etAdminCode);
        EditText etAdminPassword = findViewById(R.id.etAdminPassword);

        findViewById(R.id.btnAdminLogin).setOnClickListener(v -> {
            String code = etAdminCode.getText().toString().trim();
            String pass = etAdminPassword.getText().toString();
            if (code.isEmpty() || pass.isEmpty()) toast("Remplis code et mot de passe");
            else if (code.equals("1234") && pass.equals("admin")) {
                toast("Connexion admin");
                startActivity(new Intent(MainActivity.this, LibraryActivity.class));
            } else toast("Code ou mot de passe incorrect");
        });
        findViewById(R.id.btnAdminBack).setOnClickListener(v -> showSection(sectionRole));

        // === INSCRIPTION
        EditText etRegName      = findViewById(R.id.etRegName);
        EditText etRegEmail     = findViewById(R.id.etRegEmail);
        EditText etRegPassword  = findViewById(R.id.etRegPassword);
        EditText etRegPassword2 = findViewById(R.id.etRegPassword2);

        findViewById(R.id.btnCreateAccount).setOnClickListener(v -> {
            String name = etRegName.getText().toString().trim();
            String email = etRegEmail.getText().toString().trim();
            String p1 = etRegPassword.getText().toString();
            String p2 = etRegPassword2.getText().toString();
            if (name.isEmpty() || email.isEmpty() || p1.isEmpty() || p2.isEmpty()) toast("Remplis tous les champs");
            else if (!email.contains("@")) toast("Email invalide");
            else if (!p1.equals(p2)) toast("Les mots de passe ne correspondent pas");
            else {
                toast("Compte créé");
                showSection(sectionVisitor);
            }
        });
        findViewById(R.id.btnRegisterBack).setOnClickListener(v -> showSection(sectionVisitor));

        // === MOT DE PASSE OUBLIÉ
        EditText etForgotEmail = findViewById(R.id.etForgotEmail);
        findViewById(R.id.btnSendReset).setOnClickListener(v -> {
            String email = etForgotEmail.getText().toString().trim();
            if (email.isEmpty() || !email.contains("@")) toast("Saisis un email valide");
            else {
                toast("Lien envoyé");
                showSection(sectionVisitor);
            }
        });
        findViewById(R.id.btnForgotBack).setOnClickListener(v -> showSection(sectionVisitor));
    }
}
