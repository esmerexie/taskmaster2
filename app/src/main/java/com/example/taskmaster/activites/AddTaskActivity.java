package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.taskmaster.R;
import com.example.taskmaster.model.Task;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "task_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        setUpTypeSpinner();
        setUpSubmitBttn();

    }

    private void setUpTypeSpinner() {
        Spinner taskTypeSpinner = findViewById(R.id.addTaskTypeSpinner);
        taskTypeSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                Task.TaskTypeEnum.values()
        ));

    }

    private void setUpSubmitBttn() {
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
            java.util.Date newDate = new Date();
            Task.TaskTypeEnum taskTypeEnum = Task.TaskTypeEnum.fromString(taskTypeSpinner.getSelectedItem().toString());

            Task newTask = new Task(taskName, taskBody, taskState, taskTypeEnum, newDate);

//            taskDatabase.taskDao().insertTask(newTask);

            Intent goToMainActivity = new Intent(AddTaskActivity.this, MainActivity.class);
            startActivity(goToMainActivity);
        });
    }
}