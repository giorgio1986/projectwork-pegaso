FROM openjdk:21

# Change to root user to have write permissions
USER root

# Crea la cartella filesystem a cui verrà collegato il filesystem esterno
RUN mkdir -p /opt/filesystem

# Copy Quarkus App
COPY quarkus-app/ /app

# Change Working directory
WORKDIR /app

# Copy .sh
COPY startup.sh /app/startup.sh

# Imposta i permessi corretti per l'utente 2002
RUN chown -R 2002:0 /opt/filesystem \
	&& chmod -R 755 /app \
	&& chmod 0755 /app/startup.sh \
    && chown -R 2002:0 /app

# Set Current user 2002
USER 2002

ENTRYPOINT ["sh", "./startup.sh"]