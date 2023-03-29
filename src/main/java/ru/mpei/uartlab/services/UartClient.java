package ru.mpei.uartlab.services;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mpei.uartlab.model.Command;

import java.io.*;

@Slf4j
@Service
public class UartClient {

    private PrintWriter writer;

    private SerialPort port;

    public void start(String portName, int boundRate) {
        stop();

        port = SerialPort.getCommPort(portName);
        port.setBaudRate(boundRate);
        port.openPort();

        OutputStream outputStream = port.getOutputStream();

        writer = new PrintWriter(new PrintStream(outputStream), true);
        log.info("Successfully started UartClient");
    }

    public boolean sendCommand(Command command) {
        if (writer != null && !writer.checkError() && port.isOpen()) {
            try {
                writer.println(command.value());
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
        return port.isOpen() && writer != null && !writer.checkError();
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
