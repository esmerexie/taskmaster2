package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AmplifyModelProvider;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.taskmaster.R;
import com.example.taskmaster.adapter.TaskListRecylerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String Tag = "TaskActivity";
    public static final String PRODUCT_NAME_EXTRA_TAG = "productName";
    public static final String TAG = "MainActivity";
    SharedPreferences sharedPreferences;
    List<Task> taskList = null;
    TaskListRecylerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        taskList = new ArrayList<>();

        createAddTaskButton();
        createAllTaskButton();
        createTaskOneButton();
        createSettingsButton();
        setUpTaskRecyclerView();

        Team newTeam = Team.builder()
                .name("Team Coders")
                .build();
        Amplify.API.mutate(
                ModelMutation.create(newTeam),
                success -> Log.i(TAG, "Worked"),
                failure -> Log.i(TAG, "Did not work")
        );
    }

    private void setUpTaskRecyclerView() {
        RecyclerView taskRecyclerView = findViewById(R.id.mainActivityRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(layoutManager);


        adapter = new TaskListRecylerViewAdapter(taskList, this);
        taskRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userName = sharedPreferences.getString(SettingsPageActivity.USER_NAME_TAG, "No username");
        TextView userNameEdited = findViewById(R.id.mainActivityUsernameTextView);
        userNameEdited.setText(userName + "'s Tasks");

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(Tag, "Read Task successfully");
                    taskList.clear();
                    for (Task dataBaseTask : success.getData()) {
                        taskList.add(dataBaseTask);
                    }
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                },
                failureResponse -> Log.i(Tag, "Did not read Task successfully")

        );

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

