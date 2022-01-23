package usability.scale.system.spring.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import usability.scale.system.calculator.dto.Score;
import usability.scale.system.calculator.ScoreRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SpringScoreRepositoryAdapter implements ScoreRepository {
    @Autowired
    private SpringScoreRepository delegate;

    @Override
    public List<Score> getAllScores() {
        Iterable<ScoreEntity> scoreEntities = delegate.findAll();
        List<Score> scores = new ArrayList<>();
        for (ScoreEntity entity : scoreEntities) {
            scores.add(ScoreMapper.entityToScore(entity));
        }
        return scores;
    }

    @Override
    public void addScore(Score score) {
        ScoreEntity scoreEntity = ScoreMapper.scoreToEntity(score);
        delegate.save(scoreEntity);
    }
}
