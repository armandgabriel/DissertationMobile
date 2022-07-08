package com.example.mqtt;

import android.content.Context;

import com.example.mobiledroneapp.helpers.StaticCONSTANTUtils;
import com.example.mobiledroneapp.helpers.ToastHelper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTDataService implements MqttCallback {

    private Context context;

    public MQTTDataService(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void connectionLost(Throwable cause) {
        ToastHelper.message(context, "Connection Lost: " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if(topic.equals(StaticCONSTANTUtils.TOPIC_1)) {

        }
        if(topic.equals(StaticCONSTANTUtils.TOPIC_2)) {

        }
        if(topic.equals(StaticCONSTANTUtils.TOPIC_3)) {

        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        ToastHelper.message(context, "Message delivered!");
    }
}
