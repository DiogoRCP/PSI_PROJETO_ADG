package com.example.carbuddy.utils;

import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.carbuddy.R;
import com.example.carbuddy.controllers.Pagina_Inicial;
import com.example.carbuddy.singletons.LoginSingleton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTT {
    public static void connectionMQTT(Context context) {

        String clientId = MqttClient.generateClientId();
        MqttAndroidClient client = new MqttAndroidClient(context, "tcp://10.0.2.2:1883", clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("MQTT", "onSuccess");
                    subscribeMQTT(client, context);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MQTT", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private static void subscribeMQTT(MqttAndroidClient client, Context context) {
        String topic = "REPAIR-" + LoginSingleton.getInstance(context).getLogin().getId();
        int qos = 0;
        try {
            client.subscribe(topic, qos);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("MQTT", "Connection Lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String mensagem = new String(message.getPayload());
                    String[] separated = mensagem.split(":");
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "VehicleRepair");
                    builder.setContentTitle(separated[1]);
                    builder.setContentText(separated[2]);
                    builder.setSmallIcon(R.drawable.ic_car_repair);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
                    managerCompat.notify(Integer.parseInt(separated[0]), builder.build());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
