package it.previsuite.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import it.previsuite.bean.MemberQuestionnaireBean;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import it.previsuite.model.Questionnaire;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuestionnaireMapper extends AbstractMapper<Questionnaire, MemberQuestionnaireBean> {

    @Override
    public MemberQuestionnaireBean mapEntityToBean(Questionnaire entity) {
        try {
            if (entity != null) {
                MemberQuestionnaireBean bean = new MemberQuestionnaireBean();
                bean.setId(entity.getId());
                bean.setScore(0);
                bean.setQuestions(mapper.readValue(entity.getQuestionnaire(), new TypeReference<>() {}));

                return bean;
            }
        }
        catch (JsonProcessingException e) {
            throw new PreviRuntimeException(e);
        }

        return null;
    }

    @Override
    public Questionnaire mapBeanToEntity(MemberQuestionnaireBean bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}