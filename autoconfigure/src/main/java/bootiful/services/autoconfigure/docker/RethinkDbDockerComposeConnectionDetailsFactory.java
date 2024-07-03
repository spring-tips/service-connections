package bootiful.services.autoconfigure.docker;

import bootiful.services.autoconfigure.RethinkDbConnectionDetails;
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
    }

    @Override
    protected RethinkDbConnectionDetails getDockerComposeConnectionDetails(DockerComposeConnectionSource source) {
        return new RethinkDbDockerComposeConnectionDetailsFactory.RethinkDbDockerComposeConnectionDetails(source.getRunningService());
    }
}
