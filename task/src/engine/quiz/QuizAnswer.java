package engine.quiz;

import java.util.ArrayList;
import java.util.List;

class QuizAnswer {
    private List<Integer> answer;

    public QuizAnswer() {
        answer = new ArrayList<>();
    }

    public QuizAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
