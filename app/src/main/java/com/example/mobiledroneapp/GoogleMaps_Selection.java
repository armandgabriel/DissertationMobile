package com.example.mobiledroneapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;
import java.util.List;


public class GoogleMaps_Selection extends Fragment {

    public Polygon polygon = null;
    public List<LatLng> latLngList = new ArrayList<>();
    public List<Marker> markerList = new ArrayList<>();

    int red = 0, green = 0, blue = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_google_maps__selection, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager()
                        .findFragmentById(R.id.google_map2);



        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        // Create Markers
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                        Marker marker = googleMap.addMarker(markerOptions);
                        // Add latLng and Marker
                        latLngList.add(latLng);
                        markerList.add(marker);

                    }
                });
            }
        });

        return view;
    }


}