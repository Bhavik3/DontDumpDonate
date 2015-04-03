package com.example.bhavik.dontdumpdonate;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Bhavik on 02-04-2015.
 */
public class viewProfile extends Fragment{

    private ListView list;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        viewProfile f = new viewProfile();
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
}
