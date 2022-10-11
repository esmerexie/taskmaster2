package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.taskmaster.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsPageActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public static final String USER_NAME_TAG = "userName";

    List<String> teamNames = null;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString(USER_NAME_TAG, "");

        if(!userName.isEmpty()){
            EditText userNameEdited = findViewById(R.id.settingsPageActivityInputName);
            userNameEdited.setText(userName);
        }

        teamNames = new ArrayList<>();
        setUpSubmitButton();
    }


    private void setUpSubmitButton(){

        Button submitButton = findViewById(R.id.settingsPageActivityUpdateButton);

        submitButton.setOnClickListener(view -> {

            SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
            String nameInput = ((EditText) findViewById(R.id.settingsPageActivityInputName)).getText().toString();

            preferenceEditor.putString(USER_NAME_TAG, nameInput);

            Toast.makeText(SettingsPageActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();
        });
    }
}