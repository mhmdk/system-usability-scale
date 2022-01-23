package usability.scale.system.spring.persistence;

import org.springframework.data.repository.CrudRepository;

interface SpringScoreRepository extends CrudRepository<ScoreEntity, Long> {
}

