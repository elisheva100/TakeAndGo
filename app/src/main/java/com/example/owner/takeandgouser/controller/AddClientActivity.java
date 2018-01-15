package com.example.owner.takeandgouser.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.owner.takeandgouser.model.backEnd.AgencyConsts;
import com.example.owner.takeandgouser.model.backEnd.DBManagerFactory;
import com.example.owner.takeandgouser.R;

import java.util.Calendar;

public class AddClientActivity extends AppCompatActivity implements View.OnClickListener {
    int myYear, myMonth, myDay; //The user's birthday.
    static final int DIALOG = 0;
    static boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setLogo(R.mipmap.my_car);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_add_client);
        Calendar calender = Calendar.getInstance();
        //myDay = calender.get(Calendar.DAY_OF_MONTH);
        //myMonth = calender.get(Calendar.MONTH);
        //myYear = calender.get(Calendar.YEAR);

        myDay = 27;
        myMonth = 6;
        myYear = 1990;
        findViews();
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG) {
            return new DatePickerDialog(this, dPickerListener, myYear, myMonth, myDay);
        }
        return null;
    }

    private EditText clientIdEditText;
    private EditText FirstNameEditText;
    private EditText LastNameEditText;
    private EditText PhoneNumberEditText;
    private EditText EmailEditText;
    private TextView birthdayTextView;
    private Button datePickerButton;
    private EditText CreditCardEditText;
    private Button addClientButton;
    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        /**
         * Gets the user's date.
         */
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myYear = year;
            myMonth = month;
            myDay = dayOfMonth;
            Calendar calender_1 = Calendar.getInstance();
            int cDay = calender_1.get(Calendar.DAY_OF_MONTH);
            int cMonth = calender_1.get(Calendar.MONTH);
            int cYear = calender_1.get(Calendar.YEAR);
            if (cYear - myYear  < 18) {
                flag = false;//The user is younger than 18.
            } else if (myYear == (cYear- 18 ) && myMonth < cMonth) {
                flag = false; //The user is 18 this year but next months.
            } else if (myYear == (cYear - 18) && myMonth == cMonth && myDay < cDay) {
                flag = false; //The user is 18 this month but the next days.
            }
            Toast.makeText(getApplicationContext(), myDay + "/" + myMonth + "/" + myYear, Toast.LENGTH_LONG).show();

        }
    };

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-12-06 17:20:57 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        clientIdEditText = (EditText) findViewById(R.id.clientIdEditText);
        FirstNameEditText = (EditText) findViewById(R.id.FirstNameEditText);
        LastNameEditText = (EditText) findViewById(R.id.LastNameEditText);
        PhoneNumberEditText = (EditText) findViewById(R.id.PhoneNumberEditText);
        EmailEditText = (EditText) findViewById(R.id.EmailEditText);
        birthdayTextView = (TextView) findViewById(R.id.birthdayTextView);
        datePickerButton = (Button) findViewById(R.id.datePickerButton);
        CreditCardEditText = (EditText) findViewById(R.id.CreditCardEditText);
        addClientButton = (Button) findViewById(R.id.addClientButton);
        datePickerButton.setOnClickListener(this);
        addClientButton.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-12-06 17:20:57 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == datePickerButton) {
            showDialog(DIALOG);
        } else if (v == addClientButton) {
            addClient();
            /*
            clientIdEditText.getText().clear();
            FirstNameEditText.getText().clear();
            LastNameEditText.getText().clear();
            PhoneNumberEditText.getText().clear();
            EmailEditText.getText().clear();
            CreditCardEditText.getText().clear();
            */
        }
    }

    private void addClient() {
        final ContentValues contentValues = new ContentValues();
        String strException = "";
        try {
            if (Legal.legalId(clientIdEditText.getText().toString())) {
                String _id = (this.clientIdEditText.getText().toString());
                contentValues.put(AgencyConsts.ClientConst.ID, _id);
            } else {
                //Toast.makeText(AddClientActivity.this, "Client id is not legal!", Toast.LENGTH_LONG).show();
                strException += "Client's id is not legal!\n";
            }
            if (Legal.isString(FirstNameEditText.getText().toString())) {
                String firstName = (this.FirstNameEditText.getText().toString());
                contentValues.put(AgencyConsts.ClientConst.FIRST_NAME, firstName);
            } else {
                //Toast.makeText(AddClientActivity.this, "Client first name is not valid!", Toast.LENGTH_LONG).show();
                strException += "Client's first name is not valid!!\n";
            }
            if (Legal.isString(LastNameEditText.getText().toString())) {
                String lastName = (this.LastNameEditText.getText().toString());
                contentValues.put(AgencyConsts.ClientConst.LAST_NAME, lastName);
            } else {
                //Toast.makeText(AddClientActivity.this, "Client last name is not valid!", Toast.LENGTH_LONG).show();
                strException += "Client's last name is not valid!!\n";
            }
            if (Legal.isCellPhone(PhoneNumberEditText.getText().toString())) {
                String phoneNumber = (this.PhoneNumberEditText.getText().toString());
                contentValues.put(AgencyConsts.ClientConst.CELLPHONE, phoneNumber);
            } else {
                //Toast.makeText(AddClientActivity.this, "Client phone number is not valid!", Toast.LENGTH_LONG).show();
                strException += "Client's phone number is not valid!!\n";
            }
            if (Legal.isEmailAddres(EmailEditText.getText().toString())) {
                String email = (this.EmailEditText.getText().toString());
                contentValues.put(AgencyConsts.ClientConst.EMAIL, email);
            } else {
                //Toast.makeText(AddClientActivity.this, "Client email address is not valid!", Toast.LENGTH_LONG).show();
                strException += "Client's email address is not valid!\n";
            }
            if (flag) {
                String birthday = Integer.toString(myYear) + "-" + Integer.toString(myMonth) + "-" + Integer.toString(myDay);
                contentValues.put(AgencyConsts.ClientConst.BIRTHDAY, birthday);
            } else {
                Toast.makeText(AddClientActivity.this, "Client is to young for renting a car!", Toast.LENGTH_LONG).show();
                return;
            }
            if (Legal.isNum(CreditCardEditText.getText().toString())) {
                long creditCard = Long.valueOf(this.CreditCardEditText.getText().toString());
                contentValues.put(AgencyConsts.ClientConst.CREDIT_CARD, creditCard);
            } else {
                //Toast.makeText(AddClientActivity.this, "Client credit card is not valid!", Toast.LENGTH_LONG).show();
                strException += "Client's credit card is not valid!\n";

            }
            if(strException != "")
            {
                Toast.makeText(AddClientActivity.this, strException, Toast.LENGTH_LONG).show();
                return;
            }

            new AsyncTask<Void, Void, String>()
            {
                String strError = "";

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    if (result != null)
                        Toast.makeText(AddClientActivity.this, "client: " + result + " added successfully", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(AddClientActivity.this, "Insert failed\n" + strError, Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                protected String doInBackground(Void... params) {
                    try {
                        return String.valueOf(DBManagerFactory.getManager().addClient(contentValues));
                    } catch (Exception e) {
                        strError = e.getMessage();
                        //Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }.execute();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}
