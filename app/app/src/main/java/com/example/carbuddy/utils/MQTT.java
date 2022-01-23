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

/** Conexão ao canal de Repairs **/
public class MQTT {
    public static void connectionMQTTRepair(Context context) {

        //Criação de um cliente
        String clientId = MqttClient.generateClientId();
        //Endpoint do canal MQTT
        MqttAndroidClient client = new MqttAndroidClient(context, "tcp://10.0.2.2:1883", clientId);

        //Conexão ao canal MQTT
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                //Com sucesso
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // Entrar na função de conexão subscribeMQTTRepairs
                    Log.d("MQTT", "onSuccess");
                    subscribeMQTTRepair(client, context);
                }
                //Sem sucesso, mensagem de erro
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MQTT", "onFailure");

                }
            });
            //Exception
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /** Conexão ao canal de Schedules **/
    public static void connectionMQTTSchedule(Context context) {

        //Criação de um cliente
        String clientId = MqttClient.generateClientId();
        //Endpoint do canal MQTT
        MqttAndroidClient client = new MqttAndroidClient(context, "tcp://10.0.2.2:1883", clientId);

        //Conexão ao canal MQTT
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                //Com sucesso
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // Entrar na função de conexão subscribeMQTTSchedules
                    Log.d("MQTT", "onSuccess");
                    subscribeMQTTSchedules(client, context);
                }

                //Sem sucesso, mensagem de erro
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MQTT", "onFailure");

                }
            });
            //Exception
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /** subscrição do canal Repair **/
    private static void subscribeMQTTRepair(MqttAndroidClient client, Context context) {
        //Nome do Canal REPAIR- + id do user
        String topic = "REPAIR-" + LoginSingleton.getInstance(context).getLogin().getId();
        int qos = 1;
        try {
            //Subscrição do canal
            client.subscribe(topic, qos);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("MQTT", "Connection Lost");
                }

                //Mensagem recebida- criar notificação
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    //Receber a mensagem
                    String mensagem = new String(message.getPayload());
                    //Separar conteudo da mensagem (a sms é enviada com vários caracteres ::: com o intuito da separar na APP)
                    String[] separated = mensagem.split(":::");
                    //Criar uma notificação
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "VehicleRepair");
                    //titulo da notificação
                    builder.setContentTitle(separated[1]);
                    //Conteudo da notificação
                    builder.setContentText(separated[2]);
                    //Icone da notificação
                    builder.setSmallIcon(R.drawable.ic_car_repair);
                    builder.setAutoCancel(true);

                    // Criar notificação
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
                    managerCompat.notify(1 + Integer.parseInt(separated[0]), builder.build());

                    // Delete retain message (Apagar mensagem após a mostragem da notificação para não mostrar 2x a mesma mensagem)
                    client.publish(topic, new byte[]{}, qos, true);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
            //Exception
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /** subscrição do canal Schedule **/
    private static void subscribeMQTTSchedules(MqttAndroidClient client, Context context) {
        //Nome do Canal SCHEDULE- + id do user
        String topic = "SCHEDULE-" + LoginSingleton.getInstance(context).getLogin().getId();
        int qos = 1;
        try {
            //Subscrição do canal
            client.subscribe(topic, qos);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("MQTT", "Connection Lost");
                }

                //Mensagem recebida- criar notificação
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    //Receber a mensagem
                    String mensagem = new String(message.getPayload());
                    //Separar conteudo da mensagem (a sms é enviada com vários caracteres ::: com o intuito da separar na APP)
                    String[] separated = mensagem.split(":::");
                    // Data e hora
                    String[] separatedDate = separated[3].split("T");
                    // Hora separada por hora, minutos e segundos
                    String[] separatedTime = separatedDate[1].split(":");
                    //Criar uma notificação
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "VehicleSchedule");
                    //titulo da notificação
                    builder.setContentTitle("Schedule " + separated[1]);
                    //Conteudo da notificação
                    builder.setContentText(separated[2] + " - " + separatedDate[0] + " | " + separatedTime[0] + ":" + separatedTime[1]);
                    //Icone da notificação
                    builder.setSmallIcon(R.drawable.ic_car_schedule);
                    builder.setAutoCancel(true);

                    // Criar notificação
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
                    managerCompat.notify(2 + Integer.parseInt(separated[0]), builder.build());

                    // Delete retain message (Apagar mensagem após a mostragem da notificação para não mostrar 2x a mesma mensagem)
                    client.publish(topic, new byte[]{}, qos, true);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
            //Exception
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
