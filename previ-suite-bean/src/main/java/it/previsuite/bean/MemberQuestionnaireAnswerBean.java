package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberQuestionnaireAnswerBean {

    @JsonProperty("idAnswer")
    private Integer idAnswer;

    @JsonProperty("answer")
    private String answer;

    @JsonProperty("score")
    private Integer score;

    public Integer getIdAnswer() {
        return idAnswer;
    }
    public void setIdAnswer(Integer idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
}