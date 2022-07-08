package com.example.mobiledroneapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    String lat;
    String lng;
    double latD;
    double longtD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // init map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager()
                .findFragmentById(R.id.google_map);
        Bundle args = getArguments();

        lat = args.getString("lat");
        lng = args.getString("lng");



        Log.e("From MapFragment", "LAT: " + lat + " lng: " + lng );
        latD = Double.parseDouble(lat);
        longtD = Double.parseDouble(lng);

//        MarkerOptions marker = new MarkerOptions().position(new LatLng(latD, longtD)).title("point");
//        googleMap.addMarker(marker);

        // Async Map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // When map is loaded
//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(@NonNull LatLng latLng) {
//                        // When click on the map
//                        // init marker
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        // Set Position
//                        markerOptions.position(latLng);
//                        //Set Title of marker
//                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//                        // Remove all markers
//                        googleMap.clear();
//                        // Animating to zoom marker
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                latLng, 10
//                        ));
//                        // Add marker on the map
//                        googleMap.addMarker(markerOptions);
//                    }
//                });
                MarkerOptions marker = new MarkerOptions().position(new LatLng(latD, longtD)).title(latD + " : " + longtD);
                googleMap.clear();
                LatLng latLngT = new LatLng(latD, longtD);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        latLngT, 10
                        ));
                        // Ad
                googleMap.addMarker(marker);
            }
        });

        return view;
    }
}