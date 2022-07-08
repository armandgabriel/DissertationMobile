package com.example.mobiledroneapp.helpers;

public enum TOPICS {

    TOPIC_0("drone/0/connect"),
    TOPIC_1("drone/1/start"),
    TOPIC_2("drone/2/stop"),
    TOPIC_3("drone/3/disconnect"),
    TOPIC_4("drone/4/checkDrone"),
    TOPIC_5("drone/5/assignTask"),
    TOPIC_6("drone/6/fly"),
    TOPIC_7("drone/7/callDrone"),
    TOPIC_8("drone/8/landDrone"),
    TOPIC_9("drone/9/status");

    private String topic;

    TOPICS(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

}
