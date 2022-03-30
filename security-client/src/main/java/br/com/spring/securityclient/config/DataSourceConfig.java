package br.com.spring.securityclient.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() throws IOException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/ms-user");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("1qa2ws3ed");

       return new HikariDataSource(hikariConfig);
    }

    private static String extractSecretValue(String secret) throws IOException{
        Path secretPath = Path.of(secret);
        return Files.readString(secretPath);
    }
}
