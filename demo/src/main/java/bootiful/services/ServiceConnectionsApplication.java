package bootiful.services;


import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
public class ServiceConnectionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConnectionsApplication.class, args);
    }

    @Bean
    ApplicationRunner rethinkDbClient(RethinkDB rethinkDB, Connection connection) {
        return args -> {
            var db = "test";
            var table = "tv-shows";

            try {
                rethinkDB.dbDrop(db).run(connection);
            }//
            catch (Exception e) {
                // don't care
            }

            rethinkDB.dbCreate(db).run(connection);
            rethinkDB.db(db).tableCreate(table).run(connection);
            rethinkDB.table(table).insert(rethinkDB.hashMap("name", "Star Trek TNG")).run(connection);
            var scifiResult = rethinkDB.table(table).run(connection);
            scifiResult.stream()
                    .map( o -> (Map<?,?>)o )
                    .map( m -> m.get("id") +"::"+ m.get("name"))
                    .forEach(System.out::println);
        };
    }
}
