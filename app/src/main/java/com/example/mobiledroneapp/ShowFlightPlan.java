package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.models.FlightLogger;

import java.util.ArrayList;
import java.util.List;

public class ShowFlightPlan extends AppCompatActivity {

    private int assignmentID;

    DBHelperAdapter dbAdapter;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_flight_plan);

        dbAdapter = new DBHelperAdapter(context);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if(extras.containsKey("assignmentID")) {
            Log.e("assignmentID:", i.getStringExtra("assignmentID"));
            assignmentID = Integer.parseInt(i.getStringExtra("assignmentID"));
        }

        List<FlightLogger> flightLoggers = dbAdapter.findFlightLoggerByAssignmentID(assignmentID);

        Fragment fragment = new MapFragmentPath();
        Bundle args = new Bundle();
        ArrayList<String> argsList = new ArrayList<>();

        for (FlightLogger fl : flightLoggers) {
            argsList.add(fl.getLat() + "#" + fl.getLng());
        }

        args.putStringArrayList("flightPoints", argsList);
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout2, fragment)
                .commit();

    }
}