package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.taskmaster.R;

public class AllTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        Button goHomeButton = AllTasksActivity.this.findViewById(R.id.allTasksActivityHomeButton);

        goHomeButton.setOnClickListener(view -> {

            Intent goBackToHomePage = new Intent(AllTasksActivity.this, MainActivity.class);
            startActivity(goBackToHomePage);
        });
    }
}