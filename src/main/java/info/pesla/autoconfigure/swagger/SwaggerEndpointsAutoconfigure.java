package info.pesla.autoconfigure.swagger;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author krogulecp
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "pesla.swagger.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@RequiredArgsConstructor
class SwaggerEndpointsAutoconfigure {

    private final SwaggerProperties swaggerProperties;
    private final ConfigurableApplicationContext applicationContext;
    private final ConfigurableBeanFactory beanFactory;
    private final DocketFactory docketFactory = new SwaggerDocketForEndpointFactory();

    @PostConstruct
    void provideSwaggerEndpointDockets() {
        List<Docket> dockets = docketFactory.createDockets(prepareControllerNameCandidates(applicationContext),
                prepareHandlerMethodCandidates(applicationContext),
                prepareApiInfo(swaggerProperties.getUserInterface().getTitle(), swaggerProperties.getUserInterface().getVersion()));
        dockets.forEach(docket -> beanFactory.registerSingleton(docket.getGroupName(), docket));
    }

    @Bean
    Docket allRestApiSwaggerEndpoint() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(prepareApiInfo(swaggerProperties.getUserInterface().getTitle(), swaggerProperties.getUserInterface().getVersion()))
                .groupName(swaggerProperties.getAllRestGroupName())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getAllRestScanPath()))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private Map<RequestMappingInfo, HandlerMethod> prepareHandlerMethodCandidates(ConfigurableApplicationContext applicationContext) {
        return applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
    }

    private List<String> prepareControllerNameCandidates(ConfigurableApplicationContext applicationContext) {
        return List.of(applicationContext.getBeanNamesForAnnotation(Api.class));
    }

    private ApiInfo prepareApiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .version(version)
                .build();
    }
}
