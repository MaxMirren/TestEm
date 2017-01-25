/*
 * %W% %E% Max Mirren
 *
 * Copyright (c) 2017 MaxM, Free License
 *
 * This application is a part of TestEm package and provides testing features for students
 */

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

/**
 * @version 0.9 25.01.2017
 * @author Max Mirren
 * The AuthActivity class provides interaction with auth_activity.xml and ServerInterface class
 */
public class AuthActivity extends AppCompatActivity {

    private static final String EMAIL = "EMAIL";                            //code for email field data in instance state
    private static final String PASSWORD = "PASSWORD";                      //code for password field data in instance state
    private static final String SIGN_IN_ENABLED = "SIGN_IN_ENABLED";        //code for enable state of sign in button in instance state

    private static EditText emailEditText;                                  //relative of email field
    private static EditText passwordEditText;                               //relative of password field
    private static Button signIn;                                           //relative of sign in button

    private static String emailTyped = "";                                  //email field data for instance state
    private static String passwordTyped = "";                               //password field data for instance state
    private static Boolean signInEnabled = false;                           //sign in button data for instance state

    private GoogleApiClient client;                                         //represents google api interaction

    /**
     * Connects the auth_activity.xml to current class and sets uo the most important methods
     * @param savedInstanceState represents saved state of layout components
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        connectVariablesToViews();
        listenToFields();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * This was auto-generated to implement the App Indexing API.
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

    /**
     * Sets up when application has started
     */
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    /**
     * Sets up when application has stopped
     */
    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * Connects on-form objects to in-class variables
     */
    private void connectVariablesToViews() {
        emailEditText = (EditText) findViewById(R.id.emailField);
        passwordEditText = (EditText) findViewById(R.id.passwordField);
        signIn = (Button) findViewById(R.id.sign_in);
    }

    /**
     * Saves changeable data when state is changed
     * @param outState represents current state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EMAIL, emailTyped);
        outState.putString(PASSWORD, passwordTyped);
        outState.putBoolean(SIGN_IN_ENABLED, signInEnabled);
    }

    /**
     * Restores changeable data when state is changed
     * @param savedInstanceState represents daved state
     */
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
     * Listens to email and password fields to save data and change the state of sign in button
     */
    private void listenToFields() {
        emailEditText.addTextChangedListener(new TextWatcher() {

            /**
             * This is auto-generated method that does something before listened fields' text changed
             * @param s
             * @param start
             * @param count
             * @param after
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            /**
             * This is auto-generated method that does something when listened fields' text changed
             * @param s
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailTyped = emailEditText.getText().toString();
                if ((emailTyped.isEmpty()) || (passwordTyped.isEmpty())) {
                    signInEnabled = false;
                    signIn.setEnabled(signInEnabled);
                } else {
                    signInEnabled = true;
                    signIn.setEnabled(signInEnabled);
                }
            }

            /**
             * This is auto-generated method that does something after listened fields' text changed
             * @param s
             */
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
                if ((emailTyped.isEmpty()) || (passwordTyped.isEmpty())) {
                    signInEnabled = false;
                    signIn.setEnabled(signInEnabled);
                } else {
                    signInEnabled = true;
                    signIn.setEnabled(signInEnabled);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Sets up method when a view was clicked
     * @param view represents element that was clicked
     */
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

    /**
     * Simplifies the notification system by doing the same with simple interface
     * @param string incoming data user notified of
     */
    public void makeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    /**
     * Gets device unique number
     * @return obtained device number
     */
    public String getDeviceId() {
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String number = tm.getDeviceId();
        return number;
    }
}

