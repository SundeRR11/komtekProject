package com.example.komtekProject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "notification.retry")
public class NotificationRetryProperties {
    private int batchSize = 100;
    private int minIntervalMinutes = 5;
    private int maxAttempts = 10;
    private long schedulerDelayMs = 10000;
}