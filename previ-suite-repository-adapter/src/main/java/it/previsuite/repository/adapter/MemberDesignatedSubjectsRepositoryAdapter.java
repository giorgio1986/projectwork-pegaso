package it.previsuite.repository.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.exceptions.PreviSqlException;
import it.previsuite.model.MemberDesignatedSubjects;
import it.previsuite.repository.adapter.abstracts.AbstractPostgresqlRepository;
import it.previsuite.repository.port.MemberDesignatedSubjectsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MemberDesignatedSubjectsRepositoryAdapter extends AbstractPostgresqlRepository<MemberDesignatedSubjects> implements MemberDesignatedSubjectsRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemberDesignatedSubjectsRepositoryAdapter.class);

    @Override
    public Uni<MemberDesignatedSubjects> save(MemberDesignatedSubjects entity) {
        if(entity.getId() != null) {
            return this
                    .findById(entity.getId())
                    .onItem()
                    .ifNotNull()
                    .transformToUni(existing -> this.checkUpdate(entity, existing))
                    .onItem()
                    .ifNull()
                    .failWith(() -> new PreviSqlException(String.format("MemberDesignatedSubject with ID: %s not found", entity.getId())));
        }

        return this.persistAndFlush(entity);
    }

    private Uni<MemberDesignatedSubjects> checkUpdate(MemberDesignatedSubjects entity, MemberDesignatedSubjects existing) {
        if (entity.getBeneficiaryId() != null && !existing.getBeneficiaryId().equals(entity.getBeneficiaryId())) {
            existing.setBeneficiaryId(entity.getBeneficiaryId());
        }

        if (entity.getDistribution() != null && !existing.getDistribution().equals(entity.getDistribution())) {
            existing.setDistribution(entity.getDistribution());
        }

        if (entity.getOrdering() != null && !existing.getOrdering().equals(entity.getOrdering())) {
            existing.setOrdering(entity.getOrdering());
        }

        return this.persistAndFlush(existing);
    }
}