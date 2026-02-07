package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

// Firebase Imports
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class AddExpenseActivity extends AppCompatActivity {

    EditText amountInput, noteInput;
    Spinner categorySpinner;
    Button saveBtn;

    // Firebase variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        amountInput = findViewById(R.id.amountInput);
        noteInput = findViewById(R.id.noteInput);
        categorySpinner = findViewById(R.id.categorySpinner);
        saveBtn = findViewById(R.id.saveBtn);

        String[] categories = {"Food", "Transport", "Bills", "Shopping", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        saveBtn.setOnClickListener(v -> {
            saveExpenseToFirebase();
        });
    }

    private void saveExpenseToFirebase() {
        String amountStr = amountInput.getText().toString().trim();
        String note = noteInput.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();
        String userId = mAuth.getCurrentUser().getUid(); // Get unique user ID

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        long timestamp = System.currentTimeMillis();

        // Create a unique key for this expense entry
        String expenseId = mDatabase.child("users").child(userId).child("expenses").push().getKey();

        // Structure the data for Firebase
        Map<String, Object> expenseData = new HashMap<>();
        expenseData.put("amount", amount);
        expenseData.put("category", category);
        expenseData.put("note", note);
        expenseData.put("timestamp", timestamp);

        if (expenseId != null) {
            mDatabase.child("users").child(userId).child("expenses").child(expenseId)
                    .setValue(expenseData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AddExpenseActivity.this, "Expense Saved to Cloud!", Toast.LENGTH_SHORT).show();
                        finish(); // Go back
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddExpenseActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}