package info.pesla.autoconfigure.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author krogulecp
 */
@Data
@ConfigurationProperties(prefix = "pesla.swagger")
class SwaggerProperties {

    private boolean enabled = true;
    private String allRestGroupName;

    /**
     * Where to search for controllers
     */
    private String allRestScanPath = "info";
    private UserInterface userInterface = new UserInterface();

    @Data
    static class UserInterface {

        private String title;
        private String version;
    }
}
