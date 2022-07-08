package com.example.mobiledroneapp.models;

public class FlightLogger {

    private int id;//ID						int
    private int assignmentId;//ASSIGNMENTS_ID		int
    private double lat;//LAT						long
    private double lng;//LNG						long
    private long altitude;//ALTITUDE				long

    public FlightLogger() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getAltitude() {
        return altitude;
    }

    public void setAltitude(long altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        return "FlightLogger{" +
                "id=" + id +
                ", assignmentId=" + assignmentId +
                ", lat=" + lat +
                ", lng=" + lng +
                ", altitude=" + altitude +
                '}';
    }
}
