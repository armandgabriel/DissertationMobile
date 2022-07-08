package com.example.mobiledroneapp.mqtt;

import android.util.Log;

import com.example.mqtt.MQTTService;


public class MQTTBackgroundTask implements Runnable {

    private volatile boolean running = true;

    private MQTTService mqttService = MQTTService.getInstance();

    public synchronized void doStop() {
        this.running = false;
    }

    public synchronized  boolean keepRunning() {
        return this.running == false;
    }



    @Override
    public void run() {
        while(running) {
            if(mqttService.getConnectionStatus())
                mqttService.reconnect();
            try {
                Thread.sleep(5L * 1000L);
            } catch (InterruptedException e) {
                Log.d("MQTT_THREAD_EXCEPTION", e.toString());
                e.printStackTrace();
            }
        }
    }
}
