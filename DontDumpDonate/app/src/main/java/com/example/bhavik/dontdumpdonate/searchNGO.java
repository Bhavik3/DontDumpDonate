package com.example.bhavik.dontdumpdonate;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Bhavik on 03-04-2015.
 */
public class searchNGO extends Fragment{

    EditText NGO;
    Button Search;
    ListView Result;

    public static android.support.v4.app.Fragment newInstance(Context context) {
        searchNGO f = new searchNGO();
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_ngo, container, false);

        NGO = (EditText)v.findViewById(R.id.NGO_name);
        Search = (Button)v.findViewById(R.id.Search);
        Result = (ListView)v.findViewById(R.id.Result);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchNGO();
            }
        });
        return v;
    }

    private void SearchNGO(){

    }
}
