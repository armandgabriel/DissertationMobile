package com.example.mqtt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobiledroneapp.HomeScreen;
import com.example.mobiledroneapp.db.DBHelperAdapter;
import com.example.mobiledroneapp.helpers.DroneRequest;
import com.example.mobiledroneapp.helpers.DroneTaskRequest;
import com.example.mobiledroneapp.helpers.ParseDroneResponse;
import com.example.mobiledroneapp.helpers.StaticCONSTANTUtils;
import com.example.mobiledroneapp.helpers.TOPICS;
import com.example.mobiledroneapp.helpers.ToastHelper;


//import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

//import info.mqtt.android.service.Ack;
//import info.mqtt.android.service.MqttAndroidClient;
import com.example.mobiledroneapp.MyUpdateCallback;
import com.example.mobiledroneapp.models.Assignments;
import com.example.mobiledroneapp.models.FlightLogger;
import com.example.mobiledroneapp.models.StatusLogger;

public class MQTTService {

    private static MQTTService instance;

    private MqttAndroidClient client;
    private MqttConnectOptions options;
    private IMqttToken token;
    private Context context;

    private MyUpdateCallback updateCallback = null;

    private boolean isRunning = false;

    private MQTTService() {}


    private MQTTService(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static MQTTService getInstance() {
        if(instance == null) {
            instance = new MQTTService();
        }
        return instance;
    }

    public String init() {
        String serialID = MqttClient.generateClientId();
        //String serialID = "drone_test_armand";
        Log.i("MQTT", "SERIAL ID: " + serialID);
        //MemoryPersistence memoryPersistence = new MemoryPersistence();
        client = new MqttAndroidClient(context, StaticCONSTANTUtils.MQTTHOST, serialID);//, MqttAndroidClient.Ack.AUTO_ACK);
        //MQTTDataService dataService = new MQTTDataService(context);
        //client.setCallback(dataService);
        //options = new MqttConnectOptions();
        //options.setCleanSession(true);
        //options.setKeepAliveInterval(1000);
        //options.setUserName("");
        //options.setPassword("".toCharArray());
        try {
            //token = client.connect(options);
            token = client.connect();
            isRunning = true;
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("MQTT_BROKER", "CONNECTED!");
                    //ToastHelper.message(context, "Connected!");
                    subscribe();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("MQTT_BROKER", "FAILED TO CONNECt: " + exception.getMessage());
                    //ToastHelper.message(context, "Not Connected: " + exception.getMessage());
                    isRunning = false;
                }
            });
        } catch(Exception e) {
            Log.e("ERROR_MQTT_BROKER", e.getMessage());
            isRunning = false;
            e.printStackTrace();
            return "Not Connected";
        }
        return "Connected";
    }

    public boolean getConnectionStatus() {
        return this.isRunning;
    }

    public boolean disconnect() {
        try {
            if(isRunning)
                this.client.disconnect();
            this.isRunning = false;
            return true;
        } catch (MqttException e) {
            Log.d("MQTT_DISCONNECT", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void reconnect() {
        try {
            //token = client.connect(options);
            token = client.connect();
            this.isRunning = true;
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("MQTT_BROKER", "CONNECTED!");
                    //ToastHelper.message(context, "Connected!");
                    subscribe();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("MQTT_BROKER", "FAILED TO CONNECt: " + exception.getMessage());
                    //ToastHelper.message(context, "Not Connected: " + exception.getMessage());
                    isRunning = false;
                }
            });
        } catch(Exception e) {
            Log.e("ERROR_MQTT_BROKER", e.getMessage());
            isRunning = false;
            e.printStackTrace();

        }
    }

    public void publish(TOPICS _topic, String command) {
        String topic = _topic.getTopic();//StaticCONSTANTUtils.TOPIC_2;
        String message = command;
        DroneRequest droneRequest = DroneRequest.getInstance();
        //DroneTaskRequest droneTaskRequest = new DroneTaskRequest();
        switch (_topic) {
            case TOPIC_0:
                droneRequest.getDroneTaskRequest().setCmd((byte)0);
                droneRequest.getDroneTaskRequest().setAck((byte)2);
                droneRequest.setRequestSize(2);
                //droneRequest.setDroneTaskRequest(droneRequest.getDroneTaskRequest());
                break;
            case TOPIC_1:
                droneRequest.getDroneTaskRequest().setCmd((byte)1);
                droneRequest.getDroneTaskRequest().setAck((byte)2);
                droneRequest.setRequestSize(2);
                //droneRequest.setDroneTaskRequest(droneRequest.getDroneTaskRequest());
                break;
            case TOPIC_2:
                droneRequest.getDroneTaskRequest().setCmd((byte)2);
                droneRequest.getDroneTaskRequest().setAck((byte)2);
                droneRequest.setRequestSize(2);
                //droneRequest.setDroneTaskRequest(droneRequest.getDroneTaskRequest());
                break;
            case TOPIC_3:
                droneRequest.getDroneTaskRequest().setCmd((byte)3);
                droneRequest.getDroneTaskRequest().setAck((byte)2);
                droneRequest.setRequestSize(2);
                //droneRequest.setDroneTaskRequest(droneRequest.getDroneTaskRequest());
                break;
            case TOPIC_4:
                droneRequest.getDroneTaskRequest().setCmd((byte)4);
                droneRequest.getDroneTaskRequest().setAck((byte)2);
                droneRequest.setRequestSize(2);
                //droneRequest.setDroneTaskRequest(droneRequest.getDroneTaskRequest());
                break;
            case TOPIC_5:
                droneRequest.getDroneTaskRequest().setCmd((byte)5);
                droneRequest.getDroneTaskRequest().setAck((byte)2);
                //droneTaskRequest.setLat(24);
                //droneTaskRequest.setLng(26);
                //droneTaskRequest.setCmd((byte)99);
                //droneTaskRequest.setAltitude(120);
                droneRequest.setRequestSize(6);
                //droneRequest.setDroneTaskRequest(droneRequest.getDroneTaskRequest());
                break;
            case TOPIC_6:
                droneRequest.getDroneTaskRequest().setCmd((byte)6);
                droneRequest.getDroneTaskRequest().setAck((byte)2);
                droneRequest.setRequestSize(2);
                //droneRequest.setDroneTaskRequest(droneRequest.getDroneTaskRequest());
                break;
            case TOPIC_7:
                droneRequest.getDroneTaskRequest().setCmd((byte)7);
                droneRequest.getDroneTaskRequest().setAck((byte)2);
                droneRequest.setRequestSize(2);
                //droneRequest.setDroneTaskRequest(droneRequest.getDroneTaskRequest());
                break;
            case TOPIC_8:
                droneRequest.getDroneTaskRequest().setCmd((byte)8);
                droneRequest.getDroneTaskRequest().setAck((byte)2);
                droneRequest.setRequestSize(2);
                //droneRequest.setDroneTaskRequest(droneRequest.getDroneTaskRequest());
                break;
            default:
                break;
        }
        try {
            client.publish(topic, droneRequest.getAsByteArray(), 1, true);
        } catch (Exception e) {
            Log.e("ERROR_MQTT_PUBLISH", e.getMessage());
            e.printStackTrace();
        }
    }

    public void subscribe() {
        try {
            //client.subscribe(StaticCONSTANTUtils.TOPIC_1, 1);
            //client.subscribe(StaticCONSTANTUtils.TOPIC_9, 1);
            String topic = TOPICS.valueOf("TOPIC_9").getTopic();
            client.subscribe(topic,2);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    if(cause != null) {
                        isRunning = true;
                        Log.d("MQTT_CALLBACK", "Connection lost: " + cause.getMessage() + " " + cause.getCause().toString());
                    } else {
                        isRunning = false;
                        Log.d("MQTT_CALLBACK", "Disconnected");
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.d("MQTT_CALLBACK", "TOPIC: " + topic + " MESSAGE: " + message);
                    byte[] responseArray = message.toString().getBytes();
                    byte m1 = responseArray[0];
                    byte m2 = responseArray[1];
                    //EditText edTxtStatus = (EditText) ((Activity)context).findViewById(R.id.edTxtStatus);

                    ParseDroneResponse droneResponse = ParseDroneResponse.getInstance();
                    droneResponse.addResponse(responseArray);
                    if(m1 == 1 && m2 == 2) {
                        String response = "RECEIVED FROM DRONE: Connected Successfully!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    } else if(m1 == 1 && m2 == 3) {
                        String response = "RECEIVED FROM DRONE: Connected Not Successfully!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    }
                    if(m1 == 2 && m2 == 2) {
                        String response = "RECEIVED FROM DRONE: Drone is stopping!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    } else if(m1 == 2 && m2 == 3) {
                        String response = "RECEIVED FROM DRONE: Drone can't stop!!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    }
                    if(m1 == 3 && m2 == 2) {
                        String response = "RECEIVED FROM DRONE: Disconnected!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    } else if(m1 == 3 && m2 == 3) {
                        String response = "RECEIVED FROM DRONE: Can't Disconnect!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    }
                    if(m1 == 4 && m2 == 2) {
                        String response = "";// = "RECEIVED FROM DRONE: Checking drone status!";
                        //response = responseArray.toString();
                        if(updateCallback != null) {
                            for(byte b : responseArray) {
                                response = response + (int)b + "#";
                            }
                            StringBuffer sb = new StringBuffer(response);
                            try {
                                sb.deleteCharAt(sb.length() - 1);
                            } catch(Exception e ) {
                                e.printStackTrace();
                            }
                            updateCallback.updateMyTextCallback(sb.toString());
                        }
                    } else if(m1 == 4 && m2 == 3) {
                        String response = "RECEIVED FROM DRONE: Did not receive drone status!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    }
                    if(m1 == 5 && m2 == 2) {
                        String response = "RECEIVED FROM DRONE: Assigned task successfully!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    } else if(m1 == 5 && m2 == 3) {
                        String response = "RECEIVED FROM DRONE: Could not assign task!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    }
                    if(m1 == 6 && m2 == 2) {
                        String response = "RECEIVED FROM DRONE: Drone is flying!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    } else if(m1 == 6 && m2 == 3) {
                        String response = "RECEIVED FROM DRONE: Drone can't fly!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    }
                    if(m1 == 7 && m2 == 2) {
                        String response = "RECEIVED FROM DRONE: Calling drone home!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    } else if(m1 == 7 && m2 == 3) {
                        String response = "RECEIVED FROM DRONE: Can't call drone home!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    }
                    if(m1 == 8 && m2 == 2) {
                        String response = "RECEIVED FROM DRONE: Landing drone!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    } else if(m1 == 8 && m2 == 3) {
                        String response = "RECEIVED FROM DRONE: Can't land the drone!";
                        if(updateCallback != null) {
                            updateCallback.updateMyTextCallback(response);
                        }
                    }

                    // GET LAST ASSIGNMENT:
                    DBHelperAdapter dbHelperAdapter = new DBHelperAdapter(context);


                    if(m1 == 14 && m2 == 43) {
                        String response = "Fire DETECTED!!!";
                        Assignments assignment = dbHelperAdapter.getLastAssignement();
                        // SELECT FLIGHT LIG FROM IT
                        FlightLogger fl = new FlightLogger();
                        DroneTaskRequest dtr = DroneRequest.getInstance().getDroneTaskRequest();
                        fl.setLat(dtr.getLat());
                        fl.setLng(dtr.getLng());
                        fl.setAltitude(dtr.getAltitude());
                        fl.setAssignmentId(assignment.getId());
                        long flID = dbHelperAdapter.insertInFlightLogger(fl);
                        StatusLogger sl = new StatusLogger();
                        sl.setStatus("DANGER");
                        sl.setBattery(90);
                        sl.setRisk("FIRE");
                        sl.setFlightId((int)flID);
                        sl.setDate(assignment.getStartDate());
                        long slID = dbHelperAdapter.insertInStatusLogger(sl);
                        //if(updateCallback != null)
                        //    updateCallback.updateMyTextCallback(response);
                    } else if(m1 == 14 && m2 == 42) {
                        String response = "Human DETECTED!!!";
                        Assignments assignment = dbHelperAdapter.getLastAssignement();
                        // SELECT FLIGHT LIG FROM IT
                        FlightLogger fl = new FlightLogger();
                        DroneTaskRequest dtr = DroneRequest.getInstance().getDroneTaskRequest();
                        fl.setLat(dtr.getLat());
                        fl.setLng(dtr.getLng());
                        fl.setAltitude(dtr.getAltitude());
                        fl.setAssignmentId(assignment.getId());
                        long flID = dbHelperAdapter.insertInFlightLogger(fl);
                        StatusLogger sl = new StatusLogger();
                        sl.setStatus("MEDIUM DANGER");
                        sl.setBattery(90);
                        sl.setRisk("HUMAN");
                        sl.setFlightId((int)flID);
                        sl.setDate(assignment.getStartDate());
                        long slID = dbHelperAdapter.insertInStatusLogger(sl);
                        //if(updateCallback != null)
                        //    updateCallback.updateMyTextCallback(response);
                    } else if(m1 == 14 && m2 == 44) {
                        String response = "No Danger DETECTED!!!";
                        Assignments assignment = dbHelperAdapter.getLastAssignement();
                        // SELECT FLIGHT LIG FROM IT
                        FlightLogger fl = new FlightLogger();
                        DroneTaskRequest dtr = DroneRequest.getInstance().getDroneTaskRequest();
                        fl.setLat(dtr.getLat());
                        fl.setLng(dtr.getLng());
                        fl.setAltitude(dtr.getAltitude());
                        fl.setAssignmentId(assignment.getId());
                        long flID = dbHelperAdapter.insertInFlightLogger(fl);
                        StatusLogger sl = new StatusLogger();
                        sl.setStatus("GOOD");
                        sl.setBattery(90);
                        sl.setRisk("NO RISK");
                        sl.setFlightId((int)flID);
                        sl.setDate(assignment.getStartDate());
                        long slID = dbHelperAdapter.insertInStatusLogger(sl);
                        //if(updateCallback != null)
                        //    updateCallback.updateMyTextCallback(response);
                    }
                    if (m1 == 16 && m2 == 17) {
                        Assignments assignment = dbHelperAdapter.getLastAssignement();
                        FlightLogger fl = new FlightLogger();
                        DroneTaskRequest dtr = DroneRequest.getInstance().getDroneTaskRequest();
                        fl.setLat(dtr.getLat());
                        fl.setLng(dtr.getLng());
                        fl.setAltitude(dtr.getAltitude());
                        fl.setAssignmentId(assignment.getId());
                        long flID = dbHelperAdapter.insertInFlightLogger(fl);
                        StatusLogger sl = new StatusLogger();
                        if(responseArray[7] == 1)
                            sl.setStatus("GOOD");
                        if(responseArray[7] == 2)
                            sl.setStatus("LOW BATTERY");
                        if(responseArray[7] == 3)
                            sl.setStatus("GOING HOME");
                        if(responseArray[7] == 4)
                            sl.setStatus("HOME");

                        sl.setBattery(90);
                        if(responseArray[8] == 1)
                            sl.setRisk("NO RISK");
                        if(responseArray[8] == 2)
                            sl.setRisk("LOW BATTERY");
                        sl.setFlightId((int)flID);
                        sl.setDate(assignment.getStartDate());
                        long slID = dbHelperAdapter.insertInStatusLogger(sl);
                    }

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d("MQTT_CALLBACK", "Delivery completed..");
                }
            });
        } catch (Exception e) {
            Log.e("ERROR_MQTT_CALLBACK", e.getMessage());
            isRunning = false;
            e.printStackTrace();
        }
    }

    public void setMyUpdateCallback(MyUpdateCallback homeScreen) {
        this.updateCallback = homeScreen;
    }
}
