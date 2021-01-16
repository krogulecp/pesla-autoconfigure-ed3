package info.pesla.autoconfigure.swagger;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.Map;

/**
 * @author krogulecp
 */
interface DocketFactory {
    List<Docket> createDockets(List<String> controllerNames, Map<RequestMappingInfo, HandlerMethod> handlerMethods, ApiInfo apiInfo);
}
