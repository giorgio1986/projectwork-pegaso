#!/bin/bash
working_dir=$(basename "$PWD")

touch /tmp/application.properties

echo ext.quarkus.datasource.password=password >> /tmp/application.properties
echo ext.quarkus.datasource.username=edocusr >> /tmp/application.properties
echo ext.quarkus.datasource.jdbc.url=jdbc:postgresql://previ-suite-postgresql:5432/db_name >> /tmp/application.properties
echo ext.quarkus.http.limits.max-body-size=30M  >> /tmp/application.properties

java -XX:MaxRAMPercentage=80.0 -Duser.timezone=Europe/Rome -Duser.language=it -DExternalPropertiesConfigSource=/tmp/application.properties -jar ./quarkus-run.jar