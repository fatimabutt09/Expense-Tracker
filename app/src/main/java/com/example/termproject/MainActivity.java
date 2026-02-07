package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    TextView totalText;
    Button addExpenseBtn, viewExpensesBtn;  // 👈 new button added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalText = findViewById(R.id.totalText);
        addExpenseBtn = findViewById(R.id.addExpenseBtn);
        viewExpensesBtn = findViewById(R.id.viewExpensesBtn);  // 👈 findViewById for view button

        // 👇 Add Expense button logic
        addExpenseBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        // 👇 View Expenses button logic
        viewExpensesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewExpensesActivity.class);
            startActivity(intent);
        });
    }
}
