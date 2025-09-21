package it.previsuite.service.mapper;

import it.previsuite.bean.MemberBeneficiaryBaseBean;
import it.previsuite.bean.MemberBeneficiaryLegalEntityBean;
import it.previsuite.bean.MemberBeneficiaryNaturalEntityBean;
import it.previsuite.model.MemberBeneficiary;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MemberBeneficiaryMapper extends AbstractMapper<MemberBeneficiary, MemberBeneficiaryBaseBean> {
    private final MemberBeneficiaryLegalEntityMapper legalEntityMapper;
    private final MemberBeneficiaryNaturalEntityMapper naturalEntityMapper;

    @Inject
    public MemberBeneficiaryMapper(MemberBeneficiaryLegalEntityMapper legalEntityMapper, MemberBeneficiaryNaturalEntityMapper naturalEntityMapper) {
        this.legalEntityMapper = legalEntityMapper;
        this.naturalEntityMapper = new MemberBeneficiaryNaturalEntityMapper();
    }

    @Override
    public MemberBeneficiaryBaseBean mapEntityToBean(MemberBeneficiary entity) {
        if (entity != null) {
            switch (entity.getBeneficiaryType()) {
                case LEGAL_ENTITY -> {
                    MemberBeneficiaryLegalEntityBean bean = legalEntityMapper.mapEntityToBean(entity);
                    bean.setId(entity.getId());
                    return bean;
                }

                case NATURAL_ENTITY -> {
                    MemberBeneficiaryNaturalEntityBean bean = naturalEntityMapper.mapEntityToBean(entity);
                    bean.setId(entity.getId());
                    return bean;
                }
            }
        }

        return null;
    }

    @Override
    public MemberBeneficiary mapBeanToEntity(MemberBeneficiaryBaseBean bean) {
        if (bean != null) {
            switch (bean.getBeneficiaryType()) {
                case LEGAL_ENTITY -> {
                    MemberBeneficiary entity = legalEntityMapper.mapBeanToEntity(mapper.convertValue(bean, MemberBeneficiaryLegalEntityBean.class));
                    entity.setId(bean.getId());
                    return entity;
                }

                case NATURAL_ENTITY -> {
                    MemberBeneficiary entity = naturalEntityMapper.mapBeanToEntity(mapper.convertValue(bean, MemberBeneficiaryNaturalEntityBean.class));
                    entity.setId(bean.getId());
                    return entity;
                }
            }
        }

        return null;
    }
}