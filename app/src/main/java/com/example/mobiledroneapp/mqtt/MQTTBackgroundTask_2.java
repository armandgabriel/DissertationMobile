package com.example.mobiledroneapp.mqtt;

import com.example.mqtt.MQTTService;

public class MQTTBackgroundTask_2 implements Runnable {

    private MQTTService mqttService = MQTTService.getInstance();

    private volatile boolean running = true;

    public synchronized void doStop() {
        this.running = false;
    }

    public synchronized  boolean keepRunning() {
        return this.running == false;
    }

    @Override
    public void run() {
        while(running) {

        }
    }
}
