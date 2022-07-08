package com.example.mobiledroneapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragmentPath extends Fragment {

    ArrayList<String> flightPoints = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_fragment_path, container, false);
        // init map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager()
                        .findFragmentById(R.id.google_map2);
        Bundle args = getArguments();

        flightPoints = args.getStringArrayList("flightPoints");




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

                for(String flPoint : flightPoints) {
                    String[] coordonates = flPoint.split("#", -1);
                    double lat = Double.parseDouble(coordonates[0]);
                    double lng = Double.parseDouble(coordonates[1]);
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng)).title(lat + " : " + lng);
                    //googleMap.clear();
                    LatLng latLngT = new LatLng(lat, lng);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            latLngT, 10
                    ));
                    // Ad
                    googleMap.addMarker(marker);
                }

            }
        });

        return view;
    }
}