package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobiledroneapp.helpers.TOPICS;
import com.example.mobiledroneapp.mqtt.MQTTBackgroundTask;
import com.example.mqtt.MQTTService;

public class HomeScreen extends AppCompatActivity implements MyUpdateCallback {

    Button btnConnect;
    Button btnDisconnect;
    Button btnControl;
    Button btnAlerts;
    Button btnHistory;
    Button btnTestConnection;
    EditText edTxtStatus;

    private volatile boolean stopConnection = false;

    Thread b_Thread;


    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        MQTTBackgroundTask backgroundTask = new MQTTBackgroundTask();
        b_Thread = new Thread(backgroundTask);

        final Context context = this;
        MQTTService mqttService = MQTTService.getInstance();//new MQTTService(context);
        mqttService.setContext(context);
        mqttService.setMyUpdateCallback(this);
        edTxtStatus = (EditText) findViewById(R.id.edTxtStatus);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);
        btnDisconnect.setClickable(false);
        btnControl = (Button) findViewById(R.id.btnControl);
        btnAlerts = (Button) findViewById(R.id.btnAlerts);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnTestConnection = (Button) findViewById(R.id.btnTestConnection);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!stopConnection) {
                    String statusTxt = mqttService.init();
                    edTxtStatus.setText(statusTxt);
                    stopConnection = true;
                    b_Thread.start();
                    btnConnect.setClickable(false);
                    btnDisconnect.setClickable(true);
                }
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mqttService.publish();
                if(mqttService.disconnect()) {
                    edTxtStatus.setText("Disconnected!");
                    stopConnection = false;
                    backgroundTask.doStop();
                    b_Thread = new Thread(backgroundTask);
                    btnDisconnect.setClickable(false);
                    btnConnect.setClickable(true);
                }
            }
        });

        btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        btnAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlertsScreens.class);
                startActivity(intent);
                //mqttService.publish(TOPICS.TOPIC_2, "2#45");
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, History_2.class);
                startActivity(intent);
            }
        });

        btnTestConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttService.publish(TOPICS.TOPIC_0, "1#45");
            }
        });
    }

    @Override
    public void updateMyTextCallback(String text) {
        edTxtStatus.append("\n" + text);
    }
}