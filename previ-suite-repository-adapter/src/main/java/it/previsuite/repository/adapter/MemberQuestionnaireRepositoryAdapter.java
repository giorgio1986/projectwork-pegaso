package it.previsuite.repository.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.exceptions.PreviSqlException;
import it.previsuite.model.MemberQuestionnaire;
import it.previsuite.repository.adapter.abstracts.AbstractPostgresqlRepository;
import it.previsuite.repository.port.MemberQuestionnaireRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MemberQuestionnaireRepositoryAdapter extends AbstractPostgresqlRepository<MemberQuestionnaire> implements MemberQuestionnaireRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemberQuestionnaireRepositoryAdapter.class);

    @Override
    public Uni<MemberQuestionnaire> save(MemberQuestionnaire entity) {
        if(entity.getId() != null) {
            return this
                    .findById(entity.getId())
                    .onItem()
                    .ifNotNull()
                    .transformToUni(existing -> this.checkUpdate(entity, existing))
                    .onItem()
                    .ifNull()
                    .failWith(() -> new PreviSqlException(String.format("MemberQuestionnaire with ID: %s not found", entity.getId())));
        }

        return this.persistAndFlush(entity);
    }

    private Uni<MemberQuestionnaire> checkUpdate(MemberQuestionnaire entity, MemberQuestionnaire existing) {
        if (entity.getFilledTimestamp() != null && !existing.getFilledTimestamp().equals(entity.getFilledTimestamp())) {
            existing.setFilledTimestamp(entity.getFilledTimestamp());
        }

        if (entity.getScore() != null && !existing.getScore().equals(entity.getScore())) {
            existing.setScore(entity.getScore());
        }

       if (entity.getAnswers() != null && !existing.getAnswers().equals(entity.getAnswers())) {
            existing.setAnswers(entity.getAnswers());
       }

        return this.persistAndFlush(existing);
    }
}