package com.example.bhavik.dontdumpdonate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Bhavik on 02-04-2015.
 */
public class postDonation extends Fragment{

    Button post;
    EditText Address;
    EditText details;
    Spinner Category;
    EditText Quantity;

    private  profile Profile;
    static DrawerLayout mDrawerLayout;
    static ListView mDrawerList;
    static String[] navMenuTitles;

    JSONObject jobj = null;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;

    String date;
    String time;
    String strCategory;

    public void setProfile(profile p){
        this.Profile = p;
    }

    public static android.support.v4.app.Fragment newInstance(Context context) {
        postDonation f = new postDonation();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.post_donation, container, false);
        post = (Button)v.findViewById(R.id.postDonation);
        details = (EditText)v.findViewById(R.id.DonationDetail);
        Category = (Spinner)v.findViewById(R.id.donation_cat);
        Quantity = (EditText)v.findViewById(R.id.quantity);
        Address = (EditText)v.findViewById(R.id.address);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDonation();
            }
        });

        Category.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        return v;
    }

    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            strCategory = parent.getItemAtPosition(pos).toString();

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    private void postDonation(){
        //need to update server data base here....
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        if(details.equals("") || Category.equals("") || Quantity.equals("") || Address.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "please fill all the details..", Toast.LENGTH_LONG).show();
            return;
        }
        if(details.getText().toString().equals("") || strCategory.equals("") || Quantity.getText().toString().equals("") ||
                Address.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "please fill all the details..", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            new RetreiveData().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        viewOwnDonations vDonation = new viewOwnDonations();
        this.getFragmentManager().beginTransaction().replace(R.id.content_frame,vDonation).addToBackStack(null).commit();

//        Fragment fragment = new postEvent();
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_frame, fragment).commit();
//        mDrawerList.setItemChecked(3, true);
//        mDrawerList.setSelection(3);
////        setTitle(navMenuTitles[1]);
//        mDrawerLayout.closeDrawer(mDrawerList);

    }

    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("donor_id", profile.ID+""));
            params.add(new BasicNameValuePair("time", "\""+time+"\""));
            params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("category", "\""+strCategory+"\""));
            params.add(new BasicNameValuePair("details", "\""+details.getText().toString()+"\""));
            params.add(new BasicNameValuePair("quantity", Quantity.getText().toString()));
            params.add(new BasicNameValuePair("address","\""+Address.getText().toString()+"\""));
            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/post_donation.php",params);

            return ab;
        }

        protected void onPostExecute(String ab){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                        Toast.makeText(getActivity(), "Donation posted ...", Toast.LENGTH_SHORT).show();
                }
            });

//            Toast.makeText(getActivity().getApplicationContext(), "Donation posted ...", Toast.LENGTH_LONG).show();
        }

    }
}
