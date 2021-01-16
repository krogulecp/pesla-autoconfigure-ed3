package info.pesla.autoconfigure.header;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class HeaderAutoconfigureContextTest {
    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HeaderAutoconfigure.class));

    @Test
    void shouldFindPeslaHeaderFilterBeanInContext() {
        contextRunner.run(context -> assertThat(context).hasSingleBean(PeslaHeaderFilter.class));
    }
}
