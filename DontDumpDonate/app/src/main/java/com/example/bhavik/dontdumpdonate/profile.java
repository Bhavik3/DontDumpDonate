package com.example.bhavik.dontdumpdonate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class profile extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        actionBar = getSupportActionBar();
        final int x = getIntent().getIntExtra("USER_ID",-1);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);

        String [] options = InitOption(x);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,options));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChangeFragment(x,position);
            }
        });
    }

    private void ChangeFragment(int x, int pos){
        if(pos==3){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }else if(pos==0){
            android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            viewProfile viewProfile = new viewProfile();
            tx.replace(R.id.content_frame,viewProfile.newInstance(profile.this));
            tx.commit();
        }else if(pos==1 && x==1){
            android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            postDonation postDonation = new postDonation();
            tx.replace(R.id.content_frame,postDonation.newInstance(profile.this));
            tx.commit();
        }else if(pos==1 && x==2){
            android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            postEvent postEvent = new postEvent();
            tx.replace(R.id.content_frame,postEvent.newInstance(profile.this));
            actionBar.setTitle("Post Donation");
            tx.commit();
        }else if(pos==2 && x==1){
            android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            searchNGO searchNGO = new searchNGO();
            tx.replace(R.id.content_frame,searchNGO.newInstance(profile.this));
            tx.commit();
        }else if(pos==2 && x==2){
            android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            viewDonation viewDonation = new viewDonation();
            tx.replace(R.id.content_frame,viewDonation.newInstance(profile.this));
            tx.commit();
        }
        else{
            Toast.makeText(this,"something went wrong..",Toast.LENGTH_LONG);
        }

    }


    private String [] InitOption(int x){
        if(x==1){           //donors
            String [] options = {"View Profile","Post Donation","Search NGO","Log Out"};
            return options;
        }else if(x==2){         //NGO
            String [] options = {"View Profile","Post an Event","View Donation","Log Out"};
            return options;
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
