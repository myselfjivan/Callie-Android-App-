package com.status.callie.UI;

/**
 * Created by jivan.ghadage on 8/5/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.*;
import com.status.callie.Configuration.Configuration;

public class Pregister extends Activity {
    String TAG = "pregistration Activity";
    Button btn;
    TextView mobile_no;
    EditText link;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            return;
        }
        btn.setEnabled(false);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean validate() {
        boolean valid = true;

        String passwordString = mobile_no.getText().toString();


        if (passwordString.isEmpty() || mobile_no.length() < 4 || mobile_no.length() > 13) {
            mobile_no.setError("between 4 and 13 numeric characters");
            valid = false;
        } else {
            mobile_no.setError(null);
        }

        return valid;
    }


    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Configuration.URL + relativeUrl;
    }
}

