package info.pesla.autoconfigure.swagger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author krogulecp
 */
class SwaggerEndpointsAutoconfigureTest {

    private ApplicationContextRunner contextRunner;

    @BeforeEach
    void setUp() {
        contextRunner = new ApplicationContextRunner();
    }

    @Test
    void shouldDefineDocketInContext() {
        contextRunner
                .withConfiguration(AutoConfigurations.of(SwaggerEndpointsAutoconfigure.class))
                .withBean(RequestMappingHandlerMapping.class)
                .run(context -> assertThat(context).hasSingleBean(Docket.class));
    }

    @Test
    void shouldNotDefineDocketWhenSwaggerIsNotEnabled() {
        contextRunner
                .withConfiguration(AutoConfigurations.of(SwaggerEndpointsAutoconfigure.class))
                .withPropertyValues("pesla.swagger.enabled=false")
                .run(context -> assertThat(context).doesNotHaveBean(Docket.class));
    }

    @Test
    void shouldDefineMultipleDocketsInContext() {
        contextRunner
                .withConfiguration(AutoConfigurations.of(SwaggerEndpointsAutoconfigure.class))
                .withBean(RequestMappingHandlerMapping.class)
                .withBean(TestRestController.class)
                .run(context -> assertThat(context).getBeans(Docket.class).containsKeys("testEndpoint", "allRestApiSwaggerEndpoint"));
    }

    @Test
    void shouldDefineMultipleDocketsInContextWithCorrectGroupNamesAndDocumentationType() {
        contextRunner
                .withConfiguration(AutoConfigurations.of(SwaggerEndpointsAutoconfigure.class))
                .withBean(RequestMappingHandlerMapping.class)
                .withBean(TestRestController.class)
                .withPropertyValues("pesla.swagger.all-rest-group-name=all-rest-api-endpoint")
                .run(context -> {
                    assertThat(context).getBean("testEndpoint")
                            .extracting(bean -> (Docket) bean)
                            .extracting(Docket::getGroupName)
                            .isEqualTo("testEndpoint");
                    assertThat(context).getBean("testEndpoint")
                            .extracting(bean -> (Docket) bean)
                            .extracting(Docket::getDocumentationType)
                            .isEqualTo(DocumentationType.SWAGGER_2);
                    assertThat(context).getBean("allRestApiSwaggerEndpoint")
                            .extracting(bean -> (Docket) bean)
                            .extracting(Docket::getGroupName)
                            .isEqualTo("all-rest-api-endpoint");
                });
    }
}
