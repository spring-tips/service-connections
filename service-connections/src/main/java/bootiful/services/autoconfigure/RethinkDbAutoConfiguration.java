package bootiful.services.autoconfigure;

import com.rethinkdb.RethinkDB;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.docker.compose.core.RunningService;
import org.springframework.boot.docker.compose.service.connection.DockerComposeConnectionDetailsFactory;
import org.springframework.boot.docker.compose.service.connection.DockerComposeConnectionSource;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionDetailsFactory;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionSource;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.Container;

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

///
class RethinkDbContainerConnectionDetailsFactory
        extends ContainerConnectionDetailsFactory<Container<?>, RethinkDbConnectionDetails> {

    public RethinkDbContainerConnectionDetailsFactory() {
        super("rethinkdb", "com.rethinkdb.RethinkDB");
    }

    @Override
    protected RethinkDbConnectionDetails getContainerConnectionDetails(ContainerConnectionSource<Container<?>> source) {
        return new RethinkDbContainerConnectionDetails(source);
    }

    static class RethinkDbContainerConnectionDetails
            extends ContainerConnectionDetails<Container<?>>
            implements RethinkDbConnectionDetails {

        RethinkDbContainerConnectionDetails(ContainerConnectionSource<Container<?>> source) {
            super(source);
        }

        @Override
        public String host() {
            return getContainer().getHost();
        }

        @Override
        public int port() {
            return getContainer().getMappedPort(28015);
        }
    }
}


///

interface RethinkDbConnectionDetails extends ConnectionDetails {
    String host();
    int port();
}

class RethinkDbPropertiesConnectionDetails implements RethinkDbConnectionDetails {

    private final RethinkDbProperties properties;

    RethinkDbPropertiesConnectionDetails(RethinkDbProperties properties) {
        this.properties = properties;
    }

    @Override
    public String host() {
        return this.properties.host();
    }

    @Override
    public int port() {
        return this.properties.port();
    }
}

@ConfigurationProperties(prefix = "rethinkdb")
record RethinkDbProperties(String host, int port) {
}


class RethinkDbDockerComposeConnectionDetailsFactory
        extends DockerComposeConnectionDetailsFactory<RethinkDbConnectionDetails> {


    static class RethinkDbDockerComposeConnectionDetails
            extends DockerComposeConnectionDetails implements RethinkDbConnectionDetails {

        private final RunningService runningService;

        RethinkDbDockerComposeConnectionDetails(RunningService runningService) {
            super(runningService);
            this.runningService = runningService;
        }

        @Override
        public String host() {
            return this.runningService.host();
        }

        @Override
        public int port() {
            return this.runningService.ports().get(28015);
        }
    }

    RethinkDbDockerComposeConnectionDetailsFactory() {
        super("rethinkdb", "com.rethinkdb.RethinkDB");
    }

    @Override
    protected RethinkDbConnectionDetails getDockerComposeConnectionDetails(DockerComposeConnectionSource source) {
        return new RethinkDbDockerComposeConnectionDetails(source.getRunningService());
    }
}
