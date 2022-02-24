package com.example.patientservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@SpringBootApplication
@EnableEurekaClient
//@Slf4j
//@ComponentScan({"com"})
//@ConfigurationProperties("patient-service")
//@PropertySource(value = "C:/config/patient-service.properties", ignoreResourceNotFound = true)
@EnableEncryptableProperties
public class PatientServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PatientServiceApplication.class, args);
    }

//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Bean
//    public DataSource getDataSource() {
//        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.postgresql.Driver");
//        log.info(url);
//        dataSourceBuilder.url(url);
//        log.info(username);
//        dataSourceBuilder.username(username);
//        log.info(password);
//        dataSourceBuilder.password(password);
//        return dataSourceBuilder.build();
//    }
}
