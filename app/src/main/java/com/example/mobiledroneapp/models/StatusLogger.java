package com.example.mobiledroneapp.models;

import java.io.Serializable;

public class StatusLogger implements Serializable {

    private int id;//						int
    private int flightId;//				int
    private String status;//					text
    private String date;//DATE					text
    private String risk;//RISK					text
    private int battery;//BATTERY				INT

    public StatusLogger() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    @Override
    public String toString() {
        return "StatusLogger{" +
                "id=" + id +
                ", flightId=" + flightId +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                ", risk='" + risk + '\'' +
                ", battery=" + battery +
                '}';
    }
}
