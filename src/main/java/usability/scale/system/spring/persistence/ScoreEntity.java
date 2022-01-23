package usability.scale.system.spring.persistence;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
class ScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private double value;
    @Getter
    private Date date;

    public ScoreEntity(double value, Date date) {
        this.value = value;
        this.date = date;
    }

    // for jpa
    protected ScoreEntity() {
    }

}
