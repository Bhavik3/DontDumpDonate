package com.example.bhavik.dontdumpdonate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bhavik on 02-04-2015.
 */
public class viewNGOProfile extends Fragment{

    private ListView list;

    JSONObject jobj = null;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        viewNGOProfile f = new viewNGOProfile();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewprofile, container, false);
        list = (ListView)v.findViewById(R.id.listView);
        String[] data = {"Name: Bhavik","Email: bhavikpatel.aha@gmail.com"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,data);
        list.setAdapter(dataAdapter);
        return v;
    }

    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("username", userName));

            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/view_ngo_profile.php",params);



            return ab;
        }

        protected void onPostExecute(String ab){
            System.out.println(" yay " + ab);

        }

    }
}
