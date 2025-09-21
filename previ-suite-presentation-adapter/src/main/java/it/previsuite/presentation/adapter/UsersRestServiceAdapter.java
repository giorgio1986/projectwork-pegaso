package it.previsuite.presentation.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.UserBean;
import it.previsuite.bean.exceptions.PreviWebApplicationException;
import it.previsuite.bean.request.LoginRequestBean;
import it.previsuite.bean.response.LoginResponseBean;
import it.previsuite.bean.response.PreviResponse;
import it.previsuite.bean.utils.StringUtils;
import it.previsuite.presentation.port.UsersRestService;
import it.previsuite.service.port.UsersManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UsersRestServiceAdapter implements UsersRestService {
    private static final Logger logger = LoggerFactory.getLogger(UsersRestServiceAdapter.class);
    private final UsersManager userManager;
    private final JsonWebToken jwt;

    @Inject
    public UsersRestServiceAdapter(UsersManager userManager, JsonWebToken jwt) {
        this.userManager = userManager;
        this.jwt = jwt;
    }

    @Override
    public Uni<PreviResponse<LoginResponseBean>> login(LoginRequestBean request) {
        if (request == null) {
            logger.error("[UserRestServiceAdapter] Invalid request on method login(): request object can not be null");
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: request object can not be null"));
        }

        if (StringUtils.isNullOrEmpty(request.getUsername())) {
            logger.error("[UserRestServiceAdapter] Invalid request on method login(): username is required");
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: username is required"));
        }

        if (StringUtils.isNullOrEmpty(request.getPassword())) {
            logger.error("[UserRestServiceAdapter] Invalid request on method login(): password is required");
            return Uni
                    .createFrom()
                    .failure(new PreviWebApplicationException(Response.Status.BAD_REQUEST, "Invalid request: password is required"));
        }

        return userManager
                .login(request)
                .onItem()
                .transform(bean -> {
                    PreviResponse<LoginResponseBean> response = new PreviResponse<>();
                    response.setData(bean);
                    return response;
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[UserRestServiceAdapter] Error on method login()", error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<Boolean>> logout() {
        return userManager
                .logout()
                .onItem()
                .transform(bean -> {
                    PreviResponse<Boolean> response = new PreviResponse<>();
                    response.setData(bean);
                    return response;
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[UserRestServiceAdapter] Error on method logout()", error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }

    @Override
    public Uni<PreviResponse<UserBean>> getUser() {
        return userManager
                .getUser(Long.parseLong(jwt.getSubject()))
                .onItem()
                .transform(bean -> {
                    PreviResponse<UserBean> response = new PreviResponse<>();
                    response.setData(bean);
                    return response;
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[UserRestServiceAdapter] Error on method getUser()", error);

                    if(error instanceof PreviWebApplicationException) {
                        return Uni.createFrom().failure(error);
                    }

                    return Uni.createFrom().failure(new PreviWebApplicationException(Response.Status.INTERNAL_SERVER_ERROR, error.getMessage()));
                });
    }
}