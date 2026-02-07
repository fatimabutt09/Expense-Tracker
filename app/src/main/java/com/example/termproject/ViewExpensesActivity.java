package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewExpensesActivity extends AppCompatActivity {

    ListView expenseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        expenseListView = findViewById(R.id.expenseListView);

        SharedPreferences prefs = getSharedPreferences("expenses", MODE_PRIVATE);
        String data = prefs.getString("data", "");

        ArrayList<String> expenseList = new ArrayList<>();

        if (!data.isEmpty()) {
            expenseList.addAll(Arrays.asList(data.split(";")));
        } else {
            expenseList.add("No expenses added yet");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                expenseList
        );

        expenseListView.setAdapter(adapter);
    }
}
