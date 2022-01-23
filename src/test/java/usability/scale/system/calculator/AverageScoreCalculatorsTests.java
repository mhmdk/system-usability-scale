package usability.scale.system.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usability.scale.system.FakeScoreRepository;
import usability.scale.system.calculator.average.AverageScoreCalculators;
import usability.scale.system.calculator.dto.Score;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;

import java.util.List;
import java.util.OptionalDouble;

public class AverageScoreCalculatorsTests {

    FakeInstantProvider fakeInstantProvider;
    AverageScoreCalculators averageScoreCalculators;

    @BeforeEach
    void setupTestCase() {
        fakeInstantProvider = new FakeInstantProvider();
        averageScoreCalculators = new AverageScoreCalculators(fakeInstantProvider, new FakeScoreRepository());
    }

    @Test
    void average_of_empty_list_should_return_empty_optional() {
        OptionalDouble average = averageScoreCalculators.calculateAllTimeAverage();
        Assertions.assertTrue(average.isEmpty());
    }

    @Test
    void average_of_singleton_list() {
        averageScoreCalculators.addScore(new Score(10.5, Date.from(fakeInstantProvider.now())));
        OptionalDouble average = averageScoreCalculators.calculateAllTimeAverage();
        Assertions.assertFalse(average.isEmpty());
        Assertions.assertEquals(10.5, average.getAsDouble(), 0);
    }

    @Test
    void all_time_average_of_sample_list() {
        double[] scoreValues = new double[]{1, 2, 3, 4.5, 6, 0};
        double expectedAverage = (1 + 2 + 3 + 4.5 + 6 + 0) / 6;
        Arrays.stream(scoreValues).
                mapToObj(value -> new Score(value, Date.from(fakeInstantProvider.now()))).
                forEach(averageScoreCalculators::addScore);
        OptionalDouble average = averageScoreCalculators.calculateAllTimeAverage();

        Assertions.assertFalse(average.isEmpty());
        Assertions.assertEquals(expectedAverage, average.getAsDouble(), 0);
    }

    @Test
    void hourly_score_should_be_empty_when_no_scores_in_last_hour() {
        fakeInstantProvider.setDate("24/02/2022 09:30");
        averageScoreCalculators.addScore(new Score(10, date("24/02/2022 07:30")));
        averageScoreCalculators.addScore(new Score(10, date("24/02/2022 08:29")));
        Assertions.assertTrue(averageScoreCalculators.calculateHourlyAverage().isEmpty());
    }

    @Test
    void hourly_score_should_discard_scores_older_than_an_hour() {
        fakeInstantProvider.setDate("24/02/2022 09:30");
        averageScoreCalculators.addScore(new Score(10, date("24/02/2022 09:00")));
        averageScoreCalculators.addScore(new Score(20, date("24/02/2022 09:29")));
        Assertions.assertEquals(averageScoreCalculators.calculateHourlyAverage().getAsDouble(), 15, 0);
        fakeInstantProvider.setDate("24/02/2022 10:01");
        Assertions.assertEquals(averageScoreCalculators.calculateHourlyAverage().getAsDouble(), 20, 0);
        fakeInstantProvider.setDate("24/02/2022 10:30");
        Assertions.assertTrue(averageScoreCalculators.calculateHourlyAverage().isEmpty());
    }

    @Test
    void average_of_bound_duration() {
        fakeInstantProvider.setDate("24/02/2022 09:30");

        List<Score> scores = List.of(
                //older than one month
                new Score(10, date("24/06/2020 02:30")),
                new Score(15, date("25/01/2021 01:30")),
                new Score(20, date("23/12/2021 00:30")),
                //last month
                new Score(25, date("25/01/2022 04:30")),
                new Score(30, date("31/01/2022 12:30")),
                new Score(35, date("01/02/2022 23:30")),
                //last week
                new Score(40, date("18/02/2022 10:30")),
                new Score(45, date("20/02/2022 00:30")),
                //last day
                new Score(50, date("23/02/2022 09:31")),
                new Score(55, date("23/02/2022 23:59")),
                new Score(60, date("24/02/2022 08:01")),
                // last hour
                new Score(65, date("24/02/2022 09:29")),
                //too old scores again
                new Score(70, date("23/02/2010 09:31")),
                new Score(75, date("23/02/2000 23:59")),
                new Score(80, date("24/02/2010 08:01"))
        );

        double expectedMonthlyAverage = (25.0 + 30 + 35 + 40 + 45 + 50 + 55 + 60 + 65) / 9;
        double expectedWeeklyAverage = (40.0 + 45 + 50 + 55 + 60 + 65) / 6;
        double expectedDailyAverage = (50.0 + 55 + 60 + 65) / 4;
        double expectedHourlyAverage = 65.0;

        scores.forEach(averageScoreCalculators::addScore);

        Assertions.assertEquals(expectedHourlyAverage,
                averageScoreCalculators.calculateHourlyAverage().getAsDouble());
        Assertions.assertEquals(expectedDailyAverage,
                averageScoreCalculators.calculateDailyAverage().getAsDouble());
        Assertions.assertEquals(expectedWeeklyAverage,
                averageScoreCalculators.calculateWeeklyAverage().getAsDouble());
        Assertions.assertEquals(expectedMonthlyAverage,
                averageScoreCalculators.calculateMonthlyAverage().getAsDouble());
    }

    private static Date date(String dateAsText) {
        try {
            return new SimpleDateFormat("z dd/MM/yyyy HH:mm").parse("GMT+00:00 " + dateAsText);
        } catch (ParseException e) {
            return Date.from(Instant.now());
        }
    }

    static class FakeInstantProvider implements InstantProvider {
        FakeInstantProvider(String dateText) {
            setDate(dateText);
        }

        FakeInstantProvider() {
            this("24/02/2022 09:30");
        }

        public Instant now() {
            return instant;
        }

        public void setDate(String dateText) {
            instant = date(dateText).toInstant();
        }

        private Instant instant;
    }
}


