package com.example.jdbcwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
//@EnableScheduling
public class JdbcWebServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcWebServiceApplication.class, args);
    }

}
