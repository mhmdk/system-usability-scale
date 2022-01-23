package usability.scale.system.calculator.average;

import usability.scale.system.calculator.InstantProvider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

class HourlyScoreCalculator extends AverageScoreCalculator {

    HourlyScoreCalculator(InstantProvider instantProvider) {
        super(instantProvider);
    }

    @Override
    boolean isWithinTimeRange(Date date) {
        return isWithinOneHour(date);
    }

    private boolean isWithinOneHour(Date date) {
        Instant oneHourBack = now().minus(1, ChronoUnit.HOURS);
        return date.after(Date.from(oneHourBack));
    }
}
