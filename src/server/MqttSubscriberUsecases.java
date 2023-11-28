package server;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttSubscriberUsecases implements MqttCallback {

    private MqttClient mqtt;
    private RemoteObject remoteObject;

    public MqttSubscriberUsecases() {}

    public void execute(RemoteObject remoteObject) {
        this.remoteObject = remoteObject;

        try {
            mqtt = new MqttClient("tcp://127.0.0.1:1883", remoteObject.getName(), new MemoryPersistence());
            mqtt.setCallback(this);

            var mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setConnectionTimeout(60);
            mqttConnectOptions.setKeepAliveInterval(60);
            mqttConnectOptions.setAutomaticReconnect(true);

            mqtt.connect(mqttConnectOptions);
            mqtt.subscribe("MASTER-MESSAGE-RECEIVED");
            System.out.println("[INFO] Servidor inscrito no tópico de mensagens");
        } catch (Exception e) {
            System.out.println("[ERROR] MQTT não pôde se inscrever no tópico");
            System.exit(0);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("[ERROR] Conexão perdida com o MQTT, tentando reconectar");
        System.out.println("\n[INFO] Se não houver reconexão, as mensagens não serão sincronizadas via tópico");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        remoteObject.addMessage(new String(message.getPayload()));
        System.out.println("\n[INFO] Mensagem '" + new String(message.getPayload()) + "' sincronizada via tópico");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}

    public void stop() {
        try {
            mqtt.disconnect();
        } catch (MqttException e) {
            System.out.println("[ERROR] Falha ao desconectar objeto remoto 'master' do tópico");
            e.printStackTrace();
        }
    }
}