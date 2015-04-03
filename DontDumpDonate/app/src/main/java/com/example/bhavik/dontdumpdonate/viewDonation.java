package com.example.bhavik.dontdumpdonate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Bhavik on 03-04-2015.
 */
public class viewDonation extends Fragment{

    ListView donations;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        viewDonation f = new viewDonation();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_donation, container, false);
        donations = (ListView)v.findViewById(R.id.Donations);
        ViewDonations();
        return v;
    }

    private void ViewDonations() {

        //do query set adapter...:D
    }
}
