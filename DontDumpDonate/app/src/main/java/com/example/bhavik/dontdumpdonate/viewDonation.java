package com.example.bhavik.dontdumpdonate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Bhavik on 03-04-2015.
 */
public class viewDonation extends Fragment{

    ListView donations;
    Button prev;
    Button next;
    Button Mark;
    int DonationIndex =0;
    int interestedDonationIndex;
    private String [] data = new String[7];

    private int isSuccess;

    JSONObject jobj = null;
    JSONArray donation ;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        viewDonation f = new viewDonation();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_donation, container, false);
        donations = (ListView)v.findViewById(R.id.Donations);
        prev = (Button)v.findViewById(R.id.previous);
        next = (Button)v.findViewById(R.id.next);
        Mark = (Button)v.findViewById(R.id.mark);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DonationIndex==0)
                    Toast.makeText(getActivity(),"No more previous donation.",Toast.LENGTH_LONG).show();
                else {
                    DonationIndex--;
                    displayDonation(DonationIndex);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DonationIndex==donation.length()-1)
                    Toast.makeText(getActivity(),"No more next donation.",Toast.LENGTH_LONG).show();
                else {
                    DonationIndex++;
                    displayDonation(DonationIndex);
                }
            }
        });

        Mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    interestedDonationIndex = Integer.parseInt(donation.getJSONObject(DonationIndex).getString("donation_id"));
                    new MarkAsInteredsted().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ViewDonations();
        return v;
    }

    public void displayDonation(int index){
        try {
            data[3] = "Time: "+donation.getJSONObject(index).getString("time");
            data[4] = "Date: "+donation.getJSONObject(index).getString("date");
            data[0] = "Details: "+donation.getJSONObject(index).getString("details");
            data[1] = "Category: "+donation.getJSONObject(index).getString("category");
            data[2] = "Quantity: "+donation.getJSONObject(index).getString("quantity");
            data[5] = "Pin code: "+donation.getJSONObject(index).getString("pincode");
            data[6] = "Contact: "+donation.getJSONObject(index).getString("contact");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        ArrayAdapter<String> dataAdapter;
        if (isSuccess == 1){
            customListAdapter adapter = new customListAdapter(getActivity().getApplicationContext(),R.layout.custom_list_item);
            for(String x:data){
                adapter.add(x);
            }
//            dataAdapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, data);
//            donations.setAdapter(dataAdapter);
            donations.setAdapter(adapter);
        }
    }

    private void ViewDonations() {
        try{
            new RetreiveData().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(!(donation.length()==0)) {
            try {
                data[3] = "Time: " + donation.getJSONObject(0).getString("time");
                data[4] = "Date: " + donation.getJSONObject(0).getString("date");
                data[0] = "Details: " + donation.getJSONObject(0).getString("details");
                data[1] = "Category: " + donation.getJSONObject(0).getString("category");
                data[2] = "Quantity: " + donation.getJSONObject(0).getString("quantity");
                data[5] = "Pin code: " + donation.getJSONObject(0).getString("pincode");
                data[6] = "Contact: " + donation.getJSONObject(0).getString("contact");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        ArrayAdapter<String> dataAdapter;
        if (isSuccess == 1){
            customListAdapter adapter = new customListAdapter(getActivity().getApplicationContext(),R.layout.custom_list_item);
            for(String x:data){
                adapter.add(x);
            }
//            dataAdapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, data);
//            donations.setAdapter(dataAdapter);
            donations.setAdapter(adapter);
        }

    }


    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", profile.ID+""));
//            System.out.println("ID==>"+profile.ID);
            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/display_donations.php",params);

            try {
                isSuccess = jobj.getInt("success");
                if(isSuccess==1){
                    donation=new JSONArray();
                    donation =  jobj.getJSONArray("donations");
                    System.out.println(donation.toString());
                }else{
                    donation = new JSONArray();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Toast.makeText(getActivity(), jobj.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
//                    Toast.makeText(getActivity().getApplicationContext(), "something went wrong, please try again..", Toast.LENGTH_LONG).show();
                }
            }catch(JSONException e){
                e.printStackTrace();
            }

            return ab;
        }

        protected void onPostExecute(String ab){
            System.out.println(" yay " + ab);

        }

    }

    class MarkAsInteredsted extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ngo_id", profile.ID+""));
            params.add(new BasicNameValuePair("donation_post_id", interestedDonationIndex+""));
            final JSONObject jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/mark_interested.php",params);

            try {
                isSuccess = jobj.getInt("success");
                System.out.println(isSuccess);
                if(isSuccess==1){
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Marked as interested", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Toast.makeText(getActivity(), jobj.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }catch(JSONException e){
                e.printStackTrace();
            }

            return ab;
        }

        protected void onPostExecute(String ab){
            System.out.println(" yay " + ab);

        }

    }
}
