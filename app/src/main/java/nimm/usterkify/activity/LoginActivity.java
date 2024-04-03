package nimm.usterkify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nimm.usterkify.R;
import nimm.usterkify.activity.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private boolean isLoggedIn = false; // Zmienna przechowująca informację o zalogowaniu użytkownika

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        Button noLoginButton = findViewById(R.id.goToAppButton);

        loginButton.setOnClickListener(v -> {
            // Tutaj można dodać logikę weryfikacji logowania
            // W tym przykładzie zakładamy, że logowanie jest zakończone sukcesem
            isLoggedIn = true;
            Toast.makeText(LoginActivity.this, "Zalogowano pomyślnie!", Toast.LENGTH_SHORT).show();
            proceedToMainScreen();
        });

        registerButton.setOnClickListener(v -> {
            // Przejście do ekranu rejestracji
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        noLoginButton.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Application works in local mode. You can log in at any time.", Toast.LENGTH_SHORT).show();
            proceedToMainScreen();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Sprawdź, czy użytkownik jest zalogowany
        if (isLoggedIn) {
            proceedToMainScreen();
        }
    }

    // Metoda przechodzenia do ekranu głównego
    private void proceedToMainScreen() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Zamyka aktywność logowania, aby użytkownik nie mógł do niej wrócić przyciskiem cofania
    }
}
