package ru.mpei.uartlab.services;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqttClientAdapter {

    private MqttClient client;

    private final MqttCommandListener commandListener;

    public MqttClientAdapter(MqttCommandListener commandListener) {
        this.commandListener = commandListener;
    }

    public void start(String serverURI, String clientId) {
        try {
            stop();
            client = new MqttClient(serverURI, clientId, new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setServerURIs(new String[]{serverURI});
            options.setCleanSession(true);
            options.setUserName("Name");
            options.setPassword("password".toCharArray());

            client.connect(options);

            client.setCallback(commandListener);
            log.info("Successfully started mqtt client.");
        } catch (MqttException e) {
            log.error("Can,t connect to mqtt broker. Reason: {}", e.getMessage());
        }
    }

    public void subscribeOnTopic(String topic) {
        if (client != null && client.isConnected()) {
            try {
                client.subscribe(topic);
                log.info("Subscribed on topic.");
            } catch (MqttException e) {
                log.error("Cant subscribe on topic. Reason: {}", e.getMessage());
            }
        }
    }

    public void stop() {
        if (client != null && client.isConnected()) {
            try {
                client.disconnect();
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PreDestroy
    private void destroy() {
        stop();
    }
}
