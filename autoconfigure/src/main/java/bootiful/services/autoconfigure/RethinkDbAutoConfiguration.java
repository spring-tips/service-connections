package bootiful.services.autoconfigure;

import com.rethinkdb.RethinkDB;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(RethinkDbProperties.class)
class RethinkDbAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean({RethinkDbConnectionDetails.class})
    RethinkDbConnectionDetails rethinkDbConnectionDetails(RethinkDbProperties properties) {
        return new RethinkDbPropertiesConnectionDetails(properties);
    }

    @Bean
    RethinkDB rethinkDB() {
        return RethinkDB.r;
    }

    @Bean
    com.rethinkdb.net.Connection rethinkDbConnection(
            RethinkDB rethinkDB, RethinkDbConnectionDetails connectionDetails) {
        System.out.println(connectionDetails);
        return rethinkDB
                .connection()
                .hostname(connectionDetails.host())
                .port(connectionDetails.port())
                .connect();
    }


}

