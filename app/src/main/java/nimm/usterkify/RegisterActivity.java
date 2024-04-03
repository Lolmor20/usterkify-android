package nimm.usterkify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);

        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            // Tutaj możesz dodać logikę rejestracji
            // W tym przykładzie zakładamy, że rejestracja jest zakończona sukcesem
            registerSuccess();
        });
    }

    private void registerSuccess() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Tutaj możesz zapisać dane rejestracji lub wykonać inne czynności po rejestracji

        Toast.makeText(this, "Rejestracja zakończona sukcesem!", Toast.LENGTH_SHORT).show();
        proceedToLoginScreen();
    }

    private void proceedToLoginScreen() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Zamyka aktywność rejestracji, aby użytkownik nie mógł do niej wrócić przyciskiem cofania
    }
}
