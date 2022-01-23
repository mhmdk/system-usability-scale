package usability.scale.system.calculator.average;

import usability.scale.system.calculator.InstantProvider;
import usability.scale.system.calculator.dto.Score;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.OptionalDouble;
import java.util.PriorityQueue;

public abstract class AverageScoreCalculator {
    private final InstantProvider instantProvider;
    private final PriorityQueue<Score> scoresSortedByDate = new PriorityQueue<>(new ScoresByDateComparator());
    private double cumulativeAverage;

    AverageScoreCalculator(InstantProvider instantProvider) {
        this.instantProvider = instantProvider;
    }

    abstract boolean isWithinTimeRange(Date date);

    Instant now() {
        return instantProvider.now();
    }

    OptionalDouble calculateAverage() {
        removeOldScores();
        if (scoresSortedByDate.size() == 0) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(cumulativeAverage);
    }

    synchronized void addScore(Score score) {
        if (isWithinTimeRange(score.getSubmissionDate())) {
            double value = score.getValue();
            if (scoresSortedByDate.size() == 0) {
                cumulativeAverage = value;
            } else {
                cumulativeAverage = CumulativeAverageUtils.adjustAverageAfterAddition(cumulativeAverage, scoresSortedByDate.size(), value);
            }
            scoresSortedByDate.add(score);
        }
    }

    private synchronized void removeOldScores() {
        while (!scoresSortedByDate.isEmpty()) {
            Score oldestScore = scoresSortedByDate.peek();
            if (isWithinTimeRange(oldestScore.getSubmissionDate())) {
                break;
            } else {
                if (scoresSortedByDate.size() > 1) {
                    double value = scoresSortedByDate.peek().getValue();
                    cumulativeAverage = CumulativeAverageUtils.adjustAverageAfterRemoval(cumulativeAverage, scoresSortedByDate.size(), value);
                }
                scoresSortedByDate.remove();
            }
        }
    }

    private static class ScoresByDateComparator implements Comparator<Score> {
        @Override
        public int compare(Score score1, Score score2) {
            Date score1Date = score1.getSubmissionDate();
            Date score2Date = score2.getSubmissionDate();
            if (score1Date.before(score2Date)) {
                return -1;
            }
            if (score1Date.after(score2Date)) {
                return 1;
            }
            return 0;
        }
    }
}



