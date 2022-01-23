package usability.scale.system.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import usability.scale.system.FakeScoreRepository;
import usability.scale.system.calculator.Questions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ActiveProfiles("test")
// to reset the susService, discarding all scores added by previous test
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class PageContentTests extends SusApplicationTests {

    @Autowired
    SusService susService;
    @Autowired
    FakeScoreRepository fakeScoreRepository;

    @BeforeEach()
    void setupTestCase() {
        fakeScoreRepository.clear();
    }

    @Test
    void main_page_is_loaded() {
        ResponseEntity<String> response = get("/");
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.toString()).contains("Please answer the questions below:");
    }

    @Test
    void index_is_mapped_to_main_page() {
        ResponseEntity<String> response = get("index");
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.toString()).contains("Please answer the questions below:");
    }

    @Test
    void main_page_should_contain_all_questions() {
        ResponseEntity<String> response = get("/");
        String pageText = response.toString();
        assertThat(pageText).contains(Questions.ALL);
    }

    @Test
    void statistics_page_should_contain_all_scores_average() {
        double[] scoreValues = new double[]{27.5, 50, 100};
        String expectedAverage = "59.1667";
        Arrays.stream(scoreValues).forEach(susService::addScore);

        ResponseEntity<String> response = get("statistics/");
        String pageText = response.toString();
        assertThat(pageText).contains(String.format("all time average: %s", expectedAverage));

    }

    @Test
    void statistics_page_should_contain_hourly_daily_weekly_monthly_scores() {
        susService.addScore(100.0);
        ResponseEntity<String> response = get("statistics/");
        String pageText = response.toString();

        assertThat(pageText).contains(String.format("hourly score: %s", 100));
        assertThat(pageText).contains(String.format("daily score: %s", 100));
        assertThat(pageText).contains(String.format("weekly score: %s", 100));
        assertThat(pageText).contains(String.format("monthly score: %s", 100));

    }

    @Test
    void statistics_page_should_contain_all_scores() {
        double[] scoreValues = new double[]{27.5, 50, 100, 17, 66};
        Arrays.stream(scoreValues).forEach(susService::addScore);
        List<String> expected = Arrays.stream(scoreValues)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());

        ResponseEntity<String> response = get("statistics/");
        String pageText = response.toString();
        for (String value : expected) {
            assertThat(pageText).contains(value);
        }
    }
}

