package com.example.mobiledroneapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.helpers.FlightLoggersArrayAdapter;
import com.example.mobiledroneapp.models.FlightLogger;
import com.example.mobiledroneapp.models.StatusLogger;

import java.util.List;

public class PopupMenuActivityView extends AppCompatActivity {

    private Context context = this;

    DBHelperAdapter dbAdapter;

    int assignmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_flights);
        dbAdapter = new DBHelperAdapter(context);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if(extras.containsKey("assignmentID")) {
            Log.e("assignmentID:", i.getStringExtra("assignmentID"));
            assignmentID = Integer.parseInt(i.getStringExtra("assignmentID"));
        }

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_flights, null);
        ListView listView = (ListView) findViewById(R.id.list_view_1);
        List<FlightLogger> flightLoggers = dbAdapter.findFlightLoggerByAssignmentID(assignmentID);
        //ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
        //                                        flightLoggers);
        FlightLoggersArrayAdapter arrayAdapter = new FlightLoggersArrayAdapter(getApplicationContext(), R.layout.adapter_view_layout,
                flightLoggers);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlightLogger fl = flightLoggers.get(position);
                StatusLogger sl = dbAdapter.getStatusLoggerByFlightLog(fl.getId());
                //Toast.makeText(context, sl.toString() ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, ShowFlightPlan.class);
                intent.putExtra("assignmentID", assignmentID + "");
                startActivity(intent);
            }
        });

    }


}
