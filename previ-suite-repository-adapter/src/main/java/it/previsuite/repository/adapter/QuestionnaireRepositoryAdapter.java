package it.previsuite.repository.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.exceptions.PreviSqlException;
import it.previsuite.model.Questionnaire;
import it.previsuite.repository.adapter.abstracts.AbstractPostgresqlRepository;
import it.previsuite.repository.port.QuestionnaireRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuestionnaireRepositoryAdapter extends AbstractPostgresqlRepository<Questionnaire> implements QuestionnaireRepository {
    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireRepositoryAdapter.class);

    @Override
    public Uni<Questionnaire> save(Questionnaire entity) {
        logger.error("[QuestionnaireRepositoryAdapter] error on save(): create or update not allowed");
        return Uni
                .createFrom()
                .failure(new PreviSqlException("create or update not allowed"));
    }
}