package usability.scale.system.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usability.scale.system.calculator.DefaultInstantProvider;
import usability.scale.system.calculator.InstantProvider;
import usability.scale.system.calculator.ScoreRepository;
import usability.scale.system.calculator.average.AverageScoreCalculators;
import usability.scale.system.calculator.dto.Score;

import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class SusService {
    private static final Logger logger = LoggerFactory.getLogger(SusService.class);
    private final ScoreRepository scoreRepository;
    private final InstantProvider instantProvider = new DefaultInstantProvider();
    private final AverageScoreCalculators averageScoreCalculators;

    @Autowired
    SusService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
        averageScoreCalculators = new AverageScoreCalculators(instantProvider, scoreRepository);
    }

    public String getAllTimeAverage() {
        return doubleOrUnderScore(averageScoreCalculators.calculateAllTimeAverage());
    }

    public String getHourlyScore() {
        return doubleOrUnderScore(averageScoreCalculators.calculateHourlyAverage());
    }

    public String getDailyScore() {
        return doubleOrUnderScore(averageScoreCalculators.calculateDailyAverage());
    }

    public String getWeeklyScore() {
        return doubleOrUnderScore(averageScoreCalculators.calculateWeeklyAverage());
    }

    public String getMonthlyScore() {
        return doubleOrUnderScore(averageScoreCalculators.calculateMonthlyAverage());
    }

    public void addScore(double value) {
        Score score = new Score(value, Date.from(instantProvider.now()));
        logger.info("a new score was calculated: {}", score);
        scoreRepository.addScore(score);
        averageScoreCalculators.addScore(score);
    }

    public List<Score> getAllScores() {
        return scoreRepository.getAllScores();
    }

    private String doubleOrUnderScore(OptionalDouble optionalDouble) {
        return optionalDouble.isPresent() ? String.valueOf(optionalDouble.getAsDouble()) : "_";
    }
}
