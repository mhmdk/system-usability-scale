package usability.scale.system.calculator.average;

import usability.scale.system.calculator.InstantProvider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

class DailyScoreCalculator extends AverageScoreCalculator{
    DailyScoreCalculator(InstantProvider instantProvider) {
        super(instantProvider);
    }

    @Override
    boolean isWithinTimeRange(Date date) {
        Instant yesterday = now().minus(1, ChronoUnit.DAYS);
        return date.after(Date.from(yesterday));
    }
}
