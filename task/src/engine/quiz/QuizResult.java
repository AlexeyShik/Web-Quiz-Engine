package engine.quiz;

class QuizResult {
    public final static QuizResult SUCCESSFUL = new QuizResult(true);
    public final static QuizResult FAILED = new QuizResult(false);

    private final boolean success;
    private final String feedback;

    private QuizResult(boolean isSuccessful) {
        success = isSuccessful;
        feedback = success ? "Congratulations, you are right!" : "Wrong answer! Please, try again.";
    }

    public boolean getSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
