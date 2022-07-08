package com.example.mobiledroneapp.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseDroneResponse {

    private static ParseDroneResponse instance;

    private List<byte[]> response;

    private ParseDroneResponse(List<byte[]> response) {
        this.response = response;
    }

    private ParseDroneResponse() {
        this.response = new ArrayList<>();
    }

    public static ParseDroneResponse getInstance() {
        if(instance == null) {
            instance = new ParseDroneResponse();
        }
        return instance;
    }

    public List<byte[]> getResponse() {
        return response;
    }

    public void setResponse(List<byte[]> response) {
        this.response = response;
    }

    public void addResponse(byte[] r) {
        this.response.add(r);
    }

    public void resetResponse() {
        if(this.response.size() > 0) {
            this.response = new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return "ParseDroneResponse{" +
                "response=" + response +
                '}';
    }
}
