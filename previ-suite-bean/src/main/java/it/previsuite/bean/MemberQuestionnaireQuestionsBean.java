package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.previsuite.bean.enums.QuestionnaireAnswersTypeEnum;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberQuestionnaireQuestionsBean {

    @JsonProperty("idQuestion")
    private Integer idQuestion;

    @JsonProperty("question")
    private String question;

    @JsonProperty("paragraph")
    private String paragraph;

    @JsonProperty("givenAnswer")
    private String givenAnswer;

    @JsonProperty("type")
    private QuestionnaireAnswersTypeEnum type;

    @JsonProperty("answers")
    private List<MemberQuestionnaireAnswerBean> answers;

    public Integer getIdQuestion() {
        return idQuestion;
    }
    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getParagraph() {
        return paragraph;
    }
    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }
    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public QuestionnaireAnswersTypeEnum getType() {
        return type;
    }
    public void setType(QuestionnaireAnswersTypeEnum type) {
        this.type = type;
    }

    public List<MemberQuestionnaireAnswerBean> getAnswers() {
        return answers;
    }
    public void setAnswers(List<MemberQuestionnaireAnswerBean> answers) {
        this.answers = answers;
    }
}