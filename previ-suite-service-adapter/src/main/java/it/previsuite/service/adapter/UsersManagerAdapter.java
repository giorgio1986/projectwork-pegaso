package it.previsuite.service.adapter;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import it.previsuite.bean.UserBean;
import it.previsuite.bean.commons.Roles;
import it.previsuite.bean.enums.UsersStateEnum;
import it.previsuite.bean.exceptions.PreviException;
import it.previsuite.bean.exceptions.PreviRuntimeException;
import it.previsuite.bean.request.LoginRequestBean;
import it.previsuite.bean.response.LoginResponseBean;
import it.previsuite.bean.utils.DateUtils;
import it.previsuite.model.Users;
import it.previsuite.repository.port.UsersRepository;
import it.previsuite.service.mapper.UsersMapper;
import it.previsuite.service.port.UsersManager;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@ApplicationScoped
public class UsersManagerAdapter implements UsersManager {
    private static final Logger logger = LoggerFactory.getLogger(UsersManagerAdapter.class);
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    @ConfigProperty(name = "project.application.name")
    protected String applicationName;

    @ConfigProperty(name = "project.user.max.attemps")
    protected Integer maxLoginAttempts;

    @Inject
    public UsersManagerAdapter(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    @PostConstruct
    public void init() {
        if (this.maxLoginAttempts == null) {
            this.maxLoginAttempts = 3; // default
        }
    }

    @Override
    @WithTransaction
    public Uni<LoginResponseBean> login(LoginRequestBean request) {
        final String query = Users.FIELDS.USERNAME + " = ?1";

        return usersRepository
                .find(query, request.getUsername().trim().toUpperCase())
                .firstResult()
                .flatMap(user -> {
                    if(UsersStateEnum.SUSPENDED.equals(user.getState())) {
                        return Uni.createFrom().failure(new PreviException("Account suspended."));
                    }

                    if(!UsersStateEnum.ACTIVE.equals(user.getState())) {
                        return Uni.createFrom().failure(new PreviException("Account not active."));
                    }

                    if(!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
                        user.setLoginAttempts(user.getLoginAttempts() + 1);

                        if (user.getLoginAttempts() >= this.maxLoginAttempts) {
                            user.setState(UsersStateEnum.SUSPENDED);
                        }

                        return usersRepository
                                .save(user)
                                .onItem()
                                .transformToUni(item -> Uni.createFrom().failure(new PreviException("Incorrect password.")));
                    }

                    // TODO aggiungere controllo scadenza password con redirect al cambio password
                    user.setLoginAttempts(0);
                    user.setLastAccessTimestamp(new Timestamp(System.currentTimeMillis()));

                    return usersRepository
                            .save(user)
                            .onItem()
                            .transform(updatedUser -> {
                                Duration jwtDuration = Duration.ofHours(1);
                                Instant expirationInstant = Instant.now().plus(jwtDuration);
                                Date expirationDate = Date.from(expirationInstant);

                                String token = Jwt
                                        .issuer(applicationName)
                                        .subject(updatedUser.getId().toString())
                                        .groups(Set.of(Roles.REGISTERED))
                                        .claim(Users.FIELDS.MEMBER_ID, updatedUser.getMemberId().toString())
                                        .expiresIn(jwtDuration)
                                        .sign();

                                LoginResponseBean response = new LoginResponseBean();
                                response.setAuthToken(token);
                                response.setAuthTokenExpirationDate(DateUtils.convert(expirationDate, DateUtils.ISO_ZONED_DATE_TIME));

                                return response;
                            });
                })
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[UserManagerAdapter] error on login(): {}", error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }

    @Override
    public Uni<Boolean> logout() {
        return Uni.createFrom().item(true);
    }

    @Override
    @WithTransaction
    public Uni<UserBean> getUser(Long userId) {
        final String query = "select u from Users u inner join fetch u." + Users.FIELDS.MEMBER + " where u." + Users.FIELDS.ID + " = ?1";
        return usersRepository
                .find(query, userId)
                .firstResult()
                .onItem()
                .transform(usersMapper::mapEntityToBean)
                .onFailure()
                .recoverWithUni(error -> {
                    logger.error("[UserManagerAdapter] error on getUser(): {}", error.getMessage());
                    return Uni.createFrom().failure(new PreviRuntimeException(error));
                });
    }
}