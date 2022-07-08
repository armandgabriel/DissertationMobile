package com.example.mobiledroneapp.helpers;

public class StaticCONSTANTUtils {
    public static final String TABLE_NAME_OWNER = "OWNER";
    public static final String OWNER_COLUMNS[] = {
            "ID", "EMAIL", "NAME", "PASSWORD", "CREATE_DATE", "LAST_LOGIN", "HOME"
    };

    public static final String OWNER_ID = "ID";
    public static final String OWNER_NAME = "NAME";
    public static final String OWNER_EMAIL = "EMAIL";
    public static final String OWNER_PASS = "PASSWORD";
    public static final String OWNER_CREATION_DATE = "CREATE_DATE";
    public static final String OWNER_LAST_LOGIN = "LAST_LOGIN";
    public static final String OWNER_HOME = "HOME";

    public static final String TABLE_NAME_STATUS_LOGGER = "STATUS_LOGGER";
    public static final String S_LOGGER_ID = "ID";
    public static final String S_LOGGER_FLIGHT_ID = "FLIGHT_ID";
    public static final String S_LOGGER_STATUS = "STATUS";
    public static final String S_LOGGER_DATE = "DATE";
    public static final String S_LOGGER_RISK = "RISK";
    public static final String S_LOGGER_BATTERY = "BATTERY";

    public static final String TABLE_NAME_FLIGHT_LOGGER = "FLIGHT_LOGGER";
    public static final String F_LOGGER_ID = "ID";
    public static final String F_LOGGER_ASSIGNMENTS_ID = "ASSIGNMENTS_ID";
    public static final String F_LOGGER_LAT = "LAT";
    public static final String F_LOGGER_LNG = "LNG";
    public static final String F_LOGGER_ALTITUDE = "ALTITUDE";

    public static final String TABLE_NAME_ASSIGNMENTS = "ASSIGNMENTS";
    public static final String ASSIGNMENTS_ID = "ID";
    public static final String ASSIGNMENTS_START_DATE = "START_DATE";
    public static final String ASSIGNMENTS_END_DATE = "END_DATE";
    public static final String ASSIGNMENTS_MODE = "MODE";
    public static final String ASSIGNMENTS_FLIGHT_DURATION = "FLIGHT_DURATION";

    public static String MQTTHOST = "tcp://test.mosquitto.org:1883";

    public static final String TOPIC_0 = "drone/0/connect";
    public static final String TOPIC_1 = "drone/1/start";
    public static final String TOPIC_2 = "drone/2/stop";
    public static final String TOPIC_3 = "drone/3/disconnect";
    public static final String TOPIC_4 = "drone/4/checkDrone";
    public static final String TOPIC_5 = "drone/5/assignTask";
    public static final String TOPIC_6 = "drone/6/fly";
    public static final String TOPIC_7 = "drone/7/callDrone";
    public static final String TOPIC_8 = "drone/8/landDrone";
    public static final String TOPIC_9 = "drone/9/status";

}
