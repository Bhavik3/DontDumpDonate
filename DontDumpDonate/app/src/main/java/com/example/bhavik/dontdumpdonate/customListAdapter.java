package com.example.bhavik.dontdumpdonate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bhavik on 05-04-2015.
 */
public class customListAdapter extends ArrayAdapter<String> {

    private List<String> List = new ArrayList<String>();

    static class ViewHolder {
        TextView data;
    }

    public customListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public void add(String object) {
        List.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.List.size();
    }

    @Override
    public String getItem(int index) {
        return this.List.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.data = (TextView) row.findViewById(R.id.title);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)row.getTag();
        }

        String data = getItem(position);
        viewHolder.data.setText(data);

        return row;
    }

}
