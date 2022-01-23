package usability.scale.system.spring;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SusApplicationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private String url;

    @BeforeEach()
    private void setUpTestCase() {
        this.url = "http://localhost:" + port + "/";
    }

    protected ResponseEntity<String> sendRequest(HttpMethod httpMethod, String path, String body, HttpHeaders headers) {
        RequestEntity<String> requestEntity = new RequestEntity<>(body, headers, httpMethod, URI.create(url + path));
        return restTemplate.exchange(url + path, httpMethod, requestEntity, String.class);
    }

    protected ResponseEntity<String> sendRequest(HttpMethod httpMethod, String path, String body, String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookie);
        return sendRequest(httpMethod, path, body, headers);
    }

    protected ResponseEntity<String> get(String path, String cookie) {
        return sendRequest(HttpMethod.GET, path, "", cookie);
    }

    protected ResponseEntity<String> get(String path) {
        return get(path, "");
    }

    protected ResponseEntity<String> post(String path, String body, String cookie) {
        return sendRequest(HttpMethod.POST, path, body, cookie);
    }

    protected ResponseEntity<String> post(String path, String cookie) {
        return post(path, "", cookie);
    }

    protected ResponseEntity<String> post(String path) {
        return post(path, "");
    }
}