package com.example.bhavik.dontdumpdonate;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class profile extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<navDrawerItem> navDrawerItems;
    private navDrawerListAdapter adapter;

    int x ;

    static int ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mTitle = mDrawerTitle = getTitle();

        x = getIntent().getIntExtra("USER_ID",-1);
        ID = getIntent().getIntExtra("ID",-1);

        // load slide menu items
        navMenuTitles = InitOption(x);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);


        navDrawerItems = new ArrayList<navDrawerItem>();

        // adding nav drawer items to array
        int temp=0;
        for(String x :navMenuTitles) {
            navDrawerItems.add(new navDrawerItem(navMenuTitles[temp], navMenuIcons.getResourceId(temp, -1)));
            temp++;
        }

        // Recycle the typed array
        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new navDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
//            displayView(0);
        }

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


        if(x==1){
            Fragment fragment = new postDonation();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
            mDrawerList.setItemChecked(1, true);
            mDrawerList.setSelection(1);
            setTitle(navMenuTitles[1]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }else if(x==2){
            Fragment fragment = new postEvent();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
            mDrawerList.setItemChecked(1, true);
            mDrawerList.setSelection(1);
            setTitle(navMenuTitles[1]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }

    }

    public void ChangeFragmentToOwnDonations(){
        Fragment fragment = new viewOwnDonations();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(3, true);
        mDrawerList.setSelection(3);
        setTitle(navMenuTitles[3]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void ChangeFragmentToOwnEvents(){
        Fragment fragment = new viewOwnEvents();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(4, true);
        mDrawerList.setSelection(4);
        setTitle(navMenuTitles[4]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            ChangeFragment(position);
        }
    }
    private void ChangeFragment(int pos){
        Fragment fragment = null;

        if(pos==5){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }else if(pos==0 && x==1) {
            fragment = new viewDonorProfile();
        }else if(pos==0 && x==2){
            fragment = new  viewNGOProfile();
        }else if(pos==1 && x==1){
            fragment = new postDonation();
//            fragment1.setProfile();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.content_frame, fragment1).commit();
//
//            // update selected item and title, then close the drawer
//            mDrawerList.setItemChecked(pos, true);
//            mDrawerList.setSelection(pos);
//            setTitle(navMenuTitles[pos]);
//            mDrawerLayout.closeDrawer(mDrawerList);
        }else if(pos==1 && x==2){
            fragment = new postEvent();
        }else if((pos==2 && x==1) || (pos==3 && x==2)){
            fragment = new searchNGO();
        }else if(pos==2 && x==2){
            fragment = new viewDonation();
        }else if(pos==3 && x==1){
            fragment = new viewOwnDonations();
        }else if(pos==4 && x==1){
            fragment = new viewEvents();
        }else if(pos==4 && x==2){
            fragment = new viewOwnEvents();
        }
        else{
            Toast.makeText(this,"something went wrong..",Toast.LENGTH_LONG);
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(pos, true);
            mDrawerList.setSelection(pos);
            setTitle(navMenuTitles[pos]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }

    private String [] InitOption(int x){
        if(x==1){           //donors
            String [] options = {"View Profile","Post Donation","View NGOs","My Posts","View Events","Log Out"};         //view events
            return options;
        }else if(x==2){         //NGO
            String [] options = {"View Profile","Post an Event","View Donation","View NGOs","View Own Events","Log Out"};    //view own events
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
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
