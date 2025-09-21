package it.previsuite.service.mapper;

import it.previsuite.bean.MemberAddressesBean;
import it.previsuite.model.MemberAddresses;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MemberAddressesMapper extends AbstractMapper<MemberAddresses, MemberAddressesBean> {

    @Override
    public MemberAddressesBean mapEntityToBean(MemberAddresses entity) {
        if (entity != null) {
            MemberAddressesBean bean = new MemberAddressesBean();
            bean.setId(entity.getId());
            bean.setAddressType(entity.getAddressType());
            bean.setNation(entity.getNation());
            bean.setProvince(entity.getProvince());
            bean.setLocation(entity.getLocation());
            bean.setAddress(entity.getAddress());

            return bean;
        }

        return null;
    }

    @Override
    public MemberAddresses mapBeanToEntity(MemberAddressesBean bean) {
        if (bean != null) {
            MemberAddresses entity = new MemberAddresses();
            entity.setId(bean.getId());
            entity.setAddressType(bean.getAddressType());
            entity.setNation(bean.getNation());
            entity.setProvince(bean.getProvince());
            entity.setLocation(bean.getLocation());
            entity.setAddress(bean.getAddress());

            return entity;
        }

        return null;
    }
}