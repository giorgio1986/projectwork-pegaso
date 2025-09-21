package it.previsuite.service.adapter;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberAddressesBean;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import it.previsuite.bean.request.MemberAddressesRequestBean;
import it.previsuite.model.MemberAddresses;
import it.previsuite.repository.port.MemberAddressesRepository;
import it.previsuite.service.mapper.MemberAddressesMapper;
import it.previsuite.service.port.MemberAddressesManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class MemberAddressesManagerAdapter extends AbstractManagerAdapter implements MemberAddressesManager {
    private static final Logger logger = LoggerFactory.getLogger(MemberAddressesManagerAdapter.class);
    private final String className = MemberAddressesManagerAdapter.class.getName();
    private final MemberAddressesRepository memberAddressesRepository;
    private final MemberAddressesMapper memberAddressesMapper;
    private static final String QUERY = MemberAddresses.FIELDS.MEMBER_ID + " = ?1";

    @Inject
    public MemberAddressesManagerAdapter(MemberAddressesRepository memberAddressesRepository, MemberAddressesMapper memberAddressesMapper) {
        this.memberAddressesRepository = memberAddressesRepository;
        this.memberAddressesMapper = memberAddressesMapper;
    }

    @Override
    @WithTransaction
    public Uni<List<MemberAddressesBean>> getMemberAddresses(Long memberId) {
        return memberAddressesRepository
                .find(QUERY, memberId)
                .list()
                .onItem()
                .transform(memberAddressesMapper::mapEntitiesToBeans)
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on getMemberAddresses(): {}", this.className, error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberAddressesBean> addMemberAddress(Long memberId, MemberAddressesRequestBean request) {
        MemberAddressesBean addressBean = this.mapper.convertValue(request, MemberAddressesBean.class);

        MemberAddresses address = memberAddressesMapper.mapBeanToEntity(addressBean);
        address.setMemberId(memberId);
        address.setId(null);

        return memberAddressesRepository
                .save(address)
                .onItem()
                .transform(memberAddressesMapper::mapEntityToBean)
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on addMemberAddress(): {}", this.className, error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberAddressesBean> updateMemberAddress(Long memberId, MemberAddressesBean request) {
        return memberAddressesRepository
                .findById(request.getId())
                .flatMap(memberAddress -> {
                    if (memberAddress == null) {
                        logger.error("[{}] error on updateMemberAddress(): MemberAddresses with ID: {} not found", this.className, request.getId());
                        return Uni.createFrom().failure(new PreviRuntimeException("Invalid request: address with ID: " + request.getId() + " not found"));
                    }

                    if (memberId.intValue() != memberAddress.getMemberId()) {
                        logger.error("[{}] error on updateMemberContacts(): incorrect memberId expected: {} provided: {}", this.className, memberId, memberAddress.getMemberId());
                        return Uni.createFrom().failure(new PreviRuntimeException("Invalid request: incorrect memberId"));
                    }

                    MemberAddresses address = memberAddressesMapper.mapBeanToEntity(request);
                    address.setMemberId(memberId);

                    return memberAddressesRepository
                            .save(address)
                            .onItem()
                            .transform(memberAddressesMapper::mapEntityToBean);
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on updateMemberAddress(): {}", this.className, error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberAddressesBean> deleteMemberAddress(Long memberId, Long id) {
        return memberAddressesRepository
                .findById(id)
                .flatMap(address -> {
                    if (address == null) {
                        logger.error("[{}] error on deleteMemberAddress(): MemberAddresses with ID: {} not found", this.className, id);
                        return Uni.createFrom().failure(new PreviRuntimeException("Invalid request: address with ID: " + id + " not found"));
                    }

                    if (memberId.intValue() != address.getMemberId()) {
                        logger.error("[{}] error on deleteMemberAddress(): incorrect memberId expected: {} provided: {}", this.className, memberId, address.getMemberId());
                        return Uni.createFrom().failure(new PreviRuntimeException("Invalid request: incorrect memberId"));
                    }

                    return memberAddressesRepository
                            .deleteById(id)
                            .onItem()
                            .transformToUni(result -> {
                                if (!Boolean.TRUE.equals(result)) {
                                    logger.error("[{}] error on deleteMemberAddress(): MemberAddresses with ID: {} not deleted", this.className, id);
                                    return Uni.createFrom().failure(new PreviRuntimeException("Error no delete address"));
                                }

                                return Uni.createFrom().item(memberAddressesMapper.mapEntityToBean(address));
                            });
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on deleteMemberAddress(): {}", this.className, error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }
}