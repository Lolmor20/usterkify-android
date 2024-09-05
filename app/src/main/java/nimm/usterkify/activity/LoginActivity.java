package nimm.usterkify.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import nimm.usterkify.AppMode;
import nimm.usterkify.R;
import nimm.usterkify.UserSessionInfo;
import nimm.usterkify.UsterkifyAppContext;
import nimm.usterkify.UsterkifySharedPreferences;
import nimm.usterkify.activity.main.MainActivity;
import nimm.usterkify.data.ObjectBoxRepository;
import nimm.usterkify.data.User;
import nimm.usterkify.data.User_;

public class LoginActivity extends AppCompatActivity {
    private BoxStore boxStore;

    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context context = getApplicationContext();
        boxStore = ObjectBoxRepository.getInstance().getBoxStore(getApplicationContext());

        Button loginButton = findViewById(R.id.localModeLoginButton);
        Button registerButton = findViewById(R.id.registerButton);
        Button noLoginButton = findViewById(R.id.goToAppButton);

        UsterkifySharedPreferences preferences = UsterkifySharedPreferences.getInstance();

        if (preferences.appMode(context).map(mode -> mode == AppMode.LOCAL).orElse(false)) {
            proceedToMainScreen();
            finish();
        }

        loginButton.setOnClickListener(v -> {
            if (logIn()) {
                UsterkifyAppContext.getInstance().setUserSessionInfo(new UserSessionInfo(true, loggedInUser));
                Toast.makeText(context, context.getString(R.string.successful_login), Toast.LENGTH_SHORT).show();
                proceedToMainScreen();
                finish();
            } else {
                Toast.makeText(context, context.getString(R.string.unsuccessful_login), Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        noLoginButton.setOnClickListener(v -> {
            preferences.setAppMode(context, AppMode.LOCAL);
            Toast.makeText(LoginActivity.this, context.getString(R.string.app_in_local_mode_msg), Toast.LENGTH_SHORT).show();
            proceedToMainScreen();
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UsterkifyAppContext.getInstance().getUserSessionInfo().isUserLoggedIn()) {
            proceedToMainScreen();
        }
    }

    private void proceedToMainScreen() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean logIn() {
        EditText emailView = findViewById(R.id.editTextEmail);
        EditText passwordView = findViewById(R.id.editTextPassword);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean errorsExist = false;

        if (email.isBlank()) {
            errorsExist = true;
            emailView.setError(getString(R.string.email_blank_error));
        }

        if (password.isBlank()) {
            errorsExist = true;
            passwordView.setError(getString(R.string.password_blank_error));
        }

        if (errorsExist) {
            return false;
        }

        Box<User> box = boxStore.boxFor(User.class);
        Query<User> query = box.query(User_.email.equal(email)).build();
        User user = query.findUnique();
        if (user == null) {
            return false;
        }
        if (Objects.equals(user.getPassword(), password)) {
            loggedInUser = user;
            return true;
        } else return false;
    }
}
