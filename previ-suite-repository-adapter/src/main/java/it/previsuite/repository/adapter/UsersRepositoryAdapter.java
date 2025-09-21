package it.previsuite.repository.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.exceptions.PreviSqlException;
import it.previsuite.model.Users;
import it.previsuite.repository.adapter.abstracts.AbstractPostgresqlRepository;
import it.previsuite.repository.port.UsersRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UsersRepositoryAdapter extends AbstractPostgresqlRepository<Users> implements UsersRepository {
    private static final Logger logger = LoggerFactory.getLogger(UsersRepositoryAdapter.class);

    @Override
    public Uni<Users> save(Users entity) {
        if(entity.getId() != null) {
            return this
                    .findById(entity.getId())
                    .onItem()
                    .ifNotNull()
                    .transformToUni(existing -> this.checkUpdate(entity, existing))
                    .onItem()
                    .ifNull()
                    .failWith(() -> new PreviSqlException(String.format("User with ID: %s not found", entity.getId())));
        }

        logger.error("[UsersRepositoryAdapter] error on save(): create not allowed");
        return Uni
                .createFrom()
                .failure(new PreviSqlException("create not allowed"));
    }

    private Uni<Users> checkUpdate(Users entity, Users existing) {
        if (entity.getPassword() != null && !existing.getPassword().equals(entity.getPassword())) {
            existing.setPassword(entity.getPassword());
        }

        if (entity.getState() != null && !existing.getState().equals(entity.getState())) {
            existing.setState(entity.getState());
        }

        if (entity.getLoginAttempts() != null && (existing.getLoginAttempts() == null || !existing.getLoginAttempts().equals(entity.getLoginAttempts()))) {
            existing.setLoginAttempts(entity.getLoginAttempts());
        }

        if (entity.getLastPasswordResetTimestamp() != null && (existing.getLastPasswordResetTimestamp() == null || !existing.getLastPasswordResetTimestamp().equals(entity.getLastPasswordResetTimestamp()))) {
            existing.setLastPasswordResetTimestamp(entity.getLastPasswordResetTimestamp());
        }

        if (entity.getLastAccessTimestamp() != null && (existing.getLastAccessTimestamp() == null || !existing.getLastAccessTimestamp().equals(entity.getLastAccessTimestamp()))) {
            existing.setLastAccessTimestamp(entity.getLastAccessTimestamp());
        }

        return this.persistAndFlush(existing);
    }
}