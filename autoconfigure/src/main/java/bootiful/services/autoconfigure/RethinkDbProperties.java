package bootiful.services.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rethinkdb")
record RethinkDbProperties(String host, int port) {
}
