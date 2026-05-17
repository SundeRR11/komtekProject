package com.example.komtekProject.config;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Component
public class SchemaConfiguration implements BeanPostProcessor {

    @Value("${spring.liquibase.default-schema:public}")
    private String schemaName;

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) {
        if (StringUtils.hasText(schemaName) && bean instanceof DataSource dataSource) {
            try (Connection conn = dataSource.getConnection();
                 Statement statement = conn.createStatement()) {
                log.info("Создание схемы БД '{}' если не существует.", schemaName);
                statement.execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
            } catch (SQLException e) {
                throw new RuntimeException("Не удалось создать схему '" + schemaName + "'", e);
            }
        }
        return bean;
    }
}