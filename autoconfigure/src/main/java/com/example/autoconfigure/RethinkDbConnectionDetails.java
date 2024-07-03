package com.example.autoconfigure;


import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;

public interface RethinkDbConnectionDetails extends ConnectionDetails {
    String host();
    int port();
}