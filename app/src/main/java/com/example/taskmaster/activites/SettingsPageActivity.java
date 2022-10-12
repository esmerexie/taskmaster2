package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.taskmaster.activites.AddTaskActivity.Tag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.taskmaster.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsPageActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public static final String USER_NAME_TAG = "userName";
    public static final String USER_TEAM_TAG = "teamName";

    List<String> teamNames = null;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString(USER_NAME_TAG, "");

        if (!userName.isEmpty()) {
            EditText userNameEdited = findViewById(R.id.settingsPageActivityInputName);
            userNameEdited.setText(userName);
        }

        teamNames = new ArrayList<>();
        setUpSubmitButton();
        setUpTeamSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(Tag, "Read Task successfully");
                    teamNames.clear();
                    for (Team dataBaseTeam : success.getData()) {
                        teamNames.add(dataBaseTeam.getName());
                    }
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                },
                failureResponse -> Log.i(Tag, "Did not read Task successfully")

        );
    }


    private void setUpSubmitButton() {

        Button submitButton = findViewById(R.id.settingsPageActivityUpdateButton);
        Spinner userTeamSpinner = findViewById(R.id.settingsPageActivitySpinner);

        submitButton.setOnClickListener(view -> {

            SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();

            String teamName = ((EditText) findViewById(R.id.settingsPageInputTeamName)).getText().toString();

            String nameInput = ((EditText) findViewById(R.id.settingsPageActivityInputName)).getText().toString();
            preferenceEditor.putString(USER_NAME_TAG, nameInput);

            String teamInput = ((String) userTeamSpinner.getSelectedItem());
            preferenceEditor.putString(USER_TEAM_TAG, teamInput);

            Team newTeam = Team.builder()
                    .name(teamName)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newTeam),
                    successResponse -> Log.i(Tag, "new team added!"),
                    failureResponse -> Log.i(Tag, "team has failed to be added!")
            );

            preferenceEditor.apply();

            Toast.makeText(SettingsPageActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();
            Intent goToMainActivity = new Intent(SettingsPageActivity.this, MainActivity.class);
            goToMainActivity.putExtra(SettingsPageActivity.USER_NAME_TAG, nameInput);
            startActivity(goToMainActivity);
        });
    }

    private void setUpTeamSpinner() {
        Spinner userTeamSpinner = findViewById(R.id.settingsPageActivitySpinner);
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teamNames);
        userTeamSpinner.setAdapter(adapter);
    }

}