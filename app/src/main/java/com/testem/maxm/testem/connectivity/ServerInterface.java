package com.testem.maxm.testem.connectivity;

import com.testem.maxm.testem.AuthActivity;

import android.annotation.SuppressLint;
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
/**
 * Created by Mr_95 on Jan 17, 2017.
 */

public final class ServerInterface extends AsyncTask<String,String,String> {

    private Connection con;
    private AuthActivity authActivity;
    final String USERNAME = "sa";
    final String PASSWORD = "sa";
    final String DATABASE = "testem";
    final String IP = "5.101.194.75";

    public String z = "ZZZ";
    Boolean isSuccess = false;


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
                    String query = "select * from login where user_name= '" + USERNAME + "' and pass_word = '"+ PASSWORD +"'  ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        z = "Login successful";
                        isSuccess=true;
                        con.close();
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
                z = ex.getMessage().toString();
            }
        //Toast.makeText(authActivity, z , Toast.LENGTH_LONG).show();
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
            Toast.makeText(authActivity, se.getMessage() , Toast.LENGTH_LONG).show();
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Toast.makeText(authActivity, e.getMessage() , Toast.LENGTH_LONG).show();
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Toast.makeText(authActivity, e.getMessage() , Toast.LENGTH_LONG).show();
            Log.e("error here 3 : ", e.getMessage());
        }
        Toast.makeText(authActivity, z , Toast.LENGTH_LONG).show();
        return connection;
    }

    public void sendActivity (AuthActivity activity) {
        authActivity = activity;
    }
}


