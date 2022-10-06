package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.example.taskmaster.R;
import com.amplifyframework.datastore.generated.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTaskActivity extends AppCompatActivity {
    public static final String Tag = "AddTaskActivity";

    Spinner teamSpinner = null;
    CompletableFuture<List<Team>> teamFuture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        setUpTypeSpinner();
        setUpSubmitBttn();

    }

    private void setUpTeamSpinner(){
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(Tag, "Read Team successfully");
                    ArrayList<String> teamName = new ArrayList<>();
                    ArrayList<Team> teams = new ArrayList<>();
                    for(Team team : success.getData()){
                        teams.add(team);
                        teamName.add(team.getName());
                    }
                    teamFuture.complete(teams);
                    runOnUiThread(() -> {
                        teamSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                teamName));
                    });
                },
                failure -> {
                    teamFuture.complete(null);
                    Log.i(Tag, "Did not read Teams successfully");
                }
        );
    }

    private void setUpTypeSpinner() {
        Spinner taskTypeSpinner = findViewById(R.id.addTaskTypeSpinner);
        taskTypeSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,

                TaskTypeEnum.values()
        ));

    }

    private void setUpSubmitBttn() {
        String selectedTeamString = teamSpinner.getSelectedItem().toString();
        List<Team> teams = null;
        try{
            teams = teamFuture.get();
        } catch (InterruptedException ie){
            Log.e(Tag, "Interrupted Exception while getting teams");
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee){
            Log.e(Tag,  "ExecutionException while getting team");
        }

        Team selectedTeam = teams.stream().filter(t -> t.getName().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);

        Spinner taskTypeSpinner = findViewById(R.id.addTaskTypeSpinner);
        Button saveNewTaskBttn = findViewById(R.id.addTaskTitleSubmitBttn);
        saveNewTaskBttn.setOnClickListener(view -> {

            Context submitted = getApplicationContext();
            CharSequence text = "Task Submitted!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(submitted, text, duration);
            toast.show();

            String taskName = ((EditText) findViewById(R.id.addTaskTitleInputText)).getText().toString();
            String taskBody = ((EditText) findViewById(R.id.addTaskDescriptionInputText)).getText().toString();
            String taskState = ((EditText) findViewById(R.id.addTaskStatusInputText)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
//            Task.TaskTypeEnum taskTypeEnum = Task.TaskTypeEnum.fromString(taskTypeSpinner.getSelectedItem().toString());

            Task newTask = Task.builder()
                    .title(taskName)
                    .body(taskBody)
                    .state(taskState)
                    .type((TaskTypeEnum) taskTypeSpinner.getSelectedItem())
                    .team(selectedTeam)
                    .dateCreated(new Temporal.DateTime(currentDateString))
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    successResponse -> Log.i(Tag, "AddTaskActivity: made a Task Successfully"),
                    failureResponse -> Log.i(Tag, "AddTaskActivity: failed with this response" + failureResponse)
            );


//            taskDatabase.taskDao().insertTask(newTask);

            Intent goToMainActivity = new Intent(AddTaskActivity.this, MainActivity.class);
            startActivity(goToMainActivity);
        });
    }
}