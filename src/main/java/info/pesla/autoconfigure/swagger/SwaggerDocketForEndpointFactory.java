package info.pesla.autoconfigure.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author krogulecp
 */
class SwaggerDocketForEndpointFactory implements DocketFactory {

    @Override
    public List<Docket> createDockets(List<String> controllerNames, Map<RequestMappingInfo, HandlerMethod> handlerMethods, ApiInfo apiInfo) {
        return controllerNames.stream()
                .map(controllerName -> createDocketsForSingleController(handlerMethods, controllerName, apiInfo))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Docket> createDocketsForSingleController(Map<RequestMappingInfo, HandlerMethod> handlerMethods, String controllerName, ApiInfo apiInfo) {
        return handlerMethods.entrySet().stream()
                .filter(handlerMappingEntry -> handlerMappingEntry.getValue().getBean().equals(controllerName))
                .map(handlerMappingEntry -> createSingleDocketForControllerMethod(handlerMappingEntry, apiInfo))
                .collect(Collectors.toList());
    }

    private Docket createSingleDocketForControllerMethod(Map.Entry<RequestMappingInfo, HandlerMethod> handlerMappingEntry, ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName(handlerMappingEntry.getValue().getMethod().getName())
                .select()
                .paths(constructPaths(handlerMappingEntry.getKey().getPatternsCondition().getPatterns()))
                .build()
                .useDefaultResponseMessages(false);
    }

    private Predicate<String> constructPaths(Set<String> paths) {
        Set<Predicate<String>> predicates = Sets.newHashSet();
        paths.forEach(path -> predicates.add(p -> Objects.equals(p, path)));
        return Predicates.or(predicates);
    }
}
