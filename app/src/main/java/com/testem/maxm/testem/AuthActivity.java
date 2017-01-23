package com.testem.maxm.testem;

import com.testem.maxm.testem.connectivity.ServerInterface;
import com.testem.maxm.testem.connectivity.Functions;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class AuthActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String SIGN_IN_ENABLED = "SIGN_IN_ENABLED";

    EditText emailEditText, passwordEditText;
    Button signIn;

    String emailTyped = "";
    String passwordTyped = "";
    Boolean signInEnabled = false;

    private ArrayList accountsList;

    private GoogleApiClient client;
    public AuthActivity authActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        connectVariablesToViews();
        listenToFields();
        authActivity = this;
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EMAIL, emailTyped);
        outState.putString(PASSWORD, passwordTyped);
        outState.putBoolean(SIGN_IN_ENABLED, signInEnabled);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        emailTyped = savedInstanceState.getString(EMAIL);
        passwordTyped = savedInstanceState.getString(PASSWORD);
        signInEnabled = savedInstanceState.getBoolean(SIGN_IN_ENABLED);
        emailEditText.setText(emailTyped);
        passwordEditText.setText(passwordTyped);
        signIn.setEnabled(signInEnabled);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Auth Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private void connectVariablesToViews() {
        emailEditText = (EditText) findViewById(R.id.emailField);
        passwordEditText = (EditText) findViewById(R.id.passwordField);
        signIn = (Button) findViewById(R.id.sign_in);
    }

    private void listenToFields() {
        emailEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailTyped = emailEditText.getText().toString();
                if (emailTyped.isEmpty() || passwordTyped.isEmpty()) {
                    signInEnabled = false;
                    signIn.setEnabled(signInEnabled);
                    //authActivity.makeToast(emailTyped + "_" + signInEnabled.toString());
                } else {
                    signInEnabled = true;
                    signIn.setEnabled(signInEnabled);
                    //authActivity.makeToast(emailTyped + "_" + signInEnabled.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordTyped = passwordEditText.getText().toString();
                if (emailTyped.isEmpty() || passwordTyped.isEmpty()) {
                    signInEnabled = false;
                    signIn.setEnabled(signInEnabled);
                } else {
                    signInEnabled = true;
                    signIn.setEnabled(signInEnabled);
                    //authActivity.makeToast(signInEnabled.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                ServerInterface serverInterface = new ServerInterface();
                serverInterface.sendData(this, emailTyped, passwordTyped);
                serverInterface.followingFunction = Functions.AUTHENTIFIER;
                serverInterface.execute("");
                break;
            default:
                break;
        }
    }

    public void makeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    public String getDeviceId() {
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String number = tm.getDeviceId();
        return number;
       // makeToast(number);
    }
}

