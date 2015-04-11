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
public class viewEvents extends Fragment{

    Button prev;
    Button next;

    JSONObject jobj = null;
    String ab;

    int EventIndex =0;
    private String [] data = new String[6];
    private int isSuccess;
    JSONArray eventsJson;
    ListView events;
    clientServerInterface clientServerInterface = new clientServerInterface();

    public static android.support.v4.app.Fragment newInstance(Context context) {
        viewEvents f = new viewEvents();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_events, container, false);

        prev = (Button)v.findViewById(R.id.prev);
        next = (Button)v.findViewById(R.id.next);

        events = (ListView)v.findViewById(R.id.listEvents);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EventIndex==0)
                    Toast.makeText(getActivity(), "No more previous donation.", Toast.LENGTH_LONG).show();
                else {
                    EventIndex--;
                    displayEvents(EventIndex);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EventIndex==eventsJson.length()-1)
                    Toast.makeText(getActivity(),"No more next donation.",Toast.LENGTH_LONG).show();
                else {
                    EventIndex++;
                    displayEvents(EventIndex);
                }
            }
        });

        ViewEvents();

        return v;
    }


    public void displayEvents(int index){
        try {
            data[3] = "Time: "+eventsJson.getJSONObject(index).getString("time");
            data[4] = "Date: "+eventsJson.getJSONObject(index).getString("date");
            data[0] = "Details: "+eventsJson.getJSONObject(index).getString("details");
            data[1] = "Venue: "+eventsJson.getJSONObject(index).getString("venue");       //venue
            data[2] = "NGO name: "+eventsJson.getJSONObject(index).getString("ngoname");       //NGO name
            data[2] = "Contact: "+eventsJson.getJSONObject(index).getString("contact");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isSuccess == 1){
            customListAdapter adapter = new customListAdapter(getActivity().getApplicationContext(),R.layout.custom_list_item);
            for(String x:data){
                adapter.add(x);
            }
            events.setAdapter(adapter);
        }
    }


    private void ViewEvents() {
        try{
            new RetreiveData().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(isSuccess==1) {
            try {data[3] = "Time: "+eventsJson.getJSONObject(0).getString("time");
                data[4] = "Date: "+eventsJson.getJSONObject(0).getString("date");
                data[0] = "Details: "+eventsJson.getJSONObject(0).getString("details");
                data[1] = "Venue: "+eventsJson.getJSONObject(0).getString("venue");       //venue
                data[2] = "NGO name: "+eventsJson.getJSONObject(0).getString("ngoname");       //NGO name
                data[5] = "Contact: "+eventsJson.getJSONObject(0).getString("contact");
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
            events.setAdapter(adapter);
        }

    }

    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", profile.ID+""));
            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/display_events.php",params);

            try {
                isSuccess = jobj.getInt("success");
                if(isSuccess==1){
                    eventsJson =  jobj.getJSONArray("events");
                    System.out.println(eventsJson.toString());
                }else{
                    eventsJson = new JSONArray();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                Toast.makeText(getActivity().getApplicationContext(), jobj.getString("message"), Toast.LENGTH_LONG).show();
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
