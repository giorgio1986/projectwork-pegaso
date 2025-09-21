package it.previsuite.service.merger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import it.previsuite.bean.MemberQuestionnaireQuestionsBean;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class MemberQuestionnaireMerger extends AbstractMerger<List<MemberQuestionnaireQuestionsBean>, String> {

    @Override
    public List<MemberQuestionnaireQuestionsBean> merge(List<MemberQuestionnaireQuestionsBean> questions, String jsonAnswers) {
        try {
            Map<Integer, String> answers = mapper.readValue(jsonAnswers, new TypeReference<>() {});

            questions
                .forEach(question -> {
                    if (answers.containsKey(question.getIdQuestion())) {
                        question.setGivenAnswer(answers.get(question.getIdQuestion()));
                    }
                });
        }
        catch (JsonProcessingException e) {
            throw new PreviRuntimeException(e);
        }

        return questions;
    }
}