package it.previsuite.service.mapper;

import it.previsuite.bean.UserBean;
import it.previsuite.bean.exceptions.PreviException;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import it.previsuite.bean.utils.DateUtils;
import it.previsuite.model.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Hibernate;

import java.sql.Timestamp;
import java.util.Date;

@ApplicationScoped
public class UsersMapper extends AbstractMapper<Users, UserBean> {
    private final MemberMapper memberMapper;

    @Inject
    public UsersMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public UserBean mapEntityToBean(Users entity) {
        if (entity != null) {
            UserBean bean = new UserBean();
            bean.setId(entity.getId());
            bean.setUsername(entity.getUsername());
            bean.setPasswordValidityDays(entity.getPasswordValidityDays());
            bean.setState(entity.getState());
            bean.setCreationTimestamp(DateUtils.convert(entity.getCreationTimestamp(), DateUtils.ISO_ZONED_DATE_TIME));
            bean.setLastAccessTimestamp(DateUtils.convert(entity.getLastAccessTimestamp(), DateUtils.ISO_ZONED_DATE_TIME));

            if (entity.getMember() != null && Hibernate.isInitialized(entity.getMember())) {
                bean.setMember(memberMapper.mapEntityToBean(entity.getMember()));
            }

            return bean;
        }

        return null;
    }

    @Override
    public Users mapBeanToEntity(UserBean bean) {
        try {
            if (bean != null) {
                Date creationTimestamp = DateUtils.convert(bean.getCreationTimestamp(), DateUtils.ISO_ZONED_DATE_TIME);
                Date lastAccessTimestamp = DateUtils.convert(bean.getLastAccessTimestamp(), DateUtils.ISO_ZONED_DATE_TIME);

                Users entity = new Users();
                entity.setId(bean.getId());
                entity.setUsername(bean.getUsername());
                entity.setPasswordValidityDays(bean.getPasswordValidityDays());
                entity.setState(bean.getState());

                if (creationTimestamp != null) {
                    entity.setCreationTimestamp(new Timestamp(creationTimestamp.getTime()));
                }

                if (lastAccessTimestamp != null) {
                    entity.setLastAccessTimestamp(new Timestamp(lastAccessTimestamp.getTime()));
                }

                if (bean.getMember() != null) {
                    entity.setMember(memberMapper.mapBeanToEntity(bean.getMember()));
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