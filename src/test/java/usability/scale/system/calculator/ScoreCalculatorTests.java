package usability.scale.system.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import usability.scale.system.calculator.AnswerChoice;

import static usability.scale.system.calculator.AnswerChoice.*;

import usability.scale.system.calculator.dto.Answers;
import usability.scale.system.calculator.ScoreCalculator;

import java.util.Arrays;

import static usability.scale.system.calculator.Questions.NUMBER_OF_QUESTIONS;

public class ScoreCalculatorTests {

    private final ScoreCalculator scoreCalculator = new ScoreCalculator();
    private AnswerChoice[] answerChoices = new AnswerChoice[NUMBER_OF_QUESTIONS];

    private double calculate(AnswerChoice[] answers) {
        return scoreCalculator.calculateScore(new Answers(answers));
    }

    @Test
    void score_of_all_neutral_answers_should_be_50() {
        Arrays.fill(answerChoices, NEUTRAL);
        Assertions.assertEquals(calculate(answerChoices), 50, 0);
    }

    @Test
    void score_of_all_strongly_disagree_answers_should_be_50() {
        Arrays.fill(answerChoices, STRONGLY_DISAGREE);
        Assertions.assertEquals(calculate(answerChoices), 50, 0);
    }

    @Test
    void score_of_all_strongly_agree_answers_should_be_50() {
        Arrays.fill(answerChoices, AGREE);
        Assertions.assertEquals(calculate(answerChoices), 50, 0);
    }

    @Test
    void score_of_all_odd_answers_strongly_agree_and_all_even_answers_strongly_disagree_should_be_100() {
        answerChoices = new AnswerChoice[]{
                STRONGLY_AGREE, STRONGLY_DISAGREE,
                STRONGLY_AGREE, STRONGLY_DISAGREE,
                STRONGLY_AGREE, STRONGLY_DISAGREE,
                STRONGLY_AGREE, STRONGLY_DISAGREE,
                STRONGLY_AGREE, STRONGLY_DISAGREE
        };
        Assertions.assertEquals(calculate(answerChoices), 100, 0);
    }

    @Test
    void score_of_all_odd_answers_strongly_disagree_and_all_even_answers_strongly_agree_should_be_0() {
        answerChoices = new AnswerChoice[]{
                STRONGLY_DISAGREE, STRONGLY_AGREE,
                STRONGLY_DISAGREE, STRONGLY_AGREE,
                STRONGLY_DISAGREE, STRONGLY_AGREE,
                STRONGLY_DISAGREE, STRONGLY_AGREE,
                STRONGLY_DISAGREE, STRONGLY_AGREE
        };
        Assertions.assertEquals(calculate(answerChoices), 0, 0);
    }

    @Test
    void score_of_all_neutral_but_one_positive_agree_answer_should_be_52_and_a_half() {
        Arrays.fill(answerChoices, NEUTRAL);
        answerChoices[0] = AGREE;
        Assertions.assertEquals(calculate(answerChoices), 52.5, 0);
    }

    @Test
    void score_of_all_neutral_but_one_negative_agree_answer_should_be_47_and_a_half() {
        Arrays.fill(answerChoices, NEUTRAL);
        answerChoices[1] = AGREE;
        Assertions.assertEquals(calculate(answerChoices), 47.5, 0);
    }

    @Test
    void score_sample_testcase() {
        answerChoices = new AnswerChoice[]{
                AGREE, AGREE,
                DISAGREE, DISAGREE,
                STRONGLY_DISAGREE, STRONGLY_AGREE,
                STRONGLY_AGREE, NEUTRAL,
                STRONGLY_DISAGREE, AGREE
        };
        Assertions.assertEquals(calculate(answerChoices), 37.5, 0);
    }
}
