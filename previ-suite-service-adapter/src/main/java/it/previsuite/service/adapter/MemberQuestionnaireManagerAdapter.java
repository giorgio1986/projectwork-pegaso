package it.previsuite.service.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberQuestionnaireAnswerBean;
import it.previsuite.bean.MemberQuestionnaireBean;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import it.previsuite.bean.utils.DateUtils;
import it.previsuite.model.Questionnaire;
import it.previsuite.model.MemberQuestionnaire;
import it.previsuite.repository.port.QuestionnaireRepository;
import it.previsuite.repository.port.MemberQuestionnaireRepository;
import it.previsuite.service.mapper.QuestionnaireMapper;
import it.previsuite.service.merger.MemberQuestionnaireMerger;
import it.previsuite.service.port.MemberQuestionnaireManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class MemberQuestionnaireManagerAdapter extends AbstractManagerAdapter implements MemberQuestionnaireManager {
    private static final Logger logger = LoggerFactory.getLogger(MemberQuestionnaireManagerAdapter.class);
    private final String className = MemberQuestionnaireManagerAdapter.class.getName();
    private final QuestionnaireRepository  questionnaireRepository;
    private final MemberQuestionnaireRepository memberQuestionnaireRepository;
    private final QuestionnaireMapper questionnaireMapper;
    private final MemberQuestionnaireMerger memberQuestionnaireMerger;
    private static final String QUESTIONNAIRE_QUERY = String.format("%s is null or %s > current_date order by %s desc", Questionnaire.FIELDS.EXPIRY_DATE, Questionnaire.FIELDS.EXPIRY_DATE, Questionnaire.FIELDS.CREATION_TIMESTAMP);
    private static final String MEMBER_QUESTIONNAIRE_QUERY = String.format("%s = ?1 and %s = ?2", MemberQuestionnaire.FIELDS.MEMBER_ID, MemberQuestionnaire.FIELDS.QUESTIONNARE_ID);

    @Inject
    public MemberQuestionnaireManagerAdapter(QuestionnaireRepository  questionnaireRepository
            , MemberQuestionnaireRepository memberQuestionnaireRepository
            , QuestionnaireMapper questionnaireMapper
            , MemberQuestionnaireMerger memberQuestionnaireMerger
    ) {
        this.questionnaireRepository = questionnaireRepository;
        this.memberQuestionnaireRepository = memberQuestionnaireRepository;
        this.questionnaireMapper = questionnaireMapper;
        this.memberQuestionnaireMerger = memberQuestionnaireMerger;
    }

    @Override
    @WithTransaction
    public Uni<MemberQuestionnaireBean> getMemberQuestionnaire(Long memberId) {
        return questionnaireRepository
                .find(QUESTIONNAIRE_QUERY)
                .firstResult()
                .flatMap(questionnaire ->
                        memberQuestionnaireRepository
                        .find(MEMBER_QUESTIONNAIRE_QUERY, memberId, questionnaire.getId())
                        .firstResult()
                        .onItem()
                        .transform(memberQuestionnaire -> {
                            MemberQuestionnaireBean bean = questionnaireMapper.mapEntityToBean(questionnaire);

                            if (memberQuestionnaire != null) {
                                bean.setScore(memberQuestionnaire.getScore());
                                bean.setFilledTimestamp(DateUtils.convert(memberQuestionnaire.getFilledTimestamp(), DateUtils.ISO_ZONED_DATE_TIME));
                                bean.setQuestions(memberQuestionnaireMerger.merge(bean.getQuestions(), memberQuestionnaire.getAnswers()));
                            }

                            return bean;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on getMemberQuestionnaire(): {}", this.className, error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberQuestionnaireBean> updateMemberQuestionnaire(Long memberId, Long idQuestionnaire, Map<Integer, String> request) {
        return questionnaireRepository
                .findById(idQuestionnaire)
                .onItem()
                .transformToUni(entity -> {
                    MemberQuestionnaireBean questionnaire = questionnaireMapper.mapEntityToBean(entity);
                    AtomicInteger score = new AtomicInteger(0);

                    questionnaire
                            .getQuestions()
                            .forEach(question -> {
                                switch(question.getType()) {
                                    case MULTIPLE_CHOICE:
                                        Integer idAnswer = Integer.parseInt(request.get(question.getIdQuestion()));
                                        score.addAndGet(question
                                                .getAnswers()
                                                .stream()
                                                .filter(answer -> answer.getIdAnswer().equals(idAnswer))
                                                .map(MemberQuestionnaireAnswerBean::getScore)
                                                .findFirst()
                                                .orElse(0));
                                        break;

                                    case TEXT:
                                    default:
                                        score.addAndGet(question.getAnswers().getFirst().getScore());
                                        break;
                                }
                            });

                    return memberQuestionnaireRepository
                            .find(MEMBER_QUESTIONNAIRE_QUERY, memberId, questionnaire.getId())
                            .firstResult()
                            .onItem()
                            .transformToUni(memberQEntity -> {
                                if (memberQEntity == null) {
                                    memberQEntity = new MemberQuestionnaire();
                                    memberQEntity.setId(null);
                                    memberQEntity.setMemberId(memberId);
                                    memberQEntity.setQuestionnaireId(questionnaire.getId());
                                }

                                memberQEntity.setFilledTimestamp(new Timestamp(System.currentTimeMillis()));
                                memberQEntity.setScore(score.get());

                                try {
                                    memberQEntity.setAnswers(mapper.writeValueAsString(request));
                                }
                                catch (JsonProcessingException e) {
                                    memberQEntity.setAnswers("{}");
                                }

                                return memberQuestionnaireRepository
                                        .save(memberQEntity)
                                        .onItem()
                                        .transform(updatedMemberQEntity -> {
                                            questionnaire.setScore(updatedMemberQEntity.getScore());
                                            questionnaire.setFilledTimestamp(DateUtils.convert(updatedMemberQEntity.getFilledTimestamp(), DateUtils.ISO_ZONED_DATE_TIME));
                                            questionnaire.setQuestions(memberQuestionnaireMerger.merge(questionnaire.getQuestions(), updatedMemberQEntity.getAnswers()));
                                            return questionnaire;
                                        });
                            });
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on updateMemberQuestionnaire(): {}", this.className, error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }
}