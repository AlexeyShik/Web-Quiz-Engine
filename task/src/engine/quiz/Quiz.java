package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotBlank(message = "Text should not be blank")
    private String text;

    @NotNull(message = "Options should not be null")
    @NotEmpty(message = "Options should not be empty")
    @Size(min = 2, message = "Minimum 2 options required")
    @ElementCollection
    private List<String> options;

    @ElementCollection
    private List<Integer> answer;

    @JsonIgnore
    private String madeBy;

    public Quiz() {
    }

    List<Integer> getAnswer() {
        return answer;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getId() {
        return id;
    }

    public String getMadeBy() {
        return madeBy;
    }

    void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setText(String text) {
        this.text = text;
    }

    void setOptions(List<String> options) {
        this.options = options;
    }

    void setId(int id) {
        this.id = id;
    }

    void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }
}
