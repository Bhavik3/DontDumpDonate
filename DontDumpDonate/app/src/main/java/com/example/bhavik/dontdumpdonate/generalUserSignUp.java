package com.example.bhavik.dontdumpdonate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
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
}
