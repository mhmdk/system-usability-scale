package usability.scale.system.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RequestMethodTests extends SusApplicationTests {
    @Test
    void post_request_to_main_page_should_return_method_not_allowed() {
        ResponseEntity<String> response = post("/");
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    void get_request_to_submit_page_should_return_method_not_allowed() {
        ResponseEntity<String> response = get("submit");
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
