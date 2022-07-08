package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    String lat;
    String lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        Log.e("From MainActivity2", extras.toString());

        if(extras.containsKey("lat")) {
            Log.e("LATITUDE:", i.getStringExtra("lat"));
            lat = i.getStringExtra("lat");
        }
        if(extras.containsKey("lng")) {
            Log.e("LONGITUDE:", i.getStringExtra("lng"));
            lng = i.getStringExtra("lng");
        }

        Fragment fragment = new MapFragment();
        Bundle args = new Bundle();
        Log.e("BEFORE FRAGMENT: ", lat + " : " + lng);
        List<String> argsList = new ArrayList<>();
        argsList.add(lat);
        argsList.add(lng);

        args.putString("lat", lat);
        args.putString("lng", lng);
        fragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}