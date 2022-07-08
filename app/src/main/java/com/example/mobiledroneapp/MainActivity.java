package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.helpers.DroneRequest;
import com.example.mobiledroneapp.helpers.TOPICS;
import com.example.mobiledroneapp.models.Assignments;
import com.example.mobiledroneapp.models.FlightLogger;
import com.example.mobiledroneapp.models.StatusLogger;
import com.example.mqtt.MQTTService;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class MainActivity extends AppCompatActivity implements MyUpdateCallback {


    TextView msgId_lbl;
    TextView msgId_txt;

    TextView msgLength_lbl;
    TextView msgLength_txt;

    TextView lat_lbl;
    TextView lat_txt;

    TextView lng_lbl;
    TextView lng_txt;

    TextView speed_lbl;
    TextView speed_txt;

    Button mapBtnId;
    String r;

    Button mapsScreen;

    ImageView drone_icon;

    TextView altitude_txt;

    Button btnLoadData;

    Button btnPickTask;

    Button btnCheckDrone;

    MQTTService mqttService = MQTTService.getInstance();//new MQTTService(context)

    DBHelperAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;
        dbAdapter = new DBHelperAdapter(context);
        mqttService.setContext(context);
        mqttService.setMyUpdateCallback(this);
        //allowAllSSL();
        // Init Components [ assign value to controls in layout ]
        msgId_lbl = findViewById(R.id.msgId_lbl);
        msgId_txt = findViewById(R.id.msgId_txt);
        msgLength_lbl = findViewById(R.id.msgLength_lbl);
        msgLength_txt = findViewById(R.id.msgLength_txt);
        lat_lbl = findViewById(R.id.lat_lbl);
        lat_txt = findViewById(R.id.lat_txt);
        lng_lbl = findViewById(R.id.lng_lbl);
        lng_txt = findViewById(R.id.lng_txt);
        speed_lbl = findViewById(R.id.speed_lbl);
        speed_txt = findViewById(R.id.speed_txt);
        mapBtnId = findViewById(R.id.mapBtnId);
        mapsScreen = findViewById(R.id.mapsScreen);
        altitude_txt = findViewById(R.id.altitude_txt);
        btnLoadData = findViewById(R.id.btnLoadData);
        btnPickTask = (Button) findViewById(R.id.btnPickTask);
        btnCheckDrone = (Button) findViewById(R.id.btnCheckDrone);
        // -------------------------------------------------------
        drone_icon = (ImageView) findViewById(R.id.drone_icon);
        //LinearLayout ll = new LinearLayout(this);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 600.0f,
                0.0f, 0.0f);
        animation.setDuration(5000);  // animation duration
        animation.setRepeatCount(500);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        //animation.setFillAfter(true);

        drone_icon.startAnimation(animation);



        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar startDate = Calendar.getInstance();
                String timeFormat = "dd-MM-yyyy";
                SimpleDateFormat dbFormat = new SimpleDateFormat(timeFormat);
                String _creationDate = dbFormat.format(startDate.getTime());
                long timeMillis = startDate.getTimeInMillis();
                Date endDate = new Date(timeMillis + (45 * 60 * 1000));
                String _endDate = dbFormat.format(endDate);

                Assignments assignment = new Assignments();
                assignment.setFlightDuration("45");
                assignment.setStartDate(_creationDate);
                assignment.setEndDate(_endDate);
                assignment.setMode("P");
                long assignmentID = dbAdapter.insertInAssignments(assignment);


                FlightLogger fl1 = new FlightLogger();
                fl1.setAltitude(0);
                fl1.setLat(24.4214);
                fl1.setLng(25.4122);
                fl1.setAssignmentId((int)assignmentID);
                long flID1 = dbAdapter.insertInFlightLogger(fl1);
                StatusLogger sl1 = new StatusLogger();
                sl1.setRisk("NO RISK");
                sl1.setStatus("GOOD");
                sl1.setDate(_creationDate);
                sl1.setBattery(95);
                sl1.setFlightId((int) flID1);
                long slID1 = dbAdapter.insertInStatusLogger(sl1);

                FlightLogger fl2 = new FlightLogger();
                fl2.setAltitude(10);
                fl2.setLat(27.4214);
                fl2.setLng(28.4122);
                fl2.setAssignmentId((int)assignmentID);
                long flID2 = dbAdapter.insertInFlightLogger(fl2);
                StatusLogger sl2 = new StatusLogger();
                sl2.setRisk("NO RISK");
                sl2.setStatus("GOOD");
                sl2.setDate(_creationDate);
                sl2.setBattery(93);
                sl2.setFlightId((int) flID2);
                long slID2 = dbAdapter.insertInStatusLogger(sl2);

                FlightLogger fl3 = new FlightLogger();
                fl3.setAltitude(24);
                fl3.setLat(27.4224);
                fl3.setLng(29.4132);
                fl3.setAssignmentId((int)assignmentID);
                long flID3 = dbAdapter.insertInFlightLogger(fl3);
                StatusLogger sl3 = new StatusLogger();
                sl3.setRisk("MEDIUM RISK");
                sl3.setStatus("GOOD");
                sl3.setDate(_creationDate);
                sl3.setBattery(85);
                sl3.setFlightId((int) flID3);
                long slID3 = dbAdapter.insertInStatusLogger(sl3);


                FlightLogger fl4 = new FlightLogger();
                fl4.setAltitude(50);
                fl4.setLat(29.4234);
                fl4.setLng(29.4132);
                fl4.setAssignmentId((int)assignmentID);
                long flID4 = dbAdapter.insertInFlightLogger(fl4);

                FlightLogger fl5 = new FlightLogger();
                fl5.setAltitude(55);
                fl5.setLat(27.4114);
                fl5.setLng(29.4222);
                fl5.setAssignmentId((int)assignmentID);
                long flID5 = dbAdapter.insertInFlightLogger(fl5);

                FlightLogger fl6 = new FlightLogger();
                fl6.setAltitude(60);
                fl6.setLat(27.4124);
                fl6.setLng(28.4232);
                fl6.setAssignmentId((int)assignmentID);
                long flID6 = dbAdapter.insertInFlightLogger(fl6);
                StatusLogger sl4 = new StatusLogger();
                sl4.setRisk("RISK");
                sl4.setStatus("LOW BATTERY");
                sl4.setDate(_creationDate);
                sl4.setBattery(45);
                sl4.setFlightId((int) flID6);
                long slID4 = dbAdapter.insertInStatusLogger(sl4);


                FlightLogger fl7 = new FlightLogger();
                fl7.setAltitude(55);
                fl7.setLat(26.4114);
                fl7.setLng(28.4222);
                fl7.setAssignmentId((int)assignmentID);
                long flID7 = dbAdapter.insertInFlightLogger(fl7);

                FlightLogger fl8 = new FlightLogger();
                fl8.setAltitude(40);
                fl8.setLat(25.4230);
                fl8.setLng(25.4120);
                fl8.setAssignmentId((int)assignmentID);
                long flID8 = dbAdapter.insertInFlightLogger(fl8);

                FlightLogger fl9 = new FlightLogger();
                fl9.setAltitude(25);
                fl9.setLat(22.4220);
                fl9.setLng(25.4130);
                fl9.setAssignmentId((int)assignmentID);
                long flID9 = dbAdapter.insertInFlightLogger(fl9);

                FlightLogger fl10 = new FlightLogger();
                fl10.setAltitude(10);
                fl10.setLat(24.4214);
                fl10.setLng(25.4122);
                fl10.setAssignmentId((int)assignmentID);
                long flID10 = dbAdapter.insertInFlightLogger(fl10);

            }
        });

        mapBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SelectionMapActivity.class);
                startActivity(intent);
                DroneRequest droneRequest = DroneRequest.getInstance();
                droneRequest.getDroneTaskRequest().setLng(24);
                droneRequest.getDroneTaskRequest().setLat(44);
                droneRequest.getDroneTaskRequest().setAltitude(124);

            }
        });

        mapsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                Log.e("FROM MAINACTIVITY: ", lat_txt.getText().toString() + " : " + lng_txt.getText().toString());
                intent.putExtra("lat", lat_txt.getText().toString());
                intent.putExtra("lng", lng_txt.getText().toString());
                MainActivity.this.startActivity(intent);
                // fragment = new MapFragment();
                //getSupportFragmentManager()
                //        .beginTransaction()
                 //       .replace()
            }
        });

        btnPickTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Control.class);
                startActivity(intent);
            }
        });

        btnCheckDrone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Request Data from gps!
                mqttService.publish(TOPICS.TOPIC_4, "4#25");
            }
        });


    }



    private static TrustManager[] trustManagers;
    public static class _FakeX509TrustManager implements javax.net.ssl.X509TrustManager {
        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return (_AcceptedIssuers);
        }
    }
    public static void allowAllSSL() {

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        javax.net.ssl.SSLContext context;

        if (trustManagers == null) {
            trustManagers = new TrustManager[]{new _FakeX509TrustManager()};
        }

        try {
            context = javax.net.ssl.SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            Log.e("allowAllSSL", e.toString());
        } catch (KeyManagementException e) {
            Log.e("allowAllSSL", e.toString());
        }
    }

    @Override
    public void updateMyTextCallback(String text) {
        String[] values = text.split("#", -1);
        byte[] responseArray = new byte[4];//text.getBytes();
        responseArray[0] = Byte.parseByte(values[2]);
        responseArray[1] = Byte.parseByte(values[3]);
        responseArray[2] = Byte.parseByte(values[4]);
        responseArray[3] = Byte.parseByte(values[5]);
        lat_txt.setText((int)responseArray[0] + "");
        lng_txt.setText((int)responseArray[1] + "");
        speed_txt.setText((int)responseArray[2] + "");
        altitude_txt.setText((int)responseArray[3] + "");
    }

}