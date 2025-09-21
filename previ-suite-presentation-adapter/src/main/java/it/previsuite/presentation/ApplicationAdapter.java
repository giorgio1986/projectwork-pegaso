package it.previsuite.presentation;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Startup
@ApplicationScoped
public class ApplicationAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationAdapter.class);

    @PostConstruct
    protected void printApplicationName() {
        // Ascii art Font Slant
        String appName = """
                    ___    ____  ____   _____                 _        \s
                   /   |  / __ \\/  _/  / ___/___  ______   __(_)_______\s
                  / /| | / /_/ // /    \\__ \\/ _ \\/ ___/ | / / / ___/ _ \\
                 / ___ |/ ____// /    ___/ /  __/ /   | |/ / / /__/  __/
                /_/  |_/_/   /___/   /____/\\___/_/    |___/_/\\___/\\___/
                """;

        String startTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        System.out.println(appName);
        logger.info("Service deployed at {}", startTime);
    }
}