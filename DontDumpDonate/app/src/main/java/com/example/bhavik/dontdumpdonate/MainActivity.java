package com.example.bhavik.dontdumpdonate;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    RelativeLayout background;
    Button signIn;
    Button ForgetPassword;
    Button signUp;
    EditText username;
    EditText password;


    JSONObject jobj = null;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;

    private int isAccess=-1;
    private String type;
    private int ID;             //if of user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = (RelativeLayout) findViewById(R.id.SignIn);
        background.setBackgroundColor(Color.parseColor("#bcc6cc"));

        signIn = (Button)findViewById(R.id.button);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        ForgetPassword = (Button)findViewById(R.id.button2);
        signUp = (Button)findViewById(R.id.singUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SignIn(username.getText().toString(),password.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ForgetPassword(username.getText().toString());
            }
        });
    }

    private void SignIn(String username, String password){
        if(username.equals("") || password.equals(""))
            new AlertDialog.Builder(this).setMessage("Username or password is missing.").show();
        else{
            int userId = 2;                                     //donors
            new RetreiveData().execute();
        }

    }

    private void signUp(){
        Intent i = new Intent(this,signUp.class);

        startActivity(i);
    }

    private void ForgetPassword(String username){
        if(username.equals(""))
            new AlertDialog.Builder(this).setMessage("Username is missing.").show();
        else{
            Intent i = new Intent(this,forgotPassword.class);
            startActivity(i);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String userName = "\""+username.getText().toString()+"\"";
            String passWord = "\""+password.getText().toString()+"\"";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", userName));
            params.add(new BasicNameValuePair("password", passWord));
            jobj = clientServerInterface.makeHttpRequest("http://192.168.177.1/myfiles/get_donor_details.php",params);

            try {
                isAccess = jobj.getInt("success");
                if(isAccess==0){
                    jobj = clientServerInterface.makeHttpRequest("http://192.168.177.1/myfiles/get_ngo_details.php",params);
                }
                isAccess = jobj.getInt("success");
                ID = jobj.getInt("id");
                type = jobj.getString("type");
                System.out.println(isAccess);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return ab;
        }

        protected void onPostExecute(String ab){
            System.out.println(" yay " + ab);
            if(isAccess==1){
                Intent i = new Intent(MainActivity.this,profile.class);
                int userId = -1;
                if(type.equals("caterer") || type.equals("general"))
                    userId = 1;
                else if(type.equals("ngo"))
                    userId = 2;

                i.putExtra("USER_ID",userId);
                i.putExtra("ID",ID);
                startActivity(i);
                finish();
            }else{

                new AlertDialog.Builder(MainActivity.this).setMessage("Username or password is wrong!!").show();
            }
        }

    }
}
