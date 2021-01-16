package info.pesla.autoconfigure.header;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(PeslaHeaderProperties.class)
public class HeaderAutoconfigure {

    private final PeslaHeaderProperties headerProperties;

    public HeaderAutoconfigure(PeslaHeaderProperties headerProperties) {
        this.headerProperties = headerProperties;
    }

    @Bean
    PeslaHeaderFilter peslaHeaderFilter() {
        return new PeslaHeaderFilter(headerProperties.getValue());
    }
}
