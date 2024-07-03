package bootiful.services.autoconfigure;

import com.rethinkdb.RethinkDB;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(RethinkDbProperties.class)
class RethinkDbAutoConfiguration {

    @Bean
    RethinkDB rethinkDB() {
        return RethinkDB.r;
    }

    @Bean
    com.rethinkdb.net.Connection rethinkDbConnection(
            RethinkDB rethinkDB, RethinkDbConnectionDetails properties) {
        return rethinkDB
                .connection()
                .hostname(properties.host())
                .port(properties.port())
                .connect();
    }


}

