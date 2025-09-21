package it.previsuite.service.mapper;

import it.previsuite.bean.MemberContactBean;
import it.previsuite.model.MemberContacts;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MemberContactsMapper extends AbstractMapper<MemberContacts, MemberContactBean> {

    @Override
    public MemberContactBean mapEntityToBean(MemberContacts entity) {
        if (entity != null) {
            MemberContactBean bean = new MemberContactBean();
            bean.setEmail(entity.getEmail());
            bean.setEmailPec(entity.getEmailPec());
            bean.setPhone(entity.getPhone());
            bean.setMobilePhone(entity.getMobilePhone());
            bean.setElectronicCommunications(entity.getElectronicCommunications());

            return bean;
        }

        return null;
    }

    @Override
    public MemberContacts mapBeanToEntity(MemberContactBean bean) {
        if (bean != null) {
            MemberContacts entity = new MemberContacts();
            entity.setEmail(bean.getEmail());
            entity.setEmailPec(bean.getEmailPec());
            entity.setPhone(bean.getPhone());
            entity.setMobilePhone(bean.getMobilePhone());
            entity.setElectronicCommunications(bean.getElectronicCommunications());

            return entity;
        }

        return null;
    }
}