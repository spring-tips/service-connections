package com.example.autoconfigure;

import com.rethinkdb.RethinkDB;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "rethinkdb")
record RethinkDbProperties(String host, int port) {
}

@AutoConfiguration
@EnableConfigurationProperties(RethinkDbProperties.class)
class RethinkDbAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean({RethinkDbConnectionDetails.class})
    RethinkDbConnectionDetails rethinkDbConnectionDetails(RethinkDbProperties properties) {
        return new RethinkDbPropertiesConnectionDetails(properties);
    }

    static class RethinkDbPropertiesConnectionDetails implements RethinkDbConnectionDetails {

        private final RethinkDbProperties rethinkDbProperties;

        RethinkDbPropertiesConnectionDetails(RethinkDbProperties rethinkDbProperties) {
            this.rethinkDbProperties = rethinkDbProperties;
        }

        @Override
        public String host() {
            return this.rethinkDbProperties.host();
        }

        @Override
        public int port() {
            return this.rethinkDbProperties.port();
        }
    }

    @Bean
    RethinkDB rethinkDB() {
        return RethinkDB.r;
    }

    @Bean
    com.rethinkdb.net.Connection rethinkDbConnection(
            RethinkDB rethinkDB, RethinkDbConnectionDetails connectionDetails) {
        return rethinkDB
                .connection()
                .hostname(connectionDetails.host())
                .port(connectionDetails.port())
                .connect();
    }


}

