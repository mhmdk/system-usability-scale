package usability.scale.system.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Score {
    private final double value;
    private final Date submissionDate;
}
