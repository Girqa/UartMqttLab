package ru.mpei.uartlab.services;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ru.mpei.uartlab.model.Command;
import ru.mpei.uartlab.utils.CommandSerialization;

import java.util.Optional;

@Slf4j
@Service
public class MqttCommandListener implements MqttCallback {

    private final UartClient client;

    public MqttCommandListener(UartClient client) {
        this.client = client;
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("Error communicating with broker. Reason: {}", cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        byte[] payload = message.getPayload();
        Optional<Command> optionalCommand = CommandSerialization.getCommand(payload);

        if (optionalCommand.isPresent()) {
            log.info("Received command: {}", optionalCommand.get());
            client.sendCommand(optionalCommand.get());
        } else {
            log.warn("Can't deserialize command.");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
