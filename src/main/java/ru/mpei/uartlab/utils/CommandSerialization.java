package ru.mpei.uartlab.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.mpei.uartlab.model.Command;

import java.io.IOException;
import java.util.Optional;

@Component
public class CommandSerialization {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static Optional<Command> getCommand(String jsonCommand) {
        try {
            Command command = objectMapper.readValue(jsonCommand, Command.class);
            return Optional.of(command);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static Optional<Command> getCommand(byte[] jsonCommand) {
        try {
            Command command = objectMapper.readValue(jsonCommand, Command.class);
            return Optional.of(command);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
