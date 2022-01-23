package usability.scale.system.calculator.average;

import java.math.BigDecimal;
import java.math.RoundingMode;

class CumulativeAverageUtils {

    static double adjustAverageAfterRemoval(double cumulativeAverage, long numberOfItems, double removedValue) {
        BigDecimal oldSum = BigDecimal.valueOf(cumulativeAverage).multiply(BigDecimal.valueOf(numberOfItems));
        BigDecimal newSum = oldSum.subtract(BigDecimal.valueOf(removedValue));
        BigDecimal newAverage = newSum.divide(BigDecimal.valueOf(numberOfItems - 1), 4, RoundingMode.HALF_UP);
        return newAverage.doubleValue();
    }

    static double  adjustAverageAfterAddition(double cumulativeAverage, long numberOfItems, double newValue) {
        BigDecimal oldSum = BigDecimal.valueOf(cumulativeAverage).multiply(BigDecimal.valueOf(numberOfItems));
        BigDecimal newSum = oldSum.add(BigDecimal.valueOf(newValue));
        BigDecimal newAverage = newSum.divide(BigDecimal.valueOf(numberOfItems +1), 4, RoundingMode.HALF_UP);
        return newAverage.doubleValue();
    }
}
