package usability.scale.system.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import usability.scale.system.calculator.AnswerChoice;
import usability.scale.system.calculator.Questions;

import java.util.Arrays;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Answers {
    private AnswerChoice[] answers = new AnswerChoice[Questions.NUMBER_OF_QUESTIONS];

    public Answers() {
        Arrays.fill(answers, AnswerChoice.NEUTRAL);
    }
}
