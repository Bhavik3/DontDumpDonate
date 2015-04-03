package com.example.bhavik.dontdumpdonate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Bhavik on 02-04-2015.
 */
public class postDonation extends Fragment{

    Button post;
    EditText details;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        postDonation f = new postDonation();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.post_donation, container, false);
        post = (Button)v.findViewById(R.id.postDonation);
        details = (EditText)v.findViewById(R.id.DonationDetail);

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
        Toast.makeText(getActivity().getApplicationContext(),"posted...",Toast.LENGTH_LONG).show();

    }
}
