package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.helpers.StatusLoggerArrayAddapter;
import com.example.mobiledroneapp.models.FlightLogger;
import com.example.mobiledroneapp.models.StatusLogger;

import java.util.ArrayList;
import java.util.List;

public class PopupMenuStatusLogs extends AppCompatActivity {

    DBHelperAdapter dbAdapter;

    private Context context = this;


    private int assignmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_menu_status_logs);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        dbAdapter = new DBHelperAdapter(context);

        if(extras.containsKey("assignmentID")) {
            //Log.e("assignmentID:", i.getStringExtra("assignmentID"));
            assignmentID = Integer.parseInt(i.getStringExtra("assignmentID"));
        }
        List<FlightLogger> flightLoggs = dbAdapter.findFlightLoggerByAssignmentID(assignmentID);
        List<StatusLogger> statusLoggs = dbAdapter.getStatusLogger();
        List<StatusLogger> displayListSL = new ArrayList<>();
        for(FlightLogger fl : flightLoggs) {
            for(StatusLogger sl : statusLoggs) {
                if(sl.getFlightId() == fl.getId()) {
                    displayListSL.add(sl);
                }
            }
        }

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_popup_menu_status_logs, null);
        ListView listView = (ListView) findViewById(R.id.list_view_2);
        StatusLoggerArrayAddapter arrayAdapter = new StatusLoggerArrayAddapter(context, R.layout.status_loggs_list_layout,
                statusLoggs);

        listView.setAdapter(arrayAdapter);
    }
}