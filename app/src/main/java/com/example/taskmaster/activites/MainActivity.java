package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.taskmaster.R;
import com.example.taskmaster.adapter.TaskListRecylerViewAdapter;
import com.example.taskmaster.database.TaskDatabase;
import com.example.taskmaster.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "task_db";
    public static final String PRODUCT_NAME_EXTRA_TAG = "productName";
    SharedPreferences sharedPreferences;
    TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        taskDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        TaskDatabase.class,
                        DATABASE_NAME
                )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        taskDatabase.taskDao().findAll();

        createAddTaskButton();
        createAllTaskButton();
        createTaskOneButton();
        createSettingsButton();
        setUpTaskRecyclerView();
    }

    private void setUpTaskRecyclerView() {
        RecyclerView taskRecyclerView = findViewById(R.id.mainActivityRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(layoutManager);

        List<Task> tasks = taskDatabase.taskDao().findAll();

        TaskListRecylerViewAdapter adapter = new TaskListRecylerViewAdapter(tasks);
        taskRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userName = sharedPreferences.getString(SettingsPageActivity.USER_NAME_TAG, "No username");
        TextView userNameEdited = findViewById(R.id.mainActivityUsernameTextView);
        userNameEdited.setText(userName + "'s Tasks");
    }


    private void createAddTaskButton() {
        Button addTaskBttn = MainActivity.this.findViewById(R.id.mainActivityAddTaskButton);

        addTaskBttn.setOnClickListener(view -> {

            Intent goToAddTaskPage = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(goToAddTaskPage);
        });
    }

    private void createAllTaskButton() {
        Button allTaskBttn = MainActivity.this.findViewById(R.id.MainActivityAllTaskButton);

        allTaskBttn.setOnClickListener(view -> {

            Intent goToAllTasksPage = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(goToAllTasksPage);
        });
    }

    private void createTaskOneButton() {
        Button taskOneButton = MainActivity.this.findViewById(R.id.mainActivityTaskOneButton);

        taskOneButton.setOnClickListener(view -> {
            Intent goToTaskPage = new Intent(MainActivity.this, TaskDetailPageActivity.class);
            startActivity(goToTaskPage);
        });
    }

    private void createSettingsButton() {
        Button settingsButton = MainActivity.this.findViewById(R.id.mainActivitySettingsButton);

        settingsButton.setOnClickListener(view -> {
            Intent goToSettingsPage = new Intent(MainActivity.this, SettingsPageActivity.class);
            startActivity(goToSettingsPage);
        });
    }


}

