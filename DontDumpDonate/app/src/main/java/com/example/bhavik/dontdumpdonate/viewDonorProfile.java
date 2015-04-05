package com.example.bhavik.dontdumpdonate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class viewDonorProfile extends Fragment{

    private ListView list;
    private String [] data = new String[6];
    private int isSuccess;

    JSONObject jobj = null;
    clientServerInterface clientServerInterface = new clientServerInterface();
    String ab;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        viewDonorProfile f = new viewDonorProfile();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewprofile, container, false);
        list = (ListView) v.findViewById(R.id.listView);

        try{
            new RetreiveData().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        ArrayAdapter<String> dataAdapter;
        if (isSuccess == 1){
            customListAdapter adapter = new customListAdapter(getActivity().getApplicationContext(),R.layout.custom_list_item);
            for(String x:data){
                adapter.add(x);
            }
//            dataAdapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, data);
//            list.setAdapter(dataAdapter);
            list.setAdapter(adapter);
        }

        return v;
    }

    class RetreiveData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", profile.ID+""));
            jobj = clientServerInterface.makeHttpRequest("http://dontdumpdonate.byethost7.com/view_self_profile.php",params);

            //jobj.getInt("success")
            //success message type username name contact email pincode address
            try {
                isSuccess = jobj.getInt("success");
                if(isSuccess==1){
                    data[0] = "Name: "+jobj.getString("name");
                    data[1] = "User Name: "+jobj.getString("username");
                    data[2] = "Contact No: "+jobj.getString("contact");
                    data[3] = "Email: "+jobj.getString("email");
                    data[4] = "Residential Address: "+jobj.getString("address");
                    data[5] = "Pin Code: "+jobj.getString("pincode");
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
