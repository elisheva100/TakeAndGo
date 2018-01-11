package com.example.owner.takeandgouser.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.takeandgouser.R;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.model.entities.Client;

import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }


    private EditText nameEditText;
    private EditText idEditText;
    private Button saveButton;
    private Button loadButton;
    private Button clearButton;
    private Button loginButton;
    private TextView AskUserView;
    private TextView goToRegisterView;
    private boolean isTextChanged;
    private  String client_id;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-11 20:34:35 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        //activityMain = (LinearLayout)findViewById( R.id.activity_menu );
        nameEditText = (EditText)findViewById( R.id.nameEditText );
        idEditText = (EditText)findViewById( R.id.idEditText );
        saveButton = (Button)findViewById( R.id.saveButton );
        loadButton = (Button)findViewById( R.id.loadButton );
        clearButton = (Button)findViewById( R.id.clearButton );
        loginButton = (Button)findViewById( R.id.loginButton );
        AskUserView = (TextView)findViewById( R.id.AskUserView );
        goToRegisterView = (TextView)findViewById( R.id.goToRegisterView );

        saveButton.setOnClickListener( this );
        loadButton.setOnClickListener( this );
        clearButton.setOnClickListener( this );
        loginButton.setOnClickListener( this );
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
        if ( v == saveButton ) {
            saveSharedPreferences();
            isTextChanged = true;
        } else if ( v == loadButton ) {
            loadSharedPreferences();
        } else if ( v == clearButton ) {
            clearSharedPreferences();
        } else if ( v == loginButton ) {
            Login(); // Handle clicks for loginButton
        }
        else if ( v == goToRegisterView ) {
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
            Toast.makeText(this, "load id", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSharedPreferences() {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String name = nameEditText.getText().toString();
            String id = idEditText.getText().toString();

            editor.putString("NAME", name);
            editor.putString("ID", id);
            editor.commit();

            client_id = id;
            Toast.makeText(this, "save name and id Preferences", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "failed to save Preferences", Toast.LENGTH_SHORT).show();
        }
    }

    private void Register() {
        Intent intent = new Intent(this,AddClientActivity.class);
        startActivity(intent);
    }
//start fix
    private void Login() {
        if ( isTextChanged){
            //if(validateInput()) {
                //if ( saveSharedPreferences().isChecked())
            validateInput();
                saveSharedPreferences();
                goToMenu();
            //}
        }
        else {
           // if( saveSharedPreferences().isChecked())
            saveSharedPreferences();
            goToMenu();
        }
    }

    /*private boolean validateInput() {
        String message = null;

       /* String name = nameEditText.getText().toString();
        String id = idEditText.getText().toString();
        if(!manager.existUserName(name))
            message = "this user name does not exist in the system";
        if(manager.findClientByUser(name) == null)
            message = "this user name is not associated with any client";
        else
            idToPass = manager.findClientByUser(userName).get_id();
        if(manager.userClientMatch(userName,password) == -1)
            message = "password does not match user name";
        if(message == null)
            return true;
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        return false;
    }*/

    private void goToMenu() {
        Intent intent = new Intent(this,MenuActivity.class);
        intent.putExtra("CLIENT_ID",client_id);
        startActivity(intent);
    }


    public void validateInput() {
        try {
            new AsyncTask<Void, Void, Boolean>() {

                @Override
                protected void onPostExecute(final Boolean isExist) {
                    //super.onPostExecute(isExist);
                     if (!isExist) {
                         clearSharedPreferences();
                         Toast.makeText(LoginActivity.this, "Your details doesn't exist in the system, please registe first", Toast.LENGTH_LONG).show();
                     }
                }

                @Override
                protected Boolean doInBackground(Void... params) {
                    try {
                        return DBManagerFactory.getManager().isExistClient(client_id);
                    } catch (Exception e) {
                        return false;
                    }
                }

            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
