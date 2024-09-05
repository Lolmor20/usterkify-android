package nimm.usterkify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import nimm.usterkify.R;
import nimm.usterkify.data.ObjectBoxRepository;
import nimm.usterkify.data.User;
import nimm.usterkify.data.User_;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private BoxStore boxStore;

    private final int MIN_PASS_LENGTH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        boxStore = ObjectBoxRepository.getInstance().getBoxStore(getApplicationContext());

        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);

        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            if (register()) {
                Toast.makeText(this, getString(R.string.register_successful), Toast.LENGTH_SHORT).show();
                proceedToLoginScreen();
            } else {
                Toast.makeText(this, getString(R.string.registration_unsuccessful), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean register() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!validateForm(firstName, lastName, email, password)) {
            return false;
        }

        User user = new User(0, firstName, lastName, email, password, Calendar.getInstance().getTime());
        Box<User> box = boxStore.boxFor(User.class);
        box.put(user);
        return true;
    }

    private void proceedToLoginScreen() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateForm(String firstName, String lastName, String email, String password) {
        boolean errorsExist = false;

        if (firstName.isBlank()) {
            errorsExist = true;
            firstNameEditText.setError(getString(R.string.first_name_blank_error));
        }

        if (lastName.isBlank()) {
            errorsExist = true;
            lastNameEditText.setError(getString(R.string.last_name_blank_error));
        }

        if (email.isBlank()) {
            errorsExist = true;
            emailEditText.setError(getString(R.string.email_blank_error));
        }

        if (password.isBlank()) {
            errorsExist = true;
            passwordEditText.setError(getString(R.string.password_blank_error));
        } else if (password.length() < MIN_PASS_LENGTH) {
            errorsExist = true;
            passwordEditText.setError(getString(R.string.password_too_short_error, MIN_PASS_LENGTH));
        }

        return !errorsExist;
    }
}
