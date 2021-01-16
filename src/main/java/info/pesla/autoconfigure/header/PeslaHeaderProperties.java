package info.pesla.autoconfigure.header;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pesla.header")
public class PeslaHeaderProperties {
    private String value = "PESLA";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
