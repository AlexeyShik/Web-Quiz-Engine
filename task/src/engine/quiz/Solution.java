package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    int DataBaseId;
    private int id;
    private LocalDateTime completedAt;
    @JsonIgnore
    private String completedBy;

    public Solution() {
    }

    public Solution(int id, LocalDateTime completedAt, String completedBy) {
        this.id = id;
        this.completedAt = completedAt;
        this.completedBy = completedBy;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    String getCompletedBy() {
        return completedBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }
}
