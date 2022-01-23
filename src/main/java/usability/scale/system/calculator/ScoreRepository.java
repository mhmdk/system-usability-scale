package usability.scale.system.calculator;

import usability.scale.system.calculator.dto.Score;

import java.util.List;

public interface ScoreRepository{

    List<Score> getAllScores();

    void addScore(Score score);
}
