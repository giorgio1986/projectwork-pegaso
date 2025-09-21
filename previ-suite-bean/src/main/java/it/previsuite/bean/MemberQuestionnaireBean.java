package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberQuestionnaireBean {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("filledTimestamp")
    private String filledTimestamp;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("questions")
    private List<MemberQuestionnaireQuestionsBean> questions;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFilledTimestamp() {
        return filledTimestamp;
    }
    public void setFilledTimestamp(String filledTimestamp) {
        this.filledTimestamp = filledTimestamp;
    }

    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }

    public List<MemberQuestionnaireQuestionsBean> getQuestions() {
        return questions;
    }
    public void setQuestions(List<MemberQuestionnaireQuestionsBean> questions) {
        this.questions = questions;
    }
}