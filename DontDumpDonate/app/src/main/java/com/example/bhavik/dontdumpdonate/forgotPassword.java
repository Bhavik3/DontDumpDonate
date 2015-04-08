package com.example.bhavik.dontdumpdonate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class forgotPassword extends ActionBarActivity {

    private EditText RecoveryEmailAddress;
    private Button RecoverPassword;
    Spinner category;
    private String type;

    JSONObject jobj = null;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;
    private int isAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        RecoverPassword = (Button)findViewById(R.id.RecoverMyPassword);
        RecoveryEmailAddress = (EditText)findViewById(R.id.RecoveryEmailAddress);

        category = (Spinner)findViewById(R.id.category);
        category.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        RecoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecoverPassword();
            }
        });

    }


    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            String temp = parent.getItemAtPosition(pos).toString();
            if(temp.equals("General User"))
                type = "general";
            else if(temp.equals("Caterer"))
                type = "caterer";
            else if(temp.equals("NGO"))
                type = "ngo";
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    private void RecoverPassword(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        try {
            new RetreiveData().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Toast t= Toast.makeText(this,"Password has been sent to your registered email Address..",Toast.LENGTH_LONG);
        t.show();
        finish();
    }


    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if(type.equals("ngo"))
                params.add(new BasicNameValuePair("ngo", RecoveryEmailAddress.getText().toString()));
            else
                params.add(new BasicNameValuePair("donor",RecoveryEmailAddress.getText().toString()));
                jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/sendmail.php",params);
//            try {
//                isAccess = jobj.getInt("success");
//                type = jobj.getString("type");
//                System.out.println(isAccess);
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            return ab;
        }

        protected void onPostExecute(String ab){
            System.out.println(" yay " + ab);
//            if(isAccess==0)
//                Toast.makeText(forgotPassword.this,"something went wrong, please try again..",Toast.LENGTH_LONG).show();
//            else {
//                Intent i = new Intent(forgotPassword.this, MainActivity.class);
//                startActivity(i);
//                finish();
//                Toast.makeText(forgotPassword.this,"Email has been sent to your registered email address.",Toast.LENGTH_LONG).show();
//            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forgot_password, menu);
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
}
