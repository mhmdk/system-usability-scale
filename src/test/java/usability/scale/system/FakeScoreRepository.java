package usability.scale.system;

import usability.scale.system.calculator.ScoreRepository;
import usability.scale.system.calculator.dto.Score;

import java.util.ArrayList;
import java.util.List;

public class FakeScoreRepository implements ScoreRepository {
    private List<Score> scores = new ArrayList<>();

    @Override
    public List<Score> getAllScores() {
        return scores;
    }

    @Override
    public void addScore(Score score) {
        scores.add(score);
    }

    public void clear() {
        scores.clear();
    }
}
