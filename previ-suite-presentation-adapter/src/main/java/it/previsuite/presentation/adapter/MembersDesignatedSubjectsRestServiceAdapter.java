package it.previsuite.presentation.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberDesignatedSubjectsBean;
import it.previsuite.bean.exceptions.PreviWebApplicationException;
import it.previsuite.bean.request.MemberDesignatedSubjectsRequestBean;
import it.previsuite.bean.response.PreviResponse;
import it.previsuite.bean.response.PreviResponseList;
import it.previsuite.presentation.port.MembersDesignatedSubjectsRestService;
import it.previsuite.service.port.MemberDesignatedSubjectsManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MembersDesignatedSubjectsRestServiceAdapter extends AbstractServiceAdapter implements MembersDesignatedSubjectsRestService {
    private static final Logger logger = LoggerFactory.getLogger(MembersDesignatedSubjectsRestServiceAdapter.class);
    private final String className = MembersDesignatedSubjectsRestServiceAdapter.class.getName();
    private final MemberDesignatedSubjectsManager memberDesignatedSubjectsManager;
    private final JsonWebToken jwt;

    @Inject
    public MembersDesignatedSubjectsRestServiceAdapter(MemberDesignatedSubjectsManager memberDesignatedSubjectsManager, JsonWebToken jwt) {
        this.memberDesignatedSubjectsManager = memberDesignatedSubjectsManager;
        this.jwt = jwt;
    }

    @Override
    public Uni<PreviResponseList<MemberDesignatedSubjectsBean>> getMemberDesignatedSubjects() {
        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberDesignatedSubjectsManager
                        .getMemberDesignatedSubjects(memberId)
                        .onItem()
                        .transform(list -> {
                            PreviResponseList<MemberDesignatedSubjectsBean> response = new PreviResponseList<>();
                            response.setData(list);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method getMemberDesignatedSubjects()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberDesignatedSubjectsBean>> addMemberDesignatedSubject(MemberDesignatedSubjectsRequestBean request) {
        if (request == null) {
            logger.error("[{}] Invalid request on method addMemberDesignatedSubject(): request object can not be null", this.className);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: request object can not be null");
        }

        if (request.getBeneficiaryId() == null) {
            logger.error("[{}] Invalid request on method addMemberDesignatedSubject(): beneficiary is required", this.className);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: beneficiary is required");
        }

        if (request.getDistribution() == null) {
            logger.error("[{}] Invalid request on method addMemberDesignatedSubject(): distribution is required", this.className);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: distribution is required");
        }

        if (request.getOrdering() == null) {
            logger.error("[{}] Invalid request on method addMemberDesignatedSubject(): ordering is required", this.className);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: ordering is required");
        }

        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberDesignatedSubjectsManager
                        .addMemberDesignatedSubject(memberId, request)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberDesignatedSubjectsBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method addMemberDesignatedSubject()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberDesignatedSubjectsBean>> updateMemberDesignatedSubject(Long id, MemberDesignatedSubjectsRequestBean request) {
        if (request == null) {
            logger.error("[{}] Invalid request on method updateMemberDesignatedSubject(): request object can not be null", this.className);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: request object can not be null");
        }

        if (request.getBeneficiaryId() == null) {
            logger.error("[{}] Invalid request on method updateMemberDesignatedSubject(): beneficiary is required", this.className);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: beneficiary is required");
        }

        if (request.getDistribution() == null) {
            logger.error("[{}] Invalid request on method updateMemberDesignatedSubject(): distribution is required", this.className);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: distribution is required");
        }

        if (request.getOrdering() == null) {
            logger.error("[{}] Invalid request on method updateMemberDesignatedSubject(): ordering is required", this.className);
            throw new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: ordering is required");
        }

        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> {
                    MemberDesignatedSubjectsBean requestBean = mapper.convertValue(request, MemberDesignatedSubjectsBean.class);
                    requestBean.setId(id);

                    return memberDesignatedSubjectsManager
                            .updateMemberDesignatedSubject(memberId, requestBean)
                            .onItem()
                            .transform(bean -> {
                                PreviResponse<MemberDesignatedSubjectsBean> response = new PreviResponse<>();
                                response.setData(bean);
                                return response;
                            });
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method updateMemberDesignatedSubject()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberDesignatedSubjectsBean>> deleteMemberDesignatedSubject(Long id) {
        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberDesignatedSubjectsManager
                        .deleteMemberDesignatedSubject(memberId, id)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberDesignatedSubjectsBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method deleteMemberDesignatedSubject()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }
}