package bootiful.services.autoconfigure.testcontainers;

import bootiful.services.autoconfigure.RethinkDbConnectionDetails;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionDetailsFactory;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionSource;
import org.testcontainers.containers.Container;

///
class RethinkDbContainerConnectionDetailsFactory
        extends ContainerConnectionDetailsFactory<Container<?>, RethinkDbConnectionDetails> {

    public RethinkDbContainerConnectionDetailsFactory() {
        super("rethinkdb", "com.rethinkdb.RethinkDB");
    }

    @Override
    protected RethinkDbConnectionDetails getContainerConnectionDetails(ContainerConnectionSource<Container<?>> source) {
        return new RethinkDbContainerConnectionDetailsFactory.RethinkDbContainerConnectionDetails(source);
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
