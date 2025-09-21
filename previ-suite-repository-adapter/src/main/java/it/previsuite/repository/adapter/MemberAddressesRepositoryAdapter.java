package it.previsuite.repository.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.exceptions.PreviSqlException;
import it.previsuite.model.MemberAddresses;
import it.previsuite.repository.adapter.abstracts.AbstractPostgresqlRepository;
import it.previsuite.repository.port.MemberAddressesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MemberAddressesRepositoryAdapter extends AbstractPostgresqlRepository<MemberAddresses> implements MemberAddressesRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemberAddressesRepositoryAdapter.class);

    @Override
    public Uni<MemberAddresses> save(MemberAddresses entity) {
        if(entity.getId() != null) {
            return this
                    .findById(entity.getId())
                    .onItem()
                    .ifNotNull()
                    .transformToUni(existing -> this.checkUpdate(entity, existing))
                    .onItem()
                    .ifNull()
                    .failWith(() -> new PreviSqlException(String.format("MemberAddresses with ID: %s not found", entity.getId())));
        }

        return this.persistAndFlush(entity);
    }

    private Uni<MemberAddresses> checkUpdate(MemberAddresses entity, MemberAddresses existing) {
        if (entity.getAddressType() != null && !existing.getAddressType().equals(entity.getAddressType())) {
            existing.setAddressType(entity.getAddressType());
        }

        if (entity.getNation() != null && !existing.getNation().equals(entity.getNation())) {
            existing.setNation(entity.getNation());
        }

        if (entity.getProvince() != null && !existing.getProvince().equals(entity.getProvince())) {
            existing.setProvince(entity.getProvince());
        }

        if (entity.getLocation() != null && !existing.getLocation().equals(entity.getLocation())) {
            existing.setLocation(entity.getLocation());
        }

        if (entity.getAddress() != null && !existing.getAddress().equals(entity.getAddress())) {
            existing.setAddress(entity.getAddress());
        }

        return this.persistAndFlush(existing);
    }
}