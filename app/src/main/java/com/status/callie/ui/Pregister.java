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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.status.callie.CallieController;
import com.status.callie.Model.Register;
import com.status.callie.Model.SqliteHelper;
import com.status.callie.R;
import com.status.callie.accounts.AccountConstants;
import com.status.callie.services.CallieSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jivan.ghadage on 8/11/2016.
 */
public class Pregister extends Activity {
    private static final String TAG = Pregister.class.getSimpleName();
    private Button btnRegister;
    private EditText inputMobile;
    private Spinner country_code;
    private ProgressDialog pDialog;
    private CallieSessionManager session;
    private SqliteHelper db;
    Register register = new Register();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregister);

        btnRegister = (Button) findViewById(R.id.btn_register);
        inputMobile = (EditText) findViewById(R.id.mobile);
        country_code = (Spinner) findViewById(R.id.country_code);

        country_code.setOnClickListener((View.OnClickListener) this);

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
        // PregisterRequest Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String mobile = inputMobile.getText().toString().trim();

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
    private void registerUser(final String mobile) {
        register.Pregister(AccountConstants.aquiredAccessToken, mobile);

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
