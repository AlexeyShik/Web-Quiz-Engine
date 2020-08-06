package engine.quiz;

import engine.user.User;
import engine.exceptions.ForbiddenResourceException;
import engine.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/api/quizzes")
class QuizController {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @GetMapping(path = "/{id}")
    public Quiz getQuiz(@PathVariable(name = "id") int id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Quiz with id = %d not found.", id)));
    }

    @GetMapping
    public Page<Quiz> getQuizzesFromPage(@RequestParam(name = "page", defaultValue = "0") int page) {
        return quizRepository.findAll(PageRequest.of(page, 10));
    }

    @GetMapping(path = "/completed")
    public Page<Solution> getSolvedQuizzes(@RequestParam(name = "page", defaultValue = "0") int page, @AuthenticationPrincipal User user) {
        return solutionRepository.findAllByCompletedBy(user.getEmail(), PageRequest.of(page, 10, Sort.by("completedAt").descending()));
    }

    @PostMapping(consumes = "application/json")
    public Quiz addQuiz(@Valid @RequestBody Quiz userQuiz, @AuthenticationPrincipal User user) {
        userQuiz.setMadeBy(user.getEmail());
        return quizRepository.save(userQuiz);
    }

    @PostMapping(path = "/{id}/solve")
    public QuizResult solveQuiz(@PathVariable(name = "id") int id,
                                @RequestBody QuizAnswer answer, @AuthenticationPrincipal User user) {
        Quiz quiz = getQuiz(id);
        if (Objects.equals(answer.getAnswer(), quiz.getAnswer())) {
            solutionRepository.save(new Solution(quiz.getId(), LocalDateTime.now(), user.getEmail()));
            return QuizResult.SUCCESSFUL;
        }
        return QuizResult.FAILED;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable(name = "id") int id, @AuthenticationPrincipal User user) {
        Quiz quiz = getQuiz(id);
        expectAuthorOfQuiz(quiz, user, "delete");
        quizRepository.delete(quiz);
    }

    //  fields id and madeBy in Quiz cannot be changed
    @PutMapping(path = "/{id}")
    public void updateQuiz(@PathVariable(name = "id") int id, @Valid @RequestBody Quiz newQuiz,
                           @AuthenticationPrincipal User user) {
        Quiz quiz = getQuiz(id);
        expectAuthorOfQuiz(quiz, user, "update");
        quiz.setTitle(newQuiz.getTitle());
        quiz.setText(newQuiz.getText());
        quiz.setOptions(newQuiz.getOptions());
        quiz.setAnswer(newQuiz.getAnswer());
        quizRepository.save(quiz);
    }

    private static void expectAuthorOfQuiz(Quiz quiz, User user, String operation) {
        if (!Objects.equals(quiz.getMadeBy(), user.getEmail())) {
            throw new ForbiddenResourceException(String.format("Cannot %s quiz, created by other user", operation));
        }
    }
}
