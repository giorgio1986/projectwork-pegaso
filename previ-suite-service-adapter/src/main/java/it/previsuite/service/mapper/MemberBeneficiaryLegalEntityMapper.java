package it.previsuite.service.mapper;

import it.previsuite.bean.MemberBeneficiaryLegalEntityBean;
import it.previsuite.model.MemberBeneficiary;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MemberBeneficiaryLegalEntityMapper extends AbstractMapper<MemberBeneficiary, MemberBeneficiaryLegalEntityBean> {

    @Override
    public MemberBeneficiaryLegalEntityBean mapEntityToBean(MemberBeneficiary entity) {
        if (entity != null) {
            MemberBeneficiaryLegalEntityBean bean = new MemberBeneficiaryLegalEntityBean();
            bean.setBeneficiaryType(entity.getBeneficiaryType());
            bean.setCompanyName(entity.getCompanyName());
            bean.setFiscalCode(entity.getFiscalCode());
            bean.setVatNumber(entity.getVatNumber());
            bean.setNation(entity.getNation());
            bean.setProvince(entity.getProvince());
            bean.setLocation(entity.getLocation());
            bean.setAddress(entity.getAddress());
            bean.setEmail(entity.getEmail());
            bean.setEmailPec(entity.getEmailPec());
            bean.setPhone(entity.getPhone());

            return bean;
        }

        return null;
    }

    @Override
    public MemberBeneficiary mapBeanToEntity(MemberBeneficiaryLegalEntityBean bean) {
        if (bean != null) {

            // LegalEntity fields
            MemberBeneficiary entity = new MemberBeneficiary();
            entity.setBeneficiaryType(bean.getBeneficiaryType());
            entity.setCompanyName(bean.getCompanyName());
            entity.setFiscalCode(bean.getFiscalCode());
            entity.setVatNumber(bean.getVatNumber());
            entity.setNation(bean.getNation());
            entity.setProvince(bean.getProvince());
            entity.setLocation(bean.getLocation());
            entity.setAddress(bean.getAddress());
            entity.setEmail(bean.getEmail());
            entity.setEmailPec(bean.getEmailPec());
            entity.setPhone(bean.getPhone());

            // NaturalEntity fields
            entity.setName(null);
            entity.setSurname(null);
            entity.setBirthDate(null);
            entity.setGender(null);
            entity.setBirthNation(null);
            entity.setBirthProvince(null);
            entity.setBirthLocation(null);
            entity.setMobilePhone(null);

            return entity;
        }

        return null;
    }
}