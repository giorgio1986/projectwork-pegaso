package it.previsuite.presentation.provider;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class AppConfigProvider {
    private static final String VERSION_FILE_NAME = "version.txt";

    @ConfigProperty(name = "project.application.name")
    protected String applicationName;

    public String getApplicationName() {
        return applicationName;
    }
    public String getApplicationVersion() {
        Path currentDir = Paths.get("");
        Path filePath = currentDir.resolve(VERSION_FILE_NAME);

        try {
            return Files.readString(filePath)
                    .replace("\n", "")
                    .replace("\r", "");
        }
        catch (IOException e) {
            return "";
        }
    }
}