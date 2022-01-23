package usability.scale.system.calculator.average;

import usability.scale.system.calculator.InstantProvider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

class WeeklyScoreCalculator extends AverageScoreCalculator {
    WeeklyScoreCalculator(InstantProvider instantProvider) {
        super(instantProvider);
    }

    @Override
    boolean isWithinTimeRange(Date date) {
        Instant lastWeek = now().minus(7, ChronoUnit.DAYS);
        return date.after(Date.from(lastWeek));
    }
}
