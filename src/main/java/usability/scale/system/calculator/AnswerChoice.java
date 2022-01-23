package usability.scale.system.calculator;

public enum AnswerChoice {
    STRONGLY_DISAGREE("Strongly Disagree"),
    DISAGREE("Disagree"),
    NEUTRAL("Neutral"),
    AGREE("Agree"),
    STRONGLY_AGREE("Strongly Agree");

    private final String description;

    public String getDescription() {
        return description;
    }

    private AnswerChoice(String description) {
        this.description = description;
    }
}
