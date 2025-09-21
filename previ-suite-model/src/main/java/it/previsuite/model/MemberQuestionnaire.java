package it.previsuite.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.sql.Timestamp;

@Entity(name = "MemberQuestionnaire")
@Table(name = "member_questionnaire", uniqueConstraints = { @UniqueConstraint(columnNames = {"member_id", "questionnaire_id"}) })
public class MemberQuestionnaire {

    public interface FIELDS {
        String ID = "id";
        String MEMBER_ID = "memberId";
        String QUESTIONNARE_ID = "questionnaireId";
        String FILLED_TIMESTAMP = "filledTimestamp";
        String SCORE = "score";
        String ANSWERS = "answers";
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "questionnaire_id", nullable = false)
    private Long questionnaireId;

    @Column(name = "filled_timestamp", nullable = false)
    private Timestamp filledTimestamp;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "answers", nullable = false)
    private String answers;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }
    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Timestamp getFilledTimestamp() {
        return filledTimestamp;
    }
    public void setFilledTimestamp(Timestamp filledTimestamp) {
        this.filledTimestamp = filledTimestamp;
    }

    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAnswers() {
        return answers;
    }
    public void setAnswers(String answers) {
        this.answers = answers;
    }
}