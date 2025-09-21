package it.previsuite.service.mapper;

import it.previsuite.bean.MemberBeneficiaryNaturalEntityBean;
import it.previsuite.bean.enums.MemberGenderEnum;
import it.previsuite.bean.exceptions.PreviException;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import it.previsuite.bean.utils.DateUtils;
import it.previsuite.model.MemberBeneficiary;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Date;

@ApplicationScoped
public class MemberBeneficiaryNaturalEntityMapper extends AbstractMapper<MemberBeneficiary, MemberBeneficiaryNaturalEntityBean> {

    @Override
    public MemberBeneficiaryNaturalEntityBean mapEntityToBean(MemberBeneficiary entity) {
        if (entity != null) {
            MemberBeneficiaryNaturalEntityBean bean = new MemberBeneficiaryNaturalEntityBean();
            bean.setBeneficiaryType(entity.getBeneficiaryType());
            bean.setName(entity.getName());
            bean.setSurname(entity.getSurname());
            bean.setFiscalCode(entity.getFiscalCode());
            bean.setBirthDate(DateUtils.convert(entity.getBirthDate(), DateUtils.ISO_LOCAL_DATE));
            bean.setGender(entity.getGender().name());
            bean.setBirthNation(entity.getBirthNation());
            bean.setBirthProvince(entity.getBirthProvince());
            bean.setBirthLocation(entity.getBirthLocation());
            bean.setNation(entity.getNation());
            bean.setProvince(entity.getProvince());
            bean.setLocation(entity.getLocation());
            bean.setMobilePhone(entity.getMobilePhone());

            return bean;
        }

        return null;
    }

    @Override
    public MemberBeneficiary mapBeanToEntity(MemberBeneficiaryNaturalEntityBean bean) {
        try {
            if (bean != null) {
                Date birthDate = DateUtils.convert(bean.getBirthDate(), DateUtils.ISO_LOCAL_DATE);

                // NaturalEntity fields
                MemberBeneficiary entity = new MemberBeneficiary();
                entity.setBeneficiaryType(bean.getBeneficiaryType());
                entity.setName(bean.getName());
                entity.setSurname(bean.getSurname());
                entity.setFiscalCode(bean.getFiscalCode());
                entity.setBirthNation(bean.getBirthNation());
                entity.setBirthProvince(bean.getBirthProvince());
                entity.setBirthLocation(bean.getBirthLocation());
                entity.setNation(bean.getNation());
                entity.setProvince(bean.getProvince());
                entity.setLocation(bean.getLocation());
                entity.setMobilePhone(bean.getMobilePhone());

                if (birthDate != null) {
                    entity.setBirthDate(new java.sql.Date(birthDate.getTime()));
                }

                if (bean.getGender() != null) {
                    entity.setGender(MemberGenderEnum.fromName(bean.getGender()));
                }

                // LegalEntity fields
                entity.setCompanyName(null);
                entity.setVatNumber(null);
                entity.setAddress(null);
                entity.setEmail(null);
                entity.setEmailPec(null);
                entity.setPhone(null);

                return entity;
            }
        }
        catch (PreviException e) {
            throw new PreviRuntimeException(e);
        }

        return null;
    }
}