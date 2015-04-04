package com.example.bhavik.dontdumpdonate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Bhavik on 02-04-2015.
 */
public class postDonation extends Fragment{

    Button post;
    EditText details;
    EditText Category;
    EditText Quantity;

    JSONObject jobj = null;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;

    String date;
    String time;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        postDonation f = new postDonation();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.post_donation, container, false);
        post = (Button)v.findViewById(R.id.postDonation);
        details = (EditText)v.findViewById(R.id.DonationDetail);
        Category = (EditText)v.findViewById(R.id.category);
        Quantity = (EditText)v.findViewById(R.id.quantity);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDonation();
            }
        });
        return v;
    }

    private void postDonation(){
        //need to update server data base here....
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        if(details.equals("") || Category.equals("") || Quantity.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "please fill all the details..", Toast.LENGTH_LONG).show();
            return;
        }
        if(details.getText().toString().equals("") || Category.getText().toString().equals("") || Quantity.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "please fill all the details..", Toast.LENGTH_LONG).show();
            return;
        }

        new RetreiveData().execute();
    }

    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("donor_id", profile.ID+""));
            params.add(new BasicNameValuePair("time", "\""+time+"\""));
            params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("category", "\""+Category.getText().toString()+"\""));
            params.add(new BasicNameValuePair("details", "\""+details.getText().toString()+"\""));
            params.add(new BasicNameValuePair("quantity", Quantity.getText().toString()));
            jobj = clientServerInterface.makeHttpRequest("http://192.168.177.1/myfiles/post_donation.php",params);

            return ab;
        }

        protected void onPostExecute(String ab){
            Toast.makeText(getActivity().getApplicationContext(), "Donation posted ...", Toast.LENGTH_LONG).show();
        }

    }
}
