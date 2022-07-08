package com.example.mobiledroneapp.helpers;

import java.nio.ByteBuffer;

public class DroneRequest {

    private DroneTaskRequest droneTaskRequest;

    private int requestSize = 0;

    private static DroneRequest instance;

    private DroneRequest() {
        droneTaskRequest = new DroneTaskRequest();
    }

    public static DroneRequest getInstance() {
        if (instance == null) {
            instance = new DroneRequest();
        }
        return instance;
    }

    public DroneTaskRequest getDroneTaskRequest() {
        return droneTaskRequest;
    }

    public void setDroneTaskRequest(DroneTaskRequest droneTaskRequest) {
        this.droneTaskRequest = droneTaskRequest;
    }

    public byte[] getAsByteArray() {
        byte a1 = droneTaskRequest.getCmd();
        byte a2 = droneTaskRequest.getAck();
        byte arr[] = null;
        if(requestSize > 2) {
            byte a3 = (byte)droneTaskRequest.getLat();
            byte a4 = (byte)droneTaskRequest.getLng();
            byte a5 = (byte)droneTaskRequest.getMode().charAt(0);
            byte a6 = (byte)droneTaskRequest.getAltitude(); //ByteBuffer.allocate(Long.BYTES).putLong(droneTaskRequest.getAltitude()).array();
            arr = new byte[]{a1, a2, a3, a4, a5, a6};
        } else {
            arr = new byte[]{a1, a2};
        }
        return arr;
    }

    public int getRequestSize() {
        return requestSize;
    }

    public void setRequestSize(int requestSize) {
        this.requestSize = requestSize;
    }

    @Override
    public String toString() {
        return "DroneRequest{" +
                "droneTaskRequest=" + droneTaskRequest +
                ", requestSize=" + requestSize +
                '}';
    }
}
