package it.previsuite.migrations;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.runtime.Startup;
import io.quarkus.vertx.VertxContextSupport;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import it.previsuite.model.DbMigrations;
import it.previsuite.repository.port.DbMigrationsRepository;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Startup
@Singleton
public class DbMigrationsValidator {
    private static final Logger logger = LoggerFactory.getLogger(DbMigrationsValidator.class);
    private final DbMigrationsRepository repository;

    @ConfigProperty(name = "project.db-migration.filename")
    protected String dbMigrationFilename;

    @Inject
    public DbMigrationsValidator(DbMigrationsRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() throws Throwable {
        VertxContextSupport
                .subscribeAndAwait(() ->
                    this.validate()
                        .onItem()
                        .invoke(() -> logger.info("Database validation successful."))
                        .onFailure()
                        .invoke(failure -> {
                            logger.error("Database validation error", failure);
                            System.exit(1);
                        })
        );
    }

    protected Uni<Void> validate() {
        List<String> scriptNames = this.loadMigrationScripts();

        // Esegui le validazioni uno alla volta in sequenza
        return Multi
                .createFrom()
                .iterable(scriptNames)
                .onItem()
                .transformToUniAndConcatenate(this::checkMigrationPresent)
                .collect()
                .asList()
                .replaceWithVoid();
    }

    protected List<String> loadMigrationScripts() {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource(dbMigrationFilename);

            if (resourceUrl == null) {
                return Collections.emptyList();
            }

            InputStream resource = resourceUrl.openStream();
            return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8))
                    .lines()
                    .map(String::trim)
                    .filter(line -> ! line.isEmpty())
                    .toList();
        }
        catch (Exception e) {
            throw new IllegalStateException("Error reading file " + dbMigrationFilename, e);
        }
    }

    @WithTransaction
    protected Uni<Void> checkMigrationPresent(String scriptName) {
        return repository
                .find(DbMigrations.FIELDS.SCRIPT, scriptName)
                .firstResult()
                .onItem()
                .ifNull()
                .failWith(() -> new IllegalStateException("Missing migration on database: " + scriptName))
                .replaceWithVoid();
    }
}