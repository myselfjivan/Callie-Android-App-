package com.status.callie.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.status.callie.Model.Register;
import com.status.callie.Model.Response.PregisterResponse;
import com.status.callie.Model.SqliteHelper;
import com.status.callie.R;
import com.status.callie.accounts.AccountConstants;
import com.status.callie.services.CallieSessionManager;

/**
 * Created by jivan.ghadage on 8/11/2016.
 */
public class Pregister extends Activity {
    private static final String TAG = Pregister.class.getSimpleName();
    private Button btnRegister;
    private EditText inputMobileEditText;
    private Spinner countryCodeSpinner;
    private String countryCode;
    private ProgressDialog pDialog;
    private CallieSessionManager session;
    private SqliteHelper db;
    Register register = new Register();
    PregisterResponse pregisterResponse = new PregisterResponse();
    CountryCodePicker ccp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregister);

        btnRegister = (Button) findViewById(R.id.btn_register);
        inputMobileEditText = (EditText) findViewById(R.id.mobile);
        //countryCodeSpinner = (Spinner) findViewById(R.id.ccp);
        //ccp = (CountryCodePicker) getView().findViewById(R.id.ccp);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        //countryCodeSpinner.setOnClickListener((View.OnClickListener) this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new CallieSessionManager(getApplicationContext());

        // SQLite database handler
        db = new SqliteHelper(getApplicationContext());
/*
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Pverify.this,
                    Callie.class);
            startActivity(intent);
            finish();
        }
*/
        countryCode = ccp.getSelectedCountryCodeWithPlus();

        // PregisterRequest Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String mobile = inputMobileEditText.getText().toString().trim();

                if (!mobile.isEmpty()) {
                    registerUser(mobile);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter mobile number!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
/*
        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
*/
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private void registerUser(String mobile) {

        if (register.Pregister(AccountConstants.aquiredAccessToken, countryCode, mobile) != null) {
            Log.d(TAG, "registerUser: I am set");
        }

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
