package ru.mpei.uartlab.services;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mpei.uartlab.model.Command;

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
                ByteBuffer buffer = ByteBuffer.wrap(new byte[2]);
                buffer.putShort(command.value());
                port.writeBytes(buffer.array(), 1, 1);
                return true;
            } catch (Exception e) {
                log.error("Can't send command. Reason: {}", e.getMessage());
                return false;
            }
        }
        log.warn("Sending command is unavailable. Try to start client.");
        return false;
    }

    public boolean isClientStarted() {
        return port.isOpen();
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
