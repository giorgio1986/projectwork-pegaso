package it.previsuite.repository.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.exceptions.PreviSqlException;
import it.previsuite.model.MemberBeneficiary;
import it.previsuite.repository.adapter.abstracts.AbstractPostgresqlRepository;
import it.previsuite.repository.port.MemberBeneficiariesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@ApplicationScoped
public class MemberBeneficiariesRepositoryAdapter extends AbstractPostgresqlRepository<MemberBeneficiary> implements MemberBeneficiariesRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemberBeneficiariesRepositoryAdapter.class);

    @Override
    public Uni<MemberBeneficiary> save(MemberBeneficiary entity) {
        if(entity.getId() != null) {
            return this
                    .findById(entity.getId())
                    .onItem()
                    .ifNotNull()
                    .transformToUni(existing -> this.checkUpdate(entity, existing))
                    .onItem()
                    .ifNull()
                    .failWith(() -> new PreviSqlException(String.format("MemberBeneficiary with ID: %s not found", entity.getId())));
        }

        return this.persistAndFlush(entity);
    }

    private Uni<MemberBeneficiary> checkUpdate(MemberBeneficiary entity, MemberBeneficiary existing) {
        if (entity.getBeneficiaryType() != null && !existing.getBeneficiaryType().equals(entity.getBeneficiaryType())) {
            existing.setBeneficiaryType(entity.getBeneficiaryType());
        }

        if (!Objects.equals(existing.getName(), entity.getName())) {
            existing.setName(entity.getName());
        }

        if (!Objects.equals(existing.getSurname(), entity.getSurname())) {
            existing.setSurname(entity.getSurname());
        }

        if (!Objects.equals(existing.getFiscalCode(), entity.getFiscalCode())) {
            existing.setFiscalCode(entity.getFiscalCode());
        }

        if (!Objects.equals(existing.getBirthDate(), entity.getBirthDate())) {
            existing.setBirthDate(entity.getBirthDate());
        }

        if (!Objects.equals(existing.getGender(), entity.getGender())) {
            existing.setGender(entity.getGender());
        }

        if (!Objects.equals(existing.getBirthNation(), entity.getBirthNation())) {
            existing.setBirthNation(entity.getBirthNation());
        }

        if (!Objects.equals(existing.getBirthProvince(), entity.getBirthProvince())) {
            existing.setBirthProvince(entity.getBirthProvince());
        }

        if (!Objects.equals(existing.getBirthLocation(), entity.getBirthLocation())) {
            existing.setBirthLocation(entity.getBirthLocation());
        }

        if (!Objects.equals(existing.getNation(), entity.getNation())) {
            existing.setNation(entity.getNation());
        }

        if (!Objects.equals(existing.getProvince(), entity.getProvince())) {
            existing.setProvince(entity.getProvince());
        }

        if (!Objects.equals(existing.getLocation(), entity.getLocation())) {
            existing.setLocation(entity.getLocation());
        }

        if (!Objects.equals(existing.getAddress(), entity.getAddress())) {
            existing.setAddress(entity.getAddress());
        }

        if (!Objects.equals(existing.getCompanyName(), entity.getCompanyName())) {
            existing.setCompanyName(entity.getCompanyName());
        }

        if (!Objects.equals(existing.getVatNumber(), entity.getVatNumber())) {
            existing.setVatNumber(entity.getVatNumber());
        }

        if (!Objects.equals(existing.getEmail(), entity.getEmail())) {
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

        return this.persistAndFlush(existing);
    }
}