package it.previsuite.service.mapper;

import it.previsuite.bean.MemberDesignatedSubjectsBean;
import it.previsuite.model.MemberDesignatedSubjects;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Hibernate;

@ApplicationScoped
public class MemberDesignatedSubjectsMapper extends AbstractMapper<MemberDesignatedSubjects, MemberDesignatedSubjectsBean> {
    private final MemberBeneficiaryMapper memberBeneficiaryMapper;

    @Inject
    public MemberDesignatedSubjectsMapper(MemberBeneficiaryMapper memberBeneficiaryMapper) {
        this.memberBeneficiaryMapper = memberBeneficiaryMapper;
    }

    @Override
    public MemberDesignatedSubjectsBean mapEntityToBean(MemberDesignatedSubjects entity) {
        if (entity != null) {
            MemberDesignatedSubjectsBean bean = new MemberDesignatedSubjectsBean();
            bean.setId(entity.getId());
            bean.setDistribution(entity.getDistribution());
            bean.setOrdering(entity.getOrdering());

            if (entity.getBeneficiary() != null && Hibernate.isInitialized(entity.getBeneficiary())) {
                bean.setBeneficiary(memberBeneficiaryMapper.mapEntityToBean(entity.getBeneficiary()));
            }

            return bean;
        }

        return null;
    }

    @Override
    public MemberDesignatedSubjects mapBeanToEntity(MemberDesignatedSubjectsBean bean) {
        if (bean != null) {
            MemberDesignatedSubjects entity = new MemberDesignatedSubjects();
            entity.setId(bean.getId());
            entity.setBeneficiaryId(bean.getBeneficiaryId());
            entity.setDistribution(bean.getDistribution());
            entity.setOrdering(bean.getOrdering());

            return entity;
        }

        return null;
    }
}