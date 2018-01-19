package com.example.owner.takeandgouser.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.model.entities.Client;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }


    // private LinearLayout activityMain;
    private EditText nameEditText;
    private EditText idEditText;
    //private Button saveButton;
    //private Button loadButton;
    //private Button clearButton;
    private CheckBox loadCheckBox;
    private CheckBox clearCheckBox;
    private Button loginButton;
    private TextView AskUserView;
    private TextView goToRegisterView;
    public String client_id;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-11 20:34:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        //activityMain = (LinearLayout)findViewById( R.id.activity_main );
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        idEditText = (EditText) findViewById(R.id.idEditText);
        //saveButton = (Button) findViewById(R.id.saveButton);
        //loadButton = (Button) findViewById(R.id.loadButton);
        //clearButton = (Button) findViewById(R.id.clearButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        AskUserView = (TextView) findViewById(R.id.AskUserView);
        goToRegisterView = (TextView) findViewById(R.id.goToRegisterView);
        loadCheckBox = (CheckBox)findViewById( R.id.loadCheckBox );
        clearCheckBox = (CheckBox)findViewById( R.id.clearCheckBox );

        //saveButton.setOnClickListener(this);
        //loadButton.setOnClickListener(this);
        //clearButton.setOnClickListener(this);
       clearCheckBox.setOnClickListener(this);
        loadCheckBox.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        goToRegisterView.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-01-11 20:34:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        //if (v == saveButton) {
         //   saveSharedPreferences();
            //isTextChanged = true;
        //} else
        if (v == clearCheckBox) {
            loadCheckBox.setChecked(false);
            clearSharedPreferences();
        } else if (v == loadCheckBox) {
            clearCheckBox.setChecked(false);
            loadSharedPreferences();
        } else if (v == loginButton) {
            Login();
        } else if (v == goToRegisterView) {
            Register();
        }
    }

    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        nameEditText.setText("");
        idEditText.setText("");
        client_id = idEditText.getText().toString();
        Toast.makeText(this, "clear Preferences", Toast.LENGTH_SHORT).show();
    }

    private void loadSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPreferences.contains("NAME")) {
            nameEditText.setText(sharedPreferences.getString("NAME", null));
            Toast.makeText(this, "load name", Toast.LENGTH_SHORT).show();
        }
        if (sharedPreferences.contains("ID")) {
            idEditText.setText(sharedPreferences.getString("ID", null));
            client_id = idEditText.getText().toString();
            Toast.makeText(this, "load id", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSharedPreferences() {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
           // if (sharedPreferences.contains("ID"))
             //   loadSharedPreferences();
           // else {
            String name = nameEditText.getText().toString();
            String id = idEditText.getText().toString();

            editor.putString("NAME", name);
            editor.putString("ID", id);
            editor.commit();

            client_id = id;
            Toast.makeText(this, "save name and id Preferences", Toast.LENGTH_SHORT).show();
           // }
        } catch (Exception ex) {
            Toast.makeText(this, "failed to save Preferences", Toast.LENGTH_SHORT).show();
        }
    }

    private void Register() {
        Intent intent = new Intent(this, AddClientActivity.class);
        startActivity(intent);
    }


    private void Login() {
        saveSharedPreferences();
        new MyAsyncTask().execute(client_id, "");
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("CLIENT_ID", client_id);
        startActivity(intent);
    }


    private class MyAsyncTask extends AsyncTask<String, Void, Boolean> {

       /* protected void fef()
        {
           String id = idEditText.getText().toString();
        }*/
        @Override
        protected void onPostExecute(Boolean isExist) {
            super.onPostExecute(isExist);
            if (isExist == false) {
                Toast.makeText(LoginActivity.this, "Your details doesn't exist in the system, please registe first", Toast.LENGTH_LONG).show();
                clearSharedPreferences();
            }else
                goToMenu();
        }

        @Override
        protected Boolean doInBackground(String... id) {
            try {
               // saveSharedPreferences();
                return DBManagerFactory.getManager().isExistClient(id[0]);
            } catch (Exception e) {
                return false;
            }
        }
    }

}


