package usability.scale.system.calculator.average;

import usability.scale.system.calculator.InstantProvider;

import java.util.Calendar;
import java.util.Date;

class MonthlyScoreCalculator extends AverageScoreCalculator {
    MonthlyScoreCalculator(InstantProvider instantProvider) {
        super(instantProvider);
    }

    @Override
    boolean isWithinTimeRange(Date date) {
        Date now = Date.from(now());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -1);
        return date.after(calendar.getTime());
    }
}
