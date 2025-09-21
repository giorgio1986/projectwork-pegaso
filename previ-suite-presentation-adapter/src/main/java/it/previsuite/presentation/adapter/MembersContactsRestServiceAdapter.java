package it.previsuite.presentation.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.MemberContactBean;
import it.previsuite.bean.exceptions.PreviWebApplicationException;
import it.previsuite.bean.response.PreviResponse;
import it.previsuite.presentation.port.MembersContactsRestService;
import it.previsuite.service.port.MemberContactsManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MembersContactsRestServiceAdapter extends AbstractServiceAdapter implements MembersContactsRestService {
    private static final Logger logger = LoggerFactory.getLogger(MembersContactsRestServiceAdapter.class);
    private final String className = MembersContactsRestServiceAdapter.class.getName();
    private final MemberContactsManager memberContactsManager;
    private final JsonWebToken jwt;

    @Inject
    public MembersContactsRestServiceAdapter(MemberContactsManager memberContactsManager, JsonWebToken jwt) {
        this.memberContactsManager = memberContactsManager;
        this.jwt = jwt;
    }

    @Override
    public Uni<PreviResponse<MemberContactBean>> getMemberContacts() {
        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberContactsManager
                        .getMemberContacts(memberId)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberContactBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method getMemberContacts()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberContactBean>> addMemberContacts(MemberContactBean request) {
        if (request == null) {
            logger.error("[{}] Invalid request on method addMemberContacts(): request object can not be null", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: request object can not be null"));
        }

        if (request.getEmail() == null) {
            logger.error("[{}] Invalid request on method addMemberContacts(): email is required", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: email is required"));
        }

        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberContactsManager
                        .addMemberContacts(memberId, request)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberContactBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method addMemberContacts()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<MemberContactBean>> updateMemberContacts(MemberContactBean request) {
        if (request == null) {
            logger.error("[{}] Invalid request on method updateMemberContacts(): request object can not be null", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: request object can not be null"));
        }

        if (request.getEmail() == null) {
            logger.error("[{}] Invalid request on method updateMemberContacts(): email is required", this.className);
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: email is required"));
        }

        return this
                .getMemberId(this.className, this.jwt)
                .flatMap(memberId -> memberContactsManager
                        .updateMemberContacts(memberId, request)
                        .onItem()
                        .transform(bean -> {
                            PreviResponse<MemberContactBean> response = new PreviResponse<>();
                            response.setData(bean);
                            return response;
                        })
                )
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[{}] Error on method updateMemberContacts()", this.className, error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }
}