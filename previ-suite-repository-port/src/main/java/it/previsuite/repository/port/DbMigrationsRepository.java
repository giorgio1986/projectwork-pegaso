package it.previsuite.repository.port;

import it.previsuite.model.DbMigrations;
import it.previsuite.repository.port.postgresql.PostgresqlRepository;

public interface DbMigrationsRepository extends PostgresqlRepository<DbMigrations> {

}