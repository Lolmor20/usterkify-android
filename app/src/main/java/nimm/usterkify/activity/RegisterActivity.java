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

        // TODO validation

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
}
