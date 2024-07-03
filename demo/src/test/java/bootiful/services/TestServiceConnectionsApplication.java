package bootiful.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

class TestServiceConnectionsApplication {

    public static void main(String[] args) {
        System.setProperty("spring.docker.compose.enabled", "false");
        SpringApplication
                .from(ServiceConnectionsApplication::main)
                .with(TestcontainersConfiguration.class).run(args);
    }

}


@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection ( name = "rethinkdb")
    Container<?> rethinkdbContainer() {
        return new GenericContainer<>(DockerImageName.parse("rethinkdb"))
                .withExposedPorts(28015);
    }

}
