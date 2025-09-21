package it.previsuite.presentation.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberAddressesBean;
import it.previsuite.bean.exceptions.PreviWebApplicationException;
import it.previsuite.bean.request.MemberAddressesRequestBean;
import it.previsuite.bean.response.PreviResponse;
import it.previsuite.bean.response.PreviResponseList;
import it.previsuite.presentation.port.MembersAddressesRestService;
import it.previsuite.service.port.MemberAddressesManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MembersAddressesRestServiceAdapter extends AbstractServiceAdapter implements MembersAddressesRestService {
    private static final Logger logger = LoggerFactory.getLogger(MembersAddressesRestServiceAdapter.class);
    private final String className = MembersAddressesRestServiceAdapter.class.getName();
    private final MemberAddressesManager memberAddressesManager;
    private final JsonWebToken jwt;

    @Inject
    public MembersAddressesRestServiceAdapter(MemberAddressesManager memberAddressesManager, JsonWebToken jwt) {
        this.memberAddressesManager = memberAddressesManager;
        this.jwt = jwt;
    }

    @Override
    public Uni<PreviResponseList<MemberAddressesBean>> getMemberAddresses() {
        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberAddressesManager
                        .getMemberAddresses(memberId)
                        .onItem()
                        .transform(list -> {
                            PreviResponseList<MemberAddressesBean> response = new PreviResponseList<>();
                            response.setData(list);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method getMemberAddresses()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberAddressesBean>> addMemberAddress(MemberAddressesRequestBean request) {
        if (request == null) {
            logger.error("[{}] Invalid request on method addMemberAddress(): request object can not be null", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: request object can not be null"));
        }

        if (request.getAddressType() == null) {
            logger.error("[{}] Invalid request on method addMemberAddress(): addressType is required", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: addressType is required"));
        }

        if (request.getNation() == null) {
            logger.error("[{}] Invalid request on method addMemberAddress(): nation is required", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: nation is required"));
        }

        if (request.getProvince() == null) {
            logger.error("[{}] Invalid request on method addMemberAddress(): province is required", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: province is required"));
        }

        if (request.getLocation() == null) {
            logger.error("[{}] Invalid request on method addMemberAddress(): location is required", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: location is required"));
        }

        if (request.getAddress() == null) {
            logger.error("[{}] Invalid request on method addMemberAddress(): address is required", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: address is required"));
        }

        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberAddressesManager
                        .addMemberAddress(memberId, request)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberAddressesBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method addMemberAddresses()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberAddressesBean>> updateMemberAddress(Long id, MemberAddressesRequestBean request) {
        if (request == null) {
            logger.error("[{}] Invalid request on method updateMemberAddress(): request object can not be null", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: request object can not be null"));
        }

        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> {
                    MemberAddressesBean requestBean = mapper.convertValue(request, MemberAddressesBean.class);
                    requestBean.setId(id);

                    return memberAddressesManager
                            .updateMemberAddress(memberId, requestBean)
                            .onItem()
                            .transform(bean -> {
                                PreviResponse<MemberAddressesBean> response = new PreviResponse<>();
                                response.setData(bean);
                                return response;
                            });
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method updateMemberAddress()", this.className,  error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberAddressesBean>> deleteMemberAddress(Long id) {
        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberAddressesManager
                        .deleteMemberAddress(memberId, id)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberAddressesBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method deleteMemberAddress()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }
}