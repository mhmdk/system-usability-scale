package usability.scale.system.calculator;

import usability.scale.system.calculator.dto.Answers;

public class ScoreCalculator {
    private static final double FACTOR = 2.5;
    private static final int NUMBER_OF_CHOICES = AnswerChoice.values().length;

    public double calculateScore(Answers answers) {
        return calculate(answers.getAnswers()) * FACTOR;
    }

    private double calculate(AnswerChoice[] answers) {
        double result = 0;
        for (int i = 0; i < answers.length; i++) {
            int questionIndex = i + 1;
            int choicePosition = answers[i].ordinal() + 1;
            if (isOdd(questionIndex)) {
                result += choicePosition - 1;
            } else {
                result += NUMBER_OF_CHOICES - choicePosition;
            }
        }
        return result;
    }

    private boolean isOdd(int n) {
        return n % 2 == 1;
    }

}
