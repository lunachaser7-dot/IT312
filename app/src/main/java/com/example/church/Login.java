package com.example.church;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ImageView logoImage;
    private TextView txtWelcome, txtFooter;
    private TextInputLayout emailLayout, passwordLayout;
    private CardView loginCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        logoImage = findViewById(R.id.logoImage);
        txtWelcome = findViewById(R.id.txtWelcome);
        txtFooter = findViewById(R.id.txtFooter);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        loginCard = findViewById(R.id.loginCard);

        // Start Animations
        applyAnimations();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    etPassword.setError("Password is required");
                    etPassword.requestFocus();
                    return;
                }

                performLogin(email, password);
            }
        });
    }

    private void performLogin(String email, String password) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        LoginRequest request = new LoginRequest(email, password);

        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // HTTP 200 - Successful login
                    Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    // HTTP 401 or other errors
                    Toast.makeText(Login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyAnimations() {
        // Set initial states
        loginCard.setAlpha(0f);
        loginCard.setTranslationY(100f);
        
        logoImage.setAlpha(0f);
        logoImage.setScaleX(0.5f);
        logoImage.setScaleY(0.5f);
        
        txtWelcome.setAlpha(0f);
        emailLayout.setAlpha(0f);
        passwordLayout.setAlpha(0f);
        btnLogin.setAlpha(0f);
        txtFooter.setAlpha(0f);

        // Animate Login Card
        loginCard.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(800)
                .setStartDelay(200)
                .start();

        // Animate Logo (Pop-in effect)
        logoImage.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(600)
                .setStartDelay(500)
                .start();

        // Staggered entry for inputs
        txtWelcome.animate().alpha(1f).setDuration(500).setStartDelay(700).start();
        emailLayout.animate().alpha(1f).setDuration(500).setStartDelay(800).start();
        passwordLayout.animate().alpha(1f).setDuration(500).setStartDelay(900).start();
        btnLogin.animate().alpha(1f).setDuration(500).setStartDelay(1000).start();
        txtFooter.animate().alpha(1f).setDuration(500).setStartDelay(1200).start();
    }
}
