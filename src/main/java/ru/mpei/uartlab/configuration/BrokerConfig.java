package ru.mpei.uartlab.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:application.properties")
@ConfigurationProperties(prefix = "mqtt.broker")
public class BrokerConfig {

    private String uri;

    private String username;

    private String password;

    private String topicPrefix;
}
