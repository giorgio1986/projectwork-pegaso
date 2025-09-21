package it.previsuite.service.adapter;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberBeneficiaryBaseBean;
import it.previsuite.bean.exceptions.PreviWebApplicationException;
import it.previsuite.bean.request.MemberBeneficiariesRequestBean;
import it.previsuite.model.MemberBeneficiary;
import it.previsuite.model.MemberDesignatedSubjects;
import it.previsuite.repository.port.MemberBeneficiariesRepository;
import it.previsuite.repository.port.MemberDesignatedSubjectsRepository;
import it.previsuite.service.mapper.MemberBeneficiaryMapper;
import it.previsuite.service.port.MemberBeneficiariesManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class MemberBeneficiariesManagerAdapter extends AbstractManagerAdapter implements MemberBeneficiariesManager {
    private static final Logger logger = LoggerFactory.getLogger(MemberBeneficiariesManagerAdapter.class);
    private final String className = MemberBeneficiariesManagerAdapter.class.getName();
    private final MemberBeneficiariesRepository memberBeneficiariesRepository;
    private final MemberDesignatedSubjectsRepository memberDesignatedSubjectsRepository;
    private final MemberBeneficiaryMapper memberBeneficiaryMapper;
    private static final String QUERY = MemberBeneficiary.FIELDS.MEMBER_ID + " = ?1";

    @Inject
    public MemberBeneficiariesManagerAdapter(MemberBeneficiariesRepository memberBeneficiariesRepository
            , MemberDesignatedSubjectsRepository memberDesignatedSubjectsRepository
            , MemberBeneficiaryMapper memberBeneficiaryMapper
    ) {
        this.memberBeneficiariesRepository = memberBeneficiariesRepository;
        this.memberDesignatedSubjectsRepository = memberDesignatedSubjectsRepository;
        this.memberBeneficiaryMapper = memberBeneficiaryMapper;
    }

    @Override
    @WithTransaction
    public Uni<List<MemberBeneficiaryBaseBean>> getMemberBeneficiaries(Long memberId) {
        return memberBeneficiariesRepository
                .find(QUERY, memberId)
                .list()
                .onItem()
                .transform(memberBeneficiaryMapper::mapEntitiesToBeans)
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on getMemberBeneficiaries(): {}", this.className, error.getMessage());

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberBeneficiaryBaseBean> addMemberBeneficiary(Long memberId, MemberBeneficiariesRequestBean request) {
        MemberBeneficiaryBaseBean beneficiaryBean = this.mapper.convertValue(request, MemberBeneficiaryBaseBean.class);

        MemberBeneficiary address = memberBeneficiaryMapper.mapBeanToEntity(beneficiaryBean);
        address.setMemberId(memberId);
        address.setId(null);

        return memberBeneficiariesRepository
                .save(address)
                .onItem()
                .transform(memberBeneficiaryMapper::mapEntityToBean)
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on addMemberBeneficiary(): {}", this.className, error.getMessage());

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberBeneficiaryBaseBean> updateMemberBeneficiary(Long memberId, MemberBeneficiaryBaseBean request) {
        return memberBeneficiariesRepository
                .findById(request.getId())
                .flatMap(memberBeneficiary -> {
                    if (memberBeneficiary == null) {
                        logger.error("[{}] error on updateMemberBeneficiary(): MemberBeneficiary with ID: {} not found", this.className, request.getId());
                        return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: beneficiary with ID: " + request.getId() + " not found"));
                    }

                    if (memberId.intValue() != memberBeneficiary.getMemberId()) {
                        logger.error("[{}] error on updateMemberBeneficiary(): incorrect memberId expected: {} provided: {}", this.className, memberId, memberBeneficiary.getMemberId());
                        return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: incorrect memberId"));
                    }

                    MemberBeneficiary beneficiary = memberBeneficiaryMapper.mapBeanToEntity(request);
                    beneficiary.setMemberId(memberId);

                    return memberBeneficiariesRepository
                            .save(beneficiary)
                            .onItem()
                            .transform(memberBeneficiaryMapper::mapEntityToBean);
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on updateMemberBeneficiary(): {}", this.className, error.getMessage());

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberBeneficiaryBaseBean> deleteMemberBeneficiary(Long memberId, Long id) {
        final String query = MemberDesignatedSubjects.FIELDS.BENEFICIARY_ID + " = ?1 ";

        return memberBeneficiariesRepository
                .findById(id)
                .flatMap(memberBeneficiary -> {
                    if (memberBeneficiary == null) {
                        logger.error("[{}] error on deleteMemberBeneficiary(): MemberBeneficiary with ID: {} not found", this.className, id);
                        return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: beneficiary with ID: " + id + " not found"));
                    }

                    if (memberId.intValue() != memberBeneficiary.getMemberId()) {
                        logger.error("[{}] error on deleteMemberBeneficiary(): incorrect memberId expected: {} provided: {}", this.className, memberId, memberBeneficiary.getMemberId());
                        return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: incorrect memberId"));
                    }

                    return memberDesignatedSubjectsRepository
                            .find(query, id)
                            .firstResult()
                            .onItem()
                            .transformToUni(designatedSubject -> {
                                if(designatedSubject != null) {
                                    logger.error("[{}] error on deleteMemberBeneficiary(): Beneficiary with ID: {} is still designated", this.className, id);
                                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.CONFLICT, "Cannot delete beneficiary: still designated"));
                                }

                                return memberBeneficiariesRepository
                                        .deleteById(id)
                                        .onItem()
                                        .transformToUni(result -> {
                                            if (!Boolean.TRUE.equals(result)) {
                                                logger.error("[{}] error on deleteMemberBeneficiary(): MemberBeneficiary with ID: {} not deleted", this.className, id);
                                                return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, "Error no delete beneficiary"));
                                            }

                                            return Uni.createFrom().item(memberBeneficiaryMapper.mapEntityToBean(memberBeneficiary));
                                        });
                            });
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on deleteMemberBeneficiary(): {}", this.className, error.getMessage());

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }
}