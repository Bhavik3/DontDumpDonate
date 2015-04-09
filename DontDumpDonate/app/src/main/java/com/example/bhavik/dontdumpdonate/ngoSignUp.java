package com.example.bhavik.dontdumpdonate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ngoSignUp extends ActionBarActivity {

    Button submit;
    EditText headPersonName;
    EditText UserName;
    EditText Password;
    EditText ContactNo;
    EditText EmailAddress;
    EditText ResAddress;
    EditText City;
    EditText PinCode;
    EditText State;
    EditText ngoName;


    private String username;
    private String password;
    private String name;        //ngo name
    private String headperson;
    private String contact;
    private String email;
    private String address;
    private String pincode;
    private String city;
    private String state;

    int isAccess;

    JSONObject jobj = null;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo__sign_up);

        submit = (Button)findViewById(R.id.submit);

        headPersonName = (EditText)findViewById(R.id.HeadPerson);
        UserName = (EditText)findViewById(R.id.Username);
        Password = (EditText)findViewById(R.id.password);
        ContactNo = (EditText)findViewById(R.id.ContactNo);
        ResAddress = (EditText)findViewById(R.id.ResidencialAddress);
        EmailAddress = (EditText)findViewById(R.id.email);
        City = (EditText)findViewById(R.id.City);
        PinCode = (EditText)findViewById(R.id.PinCode);
        State = (EditText)findViewById(R.id.State);
        ngoName = (EditText)findViewById(R.id.ngoname);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit(){

        if(headPersonName.equals("") || UserName.equals("") || Password.equals("")
                || ContactNo.equals("") || ResAddress.equals("") || EmailAddress.equals("") ||
                City.equals("") || PinCode.equals("") || State.equals("") || ngoName.equals("")){
            Toast.makeText(this, "please fill all the details..", Toast.LENGTH_LONG).show();
            return;
        }

        name = ngoName.getText().toString();
        username = UserName.getText().toString();
        password = Password.getText().toString();
        contact = ContactNo.getText().toString();
        address = ResAddress.getText().toString();
        email = EmailAddress.getText().toString();
        pincode = PinCode.getText().toString();
        headperson = headPersonName.getText().toString();
        city = City.getText().toString();
        state = State.getText().toString();

        if(name.split(" ")[0].equals("") || username.split(" ")[0].equals("") || password.split(" ")[0].equals("")
                || contact.split(" ")[0].equals("") || address.split(" ")[0].equals("") || email.split(" ")[0].equals("") ||
                pincode.split(" ")[0].equals("") || headperson.split(" ")[0].equals("") ||  city.split(" ")[0].equals("") ||
                state.split(" ")[0].equals("")){
            Toast.makeText(this,"please fill all the details...",Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length()<8){
            Toast.makeText(this,"Password should be at least 8 characters long.",Toast.LENGTH_LONG).show();
            return;
        }

        if(pincode.length()!=6){
            Toast.makeText(this,"Pin code should be 8 characters long.",Toast.LENGTH_LONG).show();
            return;
        }

        if(!email.contains("@")){
            Toast.makeText(this,"Please enter valid Email Address.",Toast.LENGTH_LONG).show();
            return;
        }

        new RetreiveData().execute();

    }


    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", "\""+username+"\""));
            params.add(new BasicNameValuePair("password",password));
            params.add(new BasicNameValuePair("name", "\""+name+"\""));
            params.add(new BasicNameValuePair("headperson", "\""+headperson+"\""));
            params.add(new BasicNameValuePair("contact", contact));
            params.add(new BasicNameValuePair("email", "\""+email+"\""));
            params.add(new BasicNameValuePair("address", "\""+address+"\""));
            params.add(new BasicNameValuePair("pincode", pincode));
            params.add(new BasicNameValuePair("city", "\""+city+"\""));
            params.add(new BasicNameValuePair("state", "\""+state+"\""));

            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/insert_ngo.php",params);

            try {
                isAccess = jobj.getInt("success");
                System.out.println(isAccess);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return ab;
        }

        protected void onPostExecute(String ab){
            System.out.println(" yay " + ab);
            if(isAccess==0)
                Toast.makeText(ngoSignUp.this,"something went wrong, please try again..",Toast.LENGTH_LONG).show();
            else {
                Intent i = new Intent(ngoSignUp.this, MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(ngoSignUp.this,"you are registered.",Toast.LENGTH_LONG).show();
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ngo__sign_up, menu);
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
