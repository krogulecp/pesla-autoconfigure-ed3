package info.pesla.autoconfigure.header;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "pesla.header.value=PESLA SA")
class HeaderAutoconfigureCustomHeaderTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturnPeslaHeader() {

        //when
        ResponseEntity<String> response = testRestTemplate.getForEntity("/test", String.class);

        //then
        List<String> values = response.getHeaders().get("best-company");
        String headerValue = values.get(0);
        assertNotNull(headerValue);
        assertEquals("PESLA SA", headerValue);
    }
}
