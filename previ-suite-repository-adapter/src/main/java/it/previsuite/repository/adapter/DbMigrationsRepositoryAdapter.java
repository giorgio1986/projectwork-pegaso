package it.previsuite.repository.adapter;

import io.smallrye.mutiny.Uni;
import it.previsuite.bean.exceptions.PreviSqlException;
import it.previsuite.model.DbMigrations;
import it.previsuite.repository.adapter.abstracts.AbstractPostgresqlRepository;
import it.previsuite.repository.port.DbMigrationsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

@ApplicationScoped
public class DbMigrationsRepositoryAdapter extends AbstractPostgresqlRepository<DbMigrations> implements DbMigrationsRepository {
    private static final Logger logger = LoggerFactory.getLogger(DbMigrationsRepositoryAdapter.class);

    @Override
    public Uni<DbMigrations> save(DbMigrations entity) {
        if(entity.getId() != null) {
            logger.error("[DbMigrationsRepositoryAdapter] error on save(): update not allowed");
            return Uni
                    .createFrom()
                    .failure(new PreviSqlException("update not allowed"));
        }

        entity.setApplyTimestamp(new Timestamp(System.currentTimeMillis()));
        return this.persistAndFlush(entity);
    }
}