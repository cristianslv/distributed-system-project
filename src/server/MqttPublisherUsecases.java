package server;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublisherUsecases {

    private MqttPublisherUsecases() {}

    public static void execute(String clientMessage) {
        try (var mqtt = new MqttClient("tcp://127.0.0.1:1883", "MASTER", new MemoryPersistence())) {
            var mqttConnectOptions = new MqttConnectOptions();

            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setConnectionTimeout(10);
            mqttConnectOptions.setAutomaticReconnect(true);

            mqtt.connect(mqttConnectOptions);

            var message = new MqttMessage(clientMessage.getBytes());

            // exactly once
            message.setQos(2);
            mqtt.publish("MASTER-MESSAGE-RECEIVED", message);

            System.out.println("[INFO] Mensagem '" + clientMessage + "' publicada no tópico");
            mqtt.disconnect();
        } catch (Exception e) {
            System.out.println("[ERROR] MQTT não pôde enviar a mensagem ao tópico");
            e.printStackTrace();
        }
    }
}