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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class generalUserSignUp extends ActionBarActivity {

    Button submit;
    EditText PersonName;
    EditText UserName;
    EditText Password;
    EditText ContactNo;
    EditText EmailAddress;
    EditText ResAddress;
    EditText Age;
    EditText PinCode;
    Spinner category;


    private String name;
    private String username;
    private String password;
    private String contact;
    private String email;
    private String address;
    private String age;
    private String pincode;
    private String type;

    JSONObject jobj = null;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;
    private int isAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_user_sign_up);

        submit = (Button)findViewById(R.id.Submit);

        PersonName = (EditText)findViewById(R.id.PersonName);
        UserName = (EditText)findViewById(R.id.UserName);
        Password = (EditText)findViewById(R.id.Password);
        ContactNo = (EditText)findViewById(R.id.ContactNo);
        ResAddress = (EditText)findViewById(R.id.ResAddress);
        EmailAddress = (EditText)findViewById(R.id.EmailAddress);
        Age = (EditText)findViewById(R.id.Age);
        PinCode = (EditText)findViewById(R.id.PinCode);
        category = (Spinner)findViewById(R.id.category);

        category.setOnItemSelectedListener(new CustomOnItemSelectedListener());


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
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
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    private void submit(){
        if(PersonName.equals("") || UserName.equals("") || Password.equals("")
                || ContactNo.equals("") || ResAddress.equals("") || EmailAddress.equals("") ||
                Age.equals("") || PinCode.equals("")){
            Toast.makeText(this,"please fill all the details..",Toast.LENGTH_LONG).show();
            return;
        }

        name = PersonName.getText().toString();
        username = UserName.getText().toString();
        password = Password.getText().toString();
        contact = ContactNo.getText().toString();
        address = ResAddress.getText().toString();
        email = EmailAddress.getText().toString();
        age = Age.getText().toString();
        pincode = PinCode.getText().toString();

        if(name.split(" ")[0].equals("") || username.split(" ")[0].equals("") || password.split(" ")[0].equals("")
                || contact.split(" ")[0].equals("") || address.split(" ")[0].equals("") || email.split(" ")[0].equals("") ||
                age.split(" ")[0].equals("") || pincode.split(" ")[0].equals("")){
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

        if(age.length()>=3){
            Toast.makeText(this,"Please enter valid Age!",Toast.LENGTH_LONG).show();
            return;
        }

        if(!email.contains("@")){
            Toast.makeText(this,"Please enter valid Email Address.",Toast.LENGTH_LONG).show();
            return;
        }

        new RetreiveData().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_general_user_sign_up, menu);
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


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", "\""+username+"\""));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("name", "\""+name+"\""));
            params.add(new BasicNameValuePair("age", age));
            params.add(new BasicNameValuePair("contact", contact));
            params.add(new BasicNameValuePair("email", "\""+email+"\""));
            params.add(new BasicNameValuePair("address", "\""+address+"\""));
            params.add(new BasicNameValuePair("pincode", pincode));
            params.add(new BasicNameValuePair("type", "\""+type+"\""));
            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/insert_donor.php",params);
            try {
                isAccess = jobj.getInt("success");
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
            if(isAccess==0)
                Toast.makeText(generalUserSignUp.this,"something went wrong, please try again..",Toast.LENGTH_LONG).show();
            else {
                Intent i = new Intent(generalUserSignUp.this, MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(generalUserSignUp.this,"you are registered.",Toast.LENGTH_LONG).show();
            }
        }

    }
}
