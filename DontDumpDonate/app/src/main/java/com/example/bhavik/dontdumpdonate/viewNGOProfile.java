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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Bhavik on 02-04-2015.
 */
public class viewNGOProfile extends Fragment{

    private ListView list;
    private String [] data = new String[9];
    private int isSuccess;

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

        try{
            new RetreiveData().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> dataAdapter;
        if (isSuccess == 1){
            dataAdapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, data);
            list.setAdapter(dataAdapter);
        }

        return v;
    }

    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", profile.ID+""));

            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/view_ngo_profile.php",params);

            try {
                isSuccess = jobj.getInt("success");
                if(isSuccess==1){
                    data[0] = "Name: "+jobj.getString("name");
                    data[1] = "User Name: "+jobj.getString("username");
                    data[2] = "Head Person: "+jobj.getString("headperson");
                    data[3] = "Contact No: "+jobj.getString("contact");
                    data[4] = "Email: "+jobj.getString("email");
                    data[5] = "Residential Address: "+jobj.getString("address");
                    data[6] = "State: "+jobj.getString("state");
                    data[7] = "City: "+jobj.getString("city");
                    data[8] = "Pin Code: "+jobj.getString("pincode");
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
