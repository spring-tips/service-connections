package com.example.simple;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;

@SpringBootApplication
public class SimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }

    @Bean
    JdbcClient jdbcClient(DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }

    @Bean
    ApplicationRunner demo(JdbcClient jdbcClient) {
        return args -> jdbcClient
                .sql("select * from customer")
                .query(Customer.class)
                .stream()
                .forEach( customer -> System.out.println( "Customer: "+ customer.toString()));
    }
}

record Customer(Integer id, String name) {
}
