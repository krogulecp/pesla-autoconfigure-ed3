package info.pesla.autoconfigure.swagger;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author krogulecp
 */
@Api
@RestController
class TestRestController {

    @GetMapping(path = "/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("test get");
    }
}
