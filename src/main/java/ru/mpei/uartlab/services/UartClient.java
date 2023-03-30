package ru.mpei.uartlab.services;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mpei.uartlab.model.Command;
import ru.mpei.uartlab.utils.NumberConverter;

import java.nio.ByteBuffer;

@Slf4j
@Service
public class UartClient {

    private SerialPort port;

    public void start(String portName, int boundRate) {
        stop();

        port = SerialPort.getCommPort(portName);
        port.setBaudRate(boundRate);
        port.openPort();

        log.info("Successfully started UartClient");
    }

    public boolean sendCommand(Command command) {
        if (port.isOpen()) {
            try {
                byte[] bytes = NumberConverter.shortToByte(command.value());
                port.writeBytes(bytes, bytes.length);
                return true;
            } catch (Exception e) {
                log.error("Can't send command. Reason: {}", e.getMessage());
                return false;
            }
        }
        log.warn("Sending command is unavailable. Try to start client.");
        return false;
    }

    public void stop() {
        if (port != null && port.isOpen()) {
            port.closePort();
        }
    }

    @PreDestroy
    private void destroy() {
        stop();
    }
}
