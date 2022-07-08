package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.helpers.DroneRequest;
import com.example.mobiledroneapp.helpers.TOPICS;
import com.example.mobiledroneapp.models.Assignments;
import com.example.mobiledroneapp.models.FlightLogger;
import com.example.mobiledroneapp.models.StatusLogger;
import com.example.mqtt.MQTTService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Control extends AppCompatActivity {

    Button btnStartWork;
    Button btnAssignTask;

    Spinner spinner;

    EditText editTextControl;

    DroneRequest droneRequest = DroneRequest.getInstance();

    MQTTService mqttService = MQTTService.getInstance();

    DBHelperAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        btnStartWork = (Button) findViewById(R.id.btnStartWork);
        btnAssignTask = (Button) findViewById(R.id.btnAssignTask);
        editTextControl = (EditText) findViewById(R.id.editTextControl);
        Context context = this;
        dbAdapter = new DBHelperAdapter(context);
        // Patrol / Survey / Protect
        String[] arraySpinner = new String[] {
                "Patrol", "Survey", "Protect"
        };

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if(droneRequest.getDroneTaskRequest().getLng() != 0
                && droneRequest.getDroneTaskRequest().getLat() != 0
                && droneRequest.getDroneTaskRequest().getAltitude() != 0) {
            editTextControl.append("You have selected: \nlatitude: "
                    + droneRequest.getDroneTaskRequest().getLat()
                    + "\n"
                    + "longitude: " + droneRequest.getDroneTaskRequest().getLng()
                    + "\n"
                    + "Optimal Altitude: " +  droneRequest.getDroneTaskRequest().getAltitude()
                    + "\n");

        }

        btnAssignTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = spinner.getSelectedItem().toString();
                char mode = '0';
                if(selection.equals("Patrol")) {
                    mode = 'p';
                    editTextControl.append("PATROL" + "\n");
                }
                if(selection.equals("Survey")) {
                    mode = 's';
                    editTextControl.append("SURVEY" + "\n");
                }
                if(selection.equals("Protect")) {
                    mode = 'r';
                    editTextControl.append("PROTECT" + "\n");
                }
                droneRequest.getDroneTaskRequest().setCmd((byte)5);
                droneRequest.getDroneTaskRequest().setCmd((byte)2);
                droneRequest.getDroneTaskRequest().setMode(mode + "");
            }
        });

        btnStartWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if lat, lng and altitude are set.

                if(droneRequest.getDroneTaskRequest().getLng() == 0
                        && droneRequest.getDroneTaskRequest().getLat() == 0
                        && droneRequest.getDroneTaskRequest().getAltitude() == 0) {
                    Toast.makeText(context, "You need to set the location values!", Toast.LENGTH_SHORT).show();
                } else {
                    mqttService.publish(TOPICS.TOPIC_5, "nothing");

                    Assignments assignment = new Assignments();
                    assignment.setMode(droneRequest.getDroneTaskRequest().getMode());
                    String timeFormat = "dd-MM-yyyy";
                    SimpleDateFormat dbFormat = new SimpleDateFormat(timeFormat);
                    String _creationDate = dbFormat.format(Calendar.getInstance().getTime());
                    assignment.setStartDate(_creationDate);
                    assignment.setFlightDuration("30");
                    Long assignmentID = dbAdapter.insertInAssignments(assignment);
                    FlightLogger fl = new FlightLogger();
                    fl.setAltitude(droneRequest.getDroneTaskRequest().getAltitude());
                    fl.setAssignmentId(Integer.parseInt(assignmentID+""));
                    fl.setLat(droneRequest.getDroneTaskRequest().getLat());
                    fl.setLng(droneRequest.getDroneTaskRequest().getLng());
                    Long flID = dbAdapter.insertInFlightLogger(fl);
                    StatusLogger sl = new StatusLogger();
                    sl.setFlightId(Integer.parseInt(flID+""));
                    sl.setDate(_creationDate);
                    sl.setRisk("NONE");
                    sl.setBattery(100);
                    sl.setStatus("NEW");
                    Long slID = dbAdapter.insertInStatusLogger(sl);
                    finish();
                }
            }
        });
    }
}