package it.previsuite.repository.port.postgresql;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

public interface PostgresqlRepository<T> extends PanacheRepository<T> {
    Uni<T> save(T entity);
}