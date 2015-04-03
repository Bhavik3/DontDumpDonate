package com.example.bhavik.dontdumpdonate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class signUp extends ActionBarActivity {

    private Button ngo;
    private Button general_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ngo = (Button)findViewById(R.id.NGO);
        general_user = (Button)findViewById(R.id.generalUser);

        ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NGO();
            }
        });

        general_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalUser();
            }
        });
    }

    private void NGO(){
        Intent i = new Intent(this,ngoSignUp.class);
        startActivity(i);
    }

    private void generalUser(){

        Intent i = new Intent(this,generalUserSignUp.class);
        startActivity(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
