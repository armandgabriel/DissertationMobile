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
import com.example.mobiledroneapp.models.StatusLogger;

import java.util.List;

public class StatusLoggerArrayAddapter extends ArrayAdapter<StatusLogger> {

    private static final String TAG = "FlightLoggersArrayAdapter";

    private Context mContext;

    private int mResources;

    public StatusLoggerArrayAddapter(Context applicationContext,
                                     int simple_list_item_1,
                                     List<StatusLogger> statusLoggers) {
        super(applicationContext, simple_list_item_1, statusLoggers);
        this.mContext = applicationContext;
        this.mResources = simple_list_item_1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int statusLogId = getItem(position).getId();
        String statusLogRisk = getItem(position).getRisk();
        String statusLogStatus = getItem(position).getStatus();
        int battery = getItem(position).getBattery();

        StatusLogger sl = new StatusLogger();
        sl.setId(statusLogId);
        sl.setStatus(statusLogStatus);
        sl.setRisk(statusLogRisk);
        sl.setBattery(battery);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResources, parent, false);

        TextView tv1 = (TextView) convertView.findViewById(R.id.avl_txv4);
        TextView tv2 = (TextView) convertView.findViewById(R.id.avl_txv5);
        TextView tv3 = (TextView) convertView.findViewById(R.id.avl_txv6);

        tv1.setText(sl.getId() + "");
        tv2.setText(sl.getStatus());
        tv3.setText(sl.getRisk());

        return convertView;


    }
}
