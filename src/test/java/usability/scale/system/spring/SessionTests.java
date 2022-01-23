package usability.scale.system.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static usability.scale.system.calculator.Questions.NUMBER_OF_QUESTIONS;

public class SessionTests extends SusApplicationTests {

    private String getCookie(ResponseEntity<String> response) {
        List<String> cookieHeader = response.getHeaders().get(SET_COOKIE);
        Assertions.assertNotNull(cookieHeader);
        Assertions.assertEquals(cookieHeader.size(), 1);
        assertThat(cookieHeader.get(0)).contains("SESSIONID");
        return cookieHeader.get(0);
    }

    private String extractSessionId(String cookie) {
        String left = cookie.split(";")[0];
        return left.split("=")[1];
    }

    @Test
    void main_page_should_create_new_session_when_no_sessionid_is_sent() {
        ResponseEntity<String> firstResponse = get("/");
        String cookie = getCookie(firstResponse);
        String firstSessionId = extractSessionId(cookie);

        ResponseEntity<String> secondResponse = get("/");
        String cookie2 = getCookie(secondResponse);
        String secondSessionId = extractSessionId(cookie2);

        Assertions.assertNotEquals(firstSessionId, secondSessionId);
    }

    @Test
    void main_page_should_create_new_session_when_old_sessionid_is_sent() {
        ResponseEntity<String> firstResponse = get("/");
        String cookie = getCookie(firstResponse);
        String firstSessionId = extractSessionId(cookie);

        ResponseEntity<String> secondResponse = get("/", cookie);
        String cookie2 = getCookie(secondResponse);
        String secondSessionId = extractSessionId(cookie2);

        Assertions.assertNotEquals(firstSessionId, secondSessionId);
    }

    @Test
    void submit_page_should_return_error_when_invalid_sessionid_is_sent() {
        ResponseEntity<String> response = post("submit/", "invalidCookie");
        List<String> cookieHeader = response.getHeaders().get(SET_COOKIE);
        Assertions.assertNull(cookieHeader);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("invalid session");
    }

    @Test
    void a_session_can_submit_only_once() {

        ResponseEntity<String> mainPageResponse = get("/");
        String cookie = getCookie(mainPageResponse);

        StringBuilder requestBodyBuilder = new StringBuilder();
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            requestBodyBuilder.append("answers[").append(i).append("]=NEUTRAL&");
        }
        String requestBody = requestBodyBuilder.toString();

        ResponseEntity<String> firstSubmissionResponse = post("submit/", requestBody, cookie);
        Assertions.assertEquals(firstSubmissionResponse.getStatusCode(), HttpStatus.OK);

        ResponseEntity<String> secondSubmissionResponse = post("submit/", requestBody, cookie);
        Assertions.assertEquals(secondSubmissionResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertThat(secondSubmissionResponse.getBody()).contains("invalid session");
    }
}
