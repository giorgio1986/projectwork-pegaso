package it.previsuite.repository.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.exceptions.PreviSqlException;
import it.previsuite.model.MemberContacts;
import it.previsuite.repository.adapter.abstracts.AbstractPostgresqlRepository;
import it.previsuite.repository.port.MemberContactsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
public class MemberContactsRepositoryAdapter extends AbstractPostgresqlRepository<MemberContacts> implements MemberContactsRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemberContactsRepositoryAdapter.class);

    @Override
    public Uni<MemberContacts> save(MemberContacts entity) {
        if(entity.getId() != null) {
            return this
                    .findById(entity.getId())
                    .onItem()
                    .ifNotNull()
                    .transformToUni(existing -> this.checkUpdate(entity, existing))
                    .onItem()
                    .ifNull()
                    .failWith(() -> new PreviSqlException(String.format("MemberContact with ID: %s not found", entity.getId())));
        }

        entity.setElectronicCommunications(Boolean.TRUE.equals(entity.getElectronicCommunications()));
        return this.persistAndFlush(entity);
    }

    private Uni<MemberContacts> checkUpdate(MemberContacts entity, MemberContacts existing) {
        if (entity.getEmail() != null && !existing.getEmail().equals(entity.getEmail())) {
            existing.setEmail(entity.getEmail());
        }

        if (!Objects.equals(existing.getEmailPec(), entity.getEmailPec())) {
            existing.setEmailPec(entity.getEmailPec());
        }

        if (!Objects.equals(existing.getPhone(), entity.getPhone())) {
            existing.setPhone(entity.getPhone());
        }

        if (!Objects.equals(existing.getMobilePhone(), entity.getMobilePhone())) {
            existing.setMobilePhone(entity.getMobilePhone());
        }

        existing.setElectronicCommunications(Boolean.TRUE.equals(entity.getElectronicCommunications()));
        return this.persistAndFlush(existing);
    }
}