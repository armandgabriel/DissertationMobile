package com.example.mobiledroneapp.helpers;

public class DroneTaskRequest {
    byte cmd;
    byte ack;
    int lat = 0;
    int lng = 0;
    String mode;
    int altitude = 0;

    public DroneTaskRequest() {
    }

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public byte getAck() {
        return ack;
    }

    public void setAck(byte ack) {
        this.ack = ack;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        return "DroneTaskRequest{" +
                "cmd=" + cmd +
                ", ack=" + ack +
                ", lat=" + lat +
                ", lng=" + lng +
                ", mode='" + mode + '\'' +
                ", altitude=" + altitude +
                '}';
    }
}
