package it.previsuite.service.adapter;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberDesignatedSubjectsBean;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import it.previsuite.bean.exceptions.PreviWebApplicationException;
import it.previsuite.bean.request.MemberDesignatedSubjectsRequestBean;
import it.previsuite.model.MemberBeneficiary;
import it.previsuite.model.MemberDesignatedSubjects;
import it.previsuite.repository.port.MemberBeneficiariesRepository;
import it.previsuite.repository.port.MemberDesignatedSubjectsRepository;
import it.previsuite.service.mapper.MemberDesignatedSubjectsMapper;
import it.previsuite.service.port.MemberDesignatedSubjectsManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class MemberDesignatedSubjectsManagerAdapter extends AbstractManagerAdapter implements MemberDesignatedSubjectsManager {
    private static final Logger logger = LoggerFactory.getLogger(MemberDesignatedSubjectsManagerAdapter.class);
    private final String className = MemberDesignatedSubjectsManagerAdapter.class.getName();
    private final MemberDesignatedSubjectsRepository memberDesignatedSubjectsRepository;
    private final MemberBeneficiariesRepository memberBeneficiariesRepository;
    private final MemberDesignatedSubjectsMapper memberDesignatedSubjectsMapper;
    private static final String QUERY = MemberBeneficiary.FIELDS.ID + " = ?1 and " + MemberBeneficiary.FIELDS.MEMBER_ID + " = ?2";

    @Inject
    public MemberDesignatedSubjectsManagerAdapter(MemberDesignatedSubjectsRepository memberDesignatedSubjectsRepository
            , MemberBeneficiariesRepository memberBeneficiariesRepository
            , MemberDesignatedSubjectsMapper memberDesignatedSubjectsMapper
    ) {
        this.memberDesignatedSubjectsRepository = memberDesignatedSubjectsRepository;
        this.memberBeneficiariesRepository = memberBeneficiariesRepository;
        this.memberDesignatedSubjectsMapper = memberDesignatedSubjectsMapper;
    }

    @Override
    @WithTransaction
    public Uni<List<MemberDesignatedSubjectsBean>> getMemberDesignatedSubjects(Long memberId) {
        final String query = "select ds " +
                "from MemberDesignatedSubjects ds " +
                "inner join fetch ds." + MemberDesignatedSubjects.FIELDS.BENEFICIARY +
                " where ds." + MemberDesignatedSubjects.FIELDS.MEMBER_ID + " = ?1 " +
                "order by ds." + MemberDesignatedSubjects.FIELDS.ORDERING + ", ds." + MemberDesignatedSubjects.FIELDS.ID;

        return memberDesignatedSubjectsRepository
                .find(query, memberId)
                .list()
                .onItem()
                .transform(memberDesignatedSubjectsMapper::mapEntitiesToBeans)
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on getMemberDesignatedSubjects(): {}", this.className, error.getMessage());

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberDesignatedSubjectsBean> addMemberDesignatedSubject(Long memberId, MemberDesignatedSubjectsRequestBean request) {
        MemberDesignatedSubjectsBean designatedSubjectBean = this.mapper.convertValue(request, MemberDesignatedSubjectsBean.class);

        MemberDesignatedSubjects memberDesignatedSubject = memberDesignatedSubjectsMapper.mapBeanToEntity(designatedSubjectBean);
        memberDesignatedSubject.setMemberId(memberId);
        memberDesignatedSubject.setId(null);

        return memberBeneficiariesRepository
                .find(QUERY, memberDesignatedSubject.getBeneficiaryId(), memberDesignatedSubject.getMemberId())
                .firstResult()
                .flatMap(beneficiary ->
                    memberDesignatedSubjectsRepository
                        .save(memberDesignatedSubject)
                        .onItem()
                        .transform(designatedSubject -> {
                            designatedSubject.setBeneficiary(beneficiary);
                            return memberDesignatedSubjectsMapper.mapEntityToBean(designatedSubject);
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] error on addMemberDesignatedSubject(): {}", this.className, error.getMessage());

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }

    @Override
    @WithTransaction
    public Uni<MemberDesignatedSubjectsBean> updateMemberDesignatedSubject(Long memberId, MemberDesignatedSubjectsBean request) {
        return memberDesignatedSubjectsRepository
                .findById(request.getId())
                .flatMap(memberDesignatedSubject -> {
                    if (memberDesignatedSubject == null) {
                        logger.error("[{}] error on updateMemberDesignatedSubject(): DesignatedSubject with ID: {} not found", this.className, request.getId());
                        return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: designatedSubject with ID: " + request.getId() + " not found"));
                    }

                    if (memberId.intValue() != memberDesignatedSubject.getMemberId()) {
                        logger.error("[{}] error on updateMemberDesignatedSubject(): incorrect memberId expected: {} provided: {}", this.className, memberId, memberDesignatedSubject.getMemberId());
                        return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: incorrect memberId"));
                    }

                    return memberBeneficiariesRepository
                            .find(QUERY, request.getBeneficiaryId(), memberDesignatedSubject.getMemberId())
                            .firstResult()
                            .flatMap(beneficiary -> {
                                MemberDesignatedSubjects entity = memberDesignatedSubjectsMapper.mapBeanToEntity(request);
                                entity.setMemberId(memberId);

                                return memberDesignatedSubjectsRepository
                                        .save(entity)
                                        .onItem()
                                        .transform(designatedSubject -> {
                                            designatedSubject.setBeneficiary(beneficiary);
                                            return memberDesignatedSubjectsMapper.mapEntityToBean(designatedSubject);
                                        });
                            });
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
    public Uni<MemberDesignatedSubjectsBean> deleteMemberDesignatedSubject(Long memberId, Long id) {
        final String query = "select ds " +
                "from MemberDesignatedSubjects ds " +
                "inner join fetch ds." + MemberDesignatedSubjects.FIELDS.BENEFICIARY +
                " where ds." + MemberDesignatedSubjects.FIELDS.ID + " = ?1 ";

        return memberDesignatedSubjectsRepository
                .find(query, id)
                .firstResult()
                .flatMap(memberDesignatedSubject -> {
                    if (memberDesignatedSubject == null) {
                        logger.error("[{}] error on deleteMemberDesignatedSubject(): DesignatedSubject with ID: {} not found", this.className, id);
                        return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: beneficiary with ID: " + id + " not found"));
                    }

                    if (memberId.intValue() != memberDesignatedSubject.getMemberId()) {
                        logger.error("[{}] error on deleteMemberDesignatedSubject(): incorrect memberId expected: {} provided: {}", this.className, memberId, memberDesignatedSubject.getMemberId());
                        return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: incorrect memberId"));
                    }

                    return memberDesignatedSubjectsRepository
                            .deleteById(id)
                            .onItem()
                            .transformToUni(result -> {
                                if (!Boolean.TRUE.equals(result)) {
                                    logger.error("[{}] error on deleteMemberDesignatedSubject(): DesignatedSubject with ID: {} not deleted", this.className, id);
                                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, "Error no delete DesignatedSubject"));
                                }

                                return Uni.createFrom().item(memberDesignatedSubjectsMapper.mapEntityToBean(memberDesignatedSubject));
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