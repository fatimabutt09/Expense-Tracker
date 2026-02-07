package com.example.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure you have an activity_splash.xml layout with your logo
        setContentView(R.layout.activity_splash);

        // Delay for 2 seconds to show branding
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Initialize Firebase Auth
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                // User is already logged in, go to Dashboard (MainActivity)
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                // No user logged in, go to Login screen
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            finish(); // Close SplashActivity so user can't go back to it
        }, 2000);
    }
}