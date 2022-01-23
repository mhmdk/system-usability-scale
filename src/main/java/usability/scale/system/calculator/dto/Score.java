package usability.scale.system.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@AllArgsConstructor
public class Score {
    private final double value;
    private final Date submissionDate;
}
