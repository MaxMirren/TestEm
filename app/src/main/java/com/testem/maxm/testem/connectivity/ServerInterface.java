package com.testem.maxm.testem.connectivity;

import com.testem.maxm.testem.AuthActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Mr_95 on Jan 17, 2017.
 */

public final class ServerInterface extends AsyncTask<String,String,String> {

    private Connection con;
    private AuthActivity authActivity;

    final String USERNAME = "testemadmin";
    final String PASSWORD = "su";
    final String DATABASE = "testem";
    final String IP = "5.101.194.75";

    public String z = "";
    public String email, password;
    private Boolean isSuccess = false;


    @Override
    protected String doInBackground(String... params)
    {
            try
            {
                con = connectionclass(USERNAME, PASSWORD, DATABASE, IP);        // Connect to database
                if (con == null)
                {
                    z = "Check Your Internet Access!";
                }
                else
                {
                    String query = "select * from accounts where email= '" + email +"' AND password= '" + password + "'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        z = "Login is successful";
                        isSuccess=true;
                        //con.close();
                    }
                    else
                    {
                        z = "Invalid Credentials!";
                        isSuccess = false;
                    }
                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                z = ex.getMessage().toString() + "  ";
                z = ex.toString();
            }
        return z;
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + ";databaseName=" + database + ";user=" + user+ ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            z = se.getMessage().toString();
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            z = e.getMessage().toString();
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            z = e.getMessage().toString();
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        authActivity.makeToast(z);
    }

    public void sendData (AuthActivity activity, String mail, String passwd) {
        authActivity = activity;
        email = mail;
        password = passwd;
    }


}


