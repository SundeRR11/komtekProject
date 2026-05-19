package com.example.komtekProject.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sftp")
public class SftpProperties {
    private String host;
    private int port = 22;
    private String username;
    private String password;
    private String remoteDir = "/upload";
    private int connectTimeoutMs = 10000;
}