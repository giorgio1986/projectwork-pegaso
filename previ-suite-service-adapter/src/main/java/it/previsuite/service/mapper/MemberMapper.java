package it.previsuite.service.mapper;

import it.previsuite.bean.MemberBean;
import it.previsuite.bean.enums.MemberGenderEnum;
import it.previsuite.bean.exceptions.PreviException;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import it.previsuite.bean.utils.DateUtils;
import it.previsuite.model.Members;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Timestamp;
import java.util.Date;

@ApplicationScoped
public class MemberMapper extends AbstractMapper<Members, MemberBean> {

    @Override
    public MemberBean mapEntityToBean(Members entity) {
        if (entity != null) {
            MemberBean bean = new MemberBean();
            bean.setId(entity.getId());
            bean.setName(entity.getName());
            bean.setSurname(entity.getSurname());
            bean.setFiscalCode(entity.getFiscalCode());
            bean.setBirthDate(DateUtils.convert(entity.getBirthDate(), DateUtils.ISO_LOCAL_DATE));
            bean.setGender(entity.getGender().name());
            bean.setCreationTimestamp(DateUtils.convert(entity.getCreationTimestamp(), DateUtils.ISO_ZONED_DATE_TIME));
            bean.setPensionRegistrationDate(DateUtils.convert(entity.getPensionRegistrationDate(), DateUtils.ISO_LOCAL_DATE));
            bean.setFirstPensionRegistrationDate(DateUtils.convert(entity.getFirstPensionRegistrationDate(), DateUtils.ISO_LOCAL_DATE));

            return bean;
        }

        return null;
    }

    @Override
    public Members mapBeanToEntity(MemberBean bean) {
        try {
            if (bean != null) {
                Date birthDate = DateUtils.convert(bean.getBirthDate(), DateUtils.ISO_LOCAL_DATE);
                Date creationTimestamp = DateUtils.convert(bean.getCreationTimestamp(), DateUtils.ISO_ZONED_DATE_TIME);
                Date pensionRegistrationDate = DateUtils.convert(bean.getBirthDate(), DateUtils.ISO_LOCAL_DATE);
                Date firstPensionRegistrationDate = DateUtils.convert(bean.getBirthDate(), DateUtils.ISO_LOCAL_DATE);

                Members entity = new Members();
                entity.setId(bean.getId());
                entity.setName(bean.getName());
                entity.setSurname(bean.getSurname());
                entity.setFiscalCode(bean.getFiscalCode());

                if (birthDate != null) {
                    entity.setBirthDate(new java.sql.Date(birthDate.getTime()));
                }

                if (bean.getGender() != null) {
                    entity.setGender(MemberGenderEnum.fromName(bean.getGender()));
                }

                if (creationTimestamp != null) {
                    entity.setCreationTimestamp(new Timestamp(creationTimestamp.getTime()));
                }

                if (pensionRegistrationDate != null) {
                    entity.setPensionRegistrationDate(new java.sql.Date(pensionRegistrationDate.getTime()));
                }

                if (firstPensionRegistrationDate != null) {
                    entity.setFirstPensionRegistrationDate(new java.sql.Date(firstPensionRegistrationDate.getTime()));
                }

                return entity;
            }
        }
        catch (PreviException e) {
            throw new PreviRuntimeException(e);
        }

        return null;
    }
}