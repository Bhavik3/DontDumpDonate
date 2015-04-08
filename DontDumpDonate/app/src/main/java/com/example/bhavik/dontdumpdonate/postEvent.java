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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Bhavik on 02-04-2015.
 */
public class postEvent extends Fragment {

    Button post;
    EditText details;
    EditText Venue;
    EditText time;
    EditText date;

    JSONObject jobj = null;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        postEvent f = new postEvent();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.post_event, container, false);

        post = (Button)v.findViewById(R.id.postEvent);
        details = (EditText)v.findViewById(R.id.EventDetail);
        Venue = (EditText)v.findViewById(R.id.venue);
        time = (EditText)v.findViewById(R.id.time);
        date = (EditText)v.findViewById(R.id.date);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postEvent();
            }
        });

        return v;
    }

    private void postEvent(){
        //need to update server data base here....
        if(details.equals("") || Venue.equals("") || time.equals("") || date.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "please fill all the details..", Toast.LENGTH_LONG).show();
            return;
        }
        if(details.getText().toString().equals("") || Venue.getText().toString().equals("") || time.getText().toString().equals("") || date.getText().toString().equals("")) {
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
        viewOwnEvents vDonation = new viewOwnEvents();
        this.getFragmentManager().beginTransaction().replace(R.id.content_frame,vDonation).addToBackStack(null).commit();

    }

    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("details", "\""+details.getText().toString()+"\""));
            params.add(new BasicNameValuePair("ngo_id", profile.ID+""));
            params.add(new BasicNameValuePair("time", "\""+time.getText().toString()+"\""));
            params.add(new BasicNameValuePair("date", date.getText().toString()));
            params.add(new BasicNameValuePair("venue", "\""+Venue.getText().toString()+"\""));
            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/post_event.php",params);
            return ab;
        }

        protected void onPostExecute(String ab){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                        Toast.makeText(getActivity(),"Event posted ...", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



}
