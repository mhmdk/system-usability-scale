package usability.scale.system.spring.persistence;

import usability.scale.system.calculator.dto.Score;

public class ScoreMapper {
    public static ScoreEntity scoreToEntity(Score score) {
        return new ScoreEntity(score.getValue(), score.getSubmissionDate());
    }

    public static Score entityToScore(ScoreEntity scoreEntity) {
        return new Score(scoreEntity.getValue(), scoreEntity.getDate());
    }
}
