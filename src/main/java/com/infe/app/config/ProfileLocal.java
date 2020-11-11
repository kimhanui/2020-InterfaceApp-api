package com.infe.app.config;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 외부(h2-console로 접속이 아닌)에서 접속하기 위해 h2 라이브러리에서
 * 제공하는 TcpServer를 사용한다.
 */
@Configuration
@Profile("local")
@PropertySource({"classpath:application-local.properties"})
public class ProfileLocal {
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() throws SQLException {
        Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "8089").start();
        return new HikariDataSource();
    }

}