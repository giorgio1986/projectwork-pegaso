import java.time.LocalDate
import java.util.regex.Matcher
import groovy.io.FileType

def codClient = project.properties['project.cod-client']
def baseDir = project.properties['project.baseDir']
def dbMigrationFilename = project.properties['project.db-migration.filename']
def pattern = ~/^V(?<date>\d{12})_+((?<client>[0-9a-zA-Z_\-#]+)\.)?[0-9a-zA-Z_\-#]+\.(sql|psql)$/
def migrationsDir = new File(pathBuilder(baseDir, "..", "db_migrations", "scripts", LocalDate.now().year.toString()))

File outputFile = new File(pathBuilder(baseDir, "src", "main", "resources", dbMigrationFilename))
Integer count = 0

if(! codClient) {
    println "project.cod-client not defined"
    System.exit(1)
}

if(! migrationsDir.exists() || ! migrationsDir.isDirectory()) {
    println "Directory not found: ${migrationsDir.absolutePath}"
    System.exit(1)
}

migrationsDir.eachFileRecurse(FileType.FILES) { file ->
    Matcher matcher = file.name =~ pattern

    if (matcher.matches()) {
        def scriptForClient = matcher.group(2)

        if(! scriptForClient || scriptForClient.equalsIgnoreCase(codClient)) {
            count++
            if(count > 1) {
                outputFile.append("\n$file.name")
            } else {
                outputFile.write(file.name)
            }
        }
    }
}

if(count == 0) {
    println "Nessun file di migrazione trovato per il cliente ${codClient}."
}
else {
    println "Migrazioni trovate per il cliente '${codClient}': ${count}"
}

String pathBuilder(String... pathItems) {
    return pathItems.join(File.separator)
}