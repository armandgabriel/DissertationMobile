package com.example.mobiledroneapp.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttCallback implements org.eclipse.paho.client.mqttv3.MqttCallback {

    private String topic;
    private String topicPayload;

    public String getTopicPayload() {
        return this.topicPayload;
    }

    public MqttCallback(String topic) {
        super();
        this.topic = topic;
        this.topicPayload = topicPayload;
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.e("MQTT ERROR" , cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //if(topic.equals(this.topic)) {
            String msgPayload = message.getPayload().toString();
            Log.i("MQTT", msgPayload);
            this.topicPayload = msgPayload;
        //}
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Delivery Complete: " + token.toString());
    }
}
