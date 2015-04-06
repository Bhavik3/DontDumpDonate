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
 * Created by Bhavik on 06-04-2015.
 */
public class viewOwnDonations extends Fragment{

    JSONObject jobj = null;
    String ab;

    int DonationIndex =0;
    private String [] data = new String[6];
    private int isSuccess;
    JSONArray donation ;
    clientServerInterface clientServerInterface = new clientServerInterface();

    private int CollectedDonationIndex;

    Button prev;
    Button next;
    Button mark;
    ListView donations;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        viewOwnDonations f = new viewOwnDonations();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_own_donations, container, false);
        prev = (Button)v.findViewById(R.id.prev);
        next = (Button)v.findViewById(R.id.next);
        mark = (Button)v.findViewById(R.id.mark);

        donations = (ListView)v.findViewById(R.id.ListOwnDonations);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DonationIndex==0)
                    Toast.makeText(getActivity(), "No more previous donation.", Toast.LENGTH_LONG).show();
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

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CollectedDonationIndex = Integer.parseInt(donation.getJSONObject(DonationIndex).getString("donation_post_id"));
                    new MarkAsCollected().execute().get();
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
            if(donation.getJSONObject(index).getString("collected").equals("1"))
                data[5] = "Collected: YES";
            else
                data[5] = "Collected: NO";
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isSuccess == 1){
            customListAdapter adapter = new customListAdapter(getActivity().getApplicationContext(),R.layout.custom_list_item);
            for(String x:data){
                adapter.add(x);
            }
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

        if(isSuccess==1) {
            try {
                data[3] = "Time: " + donation.getJSONObject(0).getString("time");
                data[4] = "Date: " + donation.getJSONObject(0).getString("date");
                data[0] = "Details: " + donation.getJSONObject(0).getString("details");
                data[1] = "Category: " + donation.getJSONObject(0).getString("category");
                data[2] = "Quantity: " + donation.getJSONObject(0).getString("quantity");
                if (donation.getJSONObject(0).getString("collected").equals("1"))
                    data[5] = "Collected: YES";
                else
                    data[5] = "Collected: NO";
//            data[6] = "Contact: "+donation.getJSONObject(0).getString("contact");
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

        if (isSuccess == 1){
            customListAdapter adapter = new customListAdapter(getActivity().getApplicationContext(),R.layout.custom_list_item);
            for(String x:data){
                adapter.add(x);
            }
            donations.setAdapter(adapter);
        }

    }


    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", profile.ID+""));
            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/view_own_donations.php",params);

            try {
                isSuccess = jobj.getInt("success");
                if(isSuccess==1){
                    donation =  jobj.getJSONArray("donations");
                    System.out.println(donation.toString());
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), jobj.getString("message"), Toast.LENGTH_LONG).show();
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


    class MarkAsCollected extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ngo_id", profile.ID+""));
            params.add(new BasicNameValuePair("donation_post_id",   CollectedDonationIndex+""));
            final JSONObject jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/mark_as_collected.php",params);

            try {
                isSuccess = jobj.getInt("success");
                System.out.println(isSuccess);
                if(isSuccess==1){
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Marked as Collected", Toast.LENGTH_SHORT).show();
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
