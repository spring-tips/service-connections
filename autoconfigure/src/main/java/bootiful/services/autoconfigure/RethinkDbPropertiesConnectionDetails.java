package bootiful.services.autoconfigure;

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
