package com.example.mobiledroneapp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mobiledroneapp.R;
import com.example.mobiledroneapp.models.FlightLogger;

import java.util.List;

public class FlightLoggersArrayAdapter extends ArrayAdapter<FlightLogger> {

    private static final String TAG = "FlightLoggersArrayAdapter";

    private Context mContext;

    private int mResources;

    public FlightLoggersArrayAdapter(Context applicationContext,
                                     int simple_list_item_1,
                                     List<FlightLogger> flightLoggers) {
        super(applicationContext, simple_list_item_1, flightLoggers);
        this.mContext = applicationContext;
        this.mResources = simple_list_item_1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int flightLogID = getItem(position).getId();
        int flightLogAssID = getItem(position).getAssignmentId();
        double flightLogLat = getItem(position).getLat();
        double flightLogLng = getItem(position).getLng();
        long flightLogAltitude = getItem(position).getAltitude();

        FlightLogger fl = new FlightLogger();
        fl.setId(flightLogID);
        fl.setAssignmentId(flightLogAssID);
        fl.setLat(flightLogLat);
        fl.setLng(flightLogLng);
        fl.setAltitude(flightLogAltitude);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResources, parent, false);

        TextView tv1 = (TextView) convertView.findViewById(R.id.avl_txv1);
        TextView tv2 = (TextView) convertView.findViewById(R.id.avl_txv2);
        TextView tv3 = (TextView) convertView.findViewById(R.id.avl_txv3);

        tv1.setText(fl.getId() + "");
        tv2.setText(fl.getAssignmentId() + "");
        tv3.setText("Status Log");

        return convertView;


    }

}
