package com.example.mobiledroneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.PolygonOptions;

public class SelectionMapActivity extends AppCompatActivity {

    Button drawBtn;
    GoogleMaps_Selection fragment = new GoogleMaps_Selection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_map);

        drawBtn = findViewById(R.id.drawBtn);

        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment.polygon != null) {
                    fragment.polygon.remove();
                }
                // create polygonOptions
                PolygonOptions polygonOptions = new PolygonOptions();
                polygonOptions.addAll(fragment.latLngList);
                polygonOptions.clickable(true);
                //fragment.polygon = fragment.supportMapFragment
            }
        });



        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.selection_frame, fragment)
                .commit();
    }
}