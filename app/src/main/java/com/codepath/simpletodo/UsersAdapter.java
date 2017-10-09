package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UsersAdapter extends ArrayAdapter<Products> {
    public UsersAdapter(MainActivity context, ArrayList<Products> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Products user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        // Lookup view for data population
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDue = (TextView) convertView.findViewById(R.id.tvDue);
        // Populate the data into the template view using the data object
        tvBody.setText(user.get_productname());
        tvPriority.setText(user.get_priority());
        tvDue.setText(user.get_timeString());
        // Return the completed view to render on screen
        return convertView;
    }
}