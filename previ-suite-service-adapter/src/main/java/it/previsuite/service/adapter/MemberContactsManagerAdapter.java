package it.previsuite.service.adapter;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberContactBean;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import it.previsuite.model.MemberContacts;
import it.previsuite.repository.port.MemberContactsRepository;
import it.previsuite.service.mapper.MemberContactsMapper;
import it.previsuite.service.port.MemberContactsManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MemberContactsManagerAdapter extends AbstractManagerAdapter implements MemberContactsManager {
    private static final Logger logger = LoggerFactory.getLogger(MemberContactsManagerAdapter.class);
    private final String className = MemberContactsManagerAdapter.class.getName();
    private final MemberContactsRepository memberContactsRepository;
    private final MemberContactsMapper memberContactsMapper;
    private static final String QUERY = MemberContacts.FIELDS.MEMBER_ID + " = ?1";

    @Inject
    public MemberContactsManagerAdapter(MemberContactsRepository memberContactsRepository, MemberContactsMapper memberContactsMapper) {
        this.memberContactsRepository = memberContactsRepository;
        this.memberContactsMapper = memberContactsMapper;
    }

    @Override
    @WithTransaction
    public Uni<MemberContactBean> getMemberContacts(Long memberId) {
        return memberContactsRepository
                .find(QUERY, memberId)
                .firstResult()
                .onItem()
                .transform(memberContactsMapper::mapEntityToBean)
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on getMemberContacts(): {}", this.className, error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberContactBean> addMemberContacts(Long memberId, MemberContactBean request) {
        MemberContacts contacts = memberContactsMapper.mapBeanToEntity(request);
        contacts.setMemberId(memberId);

        return memberContactsRepository
                .save(contacts)
                .onItem()
                .transform(memberContactsMapper::mapEntityToBean)
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on addMemberContacts(): {}", this.className, error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberContactBean> updateMemberContacts(Long memberId, MemberContactBean request) {
        return memberContactsRepository
                .find(QUERY, memberId)
                .firstResult()
                .flatMap(memberContacts -> {
                    MemberContacts contacts = memberContactsMapper.mapBeanToEntity(request);
                    contacts.setId(memberContacts.getId());
                    contacts.setMemberId(memberContacts.getMemberId());

                    return memberContactsRepository
                            .save(contacts)
                            .onItem()
                            .transform(memberContactsMapper::mapEntityToBean);
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on updateMemberContacts(): {}", this.className, error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }
}