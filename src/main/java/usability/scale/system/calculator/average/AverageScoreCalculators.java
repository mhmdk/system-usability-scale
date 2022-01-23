package usability.scale.system.calculator.average;

import usability.scale.system.calculator.InstantProvider;
import usability.scale.system.calculator.ScoreRepository;
import usability.scale.system.calculator.dto.Score;

import java.util.OptionalDouble;

public class AverageScoreCalculators {

    private final HourlyScoreCalculator hourlyScoreCalculator;
    private final DailyScoreCalculator dailyScoreCalculator;
    private final WeeklyScoreCalculator weeklyScoreCalculator;
    private final MonthlyScoreCalculator monthlyScoreCalculator;
    private final AllTimeScoreCalculator allTimeScoreCalculator;
    private final AverageScoreCalculator[] timedAverageCalculators;

    public AverageScoreCalculators(InstantProvider instantProvider, ScoreRepository scoreRepository) {
        hourlyScoreCalculator = new HourlyScoreCalculator(instantProvider);
        dailyScoreCalculator = new DailyScoreCalculator(instantProvider);
        weeklyScoreCalculator = new WeeklyScoreCalculator(instantProvider);
        monthlyScoreCalculator = new MonthlyScoreCalculator(instantProvider);
        timedAverageCalculators = new AverageScoreCalculator[]{
                hourlyScoreCalculator, dailyScoreCalculator, weeklyScoreCalculator, monthlyScoreCalculator
        };

        allTimeScoreCalculator = new AllTimeScoreCalculator();

        for (Score score : scoreRepository.getAllScores()) {
            addScore(score);
        }
    }

    public void addScore(Score score) {
        allTimeScoreCalculator.addScore(score);
        for (AverageScoreCalculator averageCalculator : timedAverageCalculators) {
            averageCalculator.addScore(score);
        }
    }

    public OptionalDouble calculateAllTimeAverage() {
        return allTimeScoreCalculator.calculateAverage();
    }

    public OptionalDouble calculateHourlyAverage() {
        return hourlyScoreCalculator.calculateAverage();
    }

    public OptionalDouble calculateDailyAverage() {
        return dailyScoreCalculator.calculateAverage();
    }

    public OptionalDouble calculateWeeklyAverage() {
        return weeklyScoreCalculator.calculateAverage();
    }

    public OptionalDouble calculateMonthlyAverage() {
        return monthlyScoreCalculator.calculateAverage();
    }

}
