package com.example.bhavik.dontdumpdonate;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class searchNGO extends Fragment{

    Button prev;
    Button next;
    ListView Result;

    int DonationIndex =0;
    private String [] data = new String[3];

    private int isSuccess;

    JSONObject jobj = null;
    JSONArray NGO;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        searchNGO f = new searchNGO();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_ngo, container, false);

        Result = (ListView)v.findViewById(R.id.Result);
        prev = (Button)v.findViewById(R.id.prev);
        next = (Button)v.findViewById(R.id.next);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DonationIndex==0)
                    Toast.makeText(getActivity(), "No more previous donation.", Toast.LENGTH_LONG).show();
                else {
                    DonationIndex--;
                    displayNGO(DonationIndex);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DonationIndex==NGO.length()-1)
                    Toast.makeText(getActivity(),"No more next donation.",Toast.LENGTH_LONG).show();
                else {
                    DonationIndex++;
                    displayNGO(DonationIndex);
                }
            }
        });


        SearchNGO();

        return v;
    }

    private void displayNGO(int index){
        try {
            data[0] = "Name: "+NGO.getJSONObject(index).getString("name");
            data[1] = "Contact: "+NGO.getJSONObject(index).getString("contact");
            data[2] = "Address: "+NGO.getJSONObject(index).getString("address");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        customListAdapter adapter = new customListAdapter(getActivity().getApplicationContext(),R.layout.custom_list_item);
        for(String x:data){
            adapter.add(x);
        }

//        ArrayAdapter<String> dataAdapter;
        if (isSuccess == 1){
//            dataAdapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, data);
//            Result.setAdapter(dataAdapter);
            Result.setAdapter(adapter);
        }
    }

    private void SearchNGO(){
        try{
            new RetreiveData().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            data[0] = "Name: "+NGO.getJSONObject(0).getString("name");
            data[1] = "Contact: "+NGO.getJSONObject(0).getString("contact");
            data[2] = "Address: "+NGO.getJSONObject(0).getString("address");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        customListAdapter adapter = new customListAdapter(getActivity().getApplicationContext(),R.layout.custom_list_item);
        for(String x:data){
            adapter.add(x);
        }

//        ArrayAdapter<String> dataAdapter;
        if (isSuccess == 1){
//            dataAdapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, data);
//            Result.setAdapter(dataAdapter);
            Result.setAdapter(adapter);
        }
    }

    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", profile.ID+""));
            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/display_ngos.php",params);

            try {
                isSuccess = jobj.getInt("success");
                if(isSuccess==1){
                    NGO =  jobj.getJSONArray("ngos");
                    System.out.println(NGO.toString());
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "something went wrong, please try again..", Toast.LENGTH_LONG).show();
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
