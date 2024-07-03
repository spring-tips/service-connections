package com.example.autoconfigure.docker;

import com.example.autoconfigure.RethinkDbConnectionDetails;
import org.springframework.boot.docker.compose.core.RunningService;
import org.springframework.boot.docker.compose.service.connection.DockerComposeConnectionDetailsFactory;
import org.springframework.boot.docker.compose.service.connection.DockerComposeConnectionSource;

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
        System.out.println("initializng " + getClass().getName() + '.');
    }

    @Override
    protected RethinkDbConnectionDetails getDockerComposeConnectionDetails(DockerComposeConnectionSource source) {
        return new RethinkDbDockerComposeConnectionDetails(source.getRunningService());
    }
}
