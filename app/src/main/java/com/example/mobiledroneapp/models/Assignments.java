package com.example.mobiledroneapp.models;

public class Assignments {

    private int id;//ID						int
    private String startDate;//START_DATE				text
    private String endDate;//END_DATE				text
    private String mode;//MODE					text
    private String flightDuration;//FLIGHT_DURATION		int

    public Assignments() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    @Override
    public String toString() {
        return "Assignments{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", mode='" + mode + '\'' +
                ", flightDuration='" + flightDuration + '\'' +
                '}';
    }
}
