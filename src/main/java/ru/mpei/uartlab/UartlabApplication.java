package ru.mpei.uartlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class UartlabApplication {

    public static void main(String[] args) {
        SpringApplication.run(UartlabApplication.class, args);
    }
}
