package com.example.mobiledroneapp.models;

public class TableHistoryModel {

    private int id;

    private String flight;

    private int duration;

    private String risk;

    public TableHistoryModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    @Override
    public String toString() {
        return "TableHistoryModel{" +
                "id=" + id +
                ", flight='" + flight + '\'' +
                ", duration=" + duration +
                ", risk='" + risk + '\'' +
                '}';
    }
}
