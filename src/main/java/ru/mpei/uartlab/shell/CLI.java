package ru.mpei.uartlab.shell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.mpei.uartlab.services.MqttClientAdapter;
import ru.mpei.uartlab.services.UartClient;

@Slf4j
@ShellComponent
public class CLI {

    private final MqttClientAdapter mqttClient;

    private final UartClient uartClient;

    public CLI(MqttClientAdapter mqttClient, UartClient uartClient) {
        this.mqttClient = mqttClient;
        this.uartClient = uartClient;
    }

    @ShellMethod(value = "Start Mqtt", key = {"start-mqtt"})
    public void startMqttClient(
            @ShellOption(value = {"uri"}, defaultValue = "tcp://localhost:1883") String serverURI,
            @ShellOption(value = {"id"}, defaultValue = "Client") String clientId) {
        mqttClient.start(serverURI, clientId);
    }

    @ShellMethod(value = "Start Uart", key = {"start-uart"})
    public void startUartClient(
            @ShellOption(value = "p", defaultValue = "COM5") String portName,
            @ShellOption(value = "b", defaultValue = "115200") String boundRate) {
        try {
            uartClient.start(portName, Integer.parseInt(boundRate));
        } catch (Exception e) {
            log.warn("Can't start uart client. Reason: {}", e.getMessage());
        }
    }

    @ShellMethod(value = "Set mqtt topic to listen", key = {"mqtt-topic"})
    public void setTopicToListen(@ShellOption(value = "topic", defaultValue = "topic") String topic) {
        mqttClient.subscribeOnTopic(topic);
    }
}
