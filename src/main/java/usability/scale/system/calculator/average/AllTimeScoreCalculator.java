package usability.scale.system.calculator.average;

import usability.scale.system.calculator.dto.Score;

import java.util.OptionalDouble;

class AllTimeScoreCalculator {

    private long numberOfItems = 0;
    private double cumulativeAverage;

    OptionalDouble calculateAverage() {
        if (numberOfItems == 0) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(cumulativeAverage);
    }

    synchronized void addScore(Score score) {
        double value = score.getValue();
        if (numberOfItems == 0) {
            cumulativeAverage = value;
        } else {
            cumulativeAverage = CumulativeAverageUtils.adjustAverageAfterAddition(cumulativeAverage, numberOfItems, value);
        }
        numberOfItems++;
    }

}
