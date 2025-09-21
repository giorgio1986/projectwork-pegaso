# Projectwork
## Traccia 1.6 Sviluppo di una applicazione full-stack API-based per un’impresa del settore finanziario

### Prerequisiti per avvio progetto:
- Maven 3.9.9 o superiore
- Java 21
- Node 23 o superiore
- Postgresql 17 o superiore

### Operazioni preliminari:
dopo aver importato il progetto come progetto Maven dentro un IDE (io ho usato Intellij),
si devono selezionare i profili che sono suddivisi in 2 macrogruppi:
- profili che iniziano con **env-** identificano l'environment sul quale si vuole far partire l'applicativo
- profili che iniziano con **client-** identificano il cliente (attualmente è presente solo previgiorgio)

selezionare un profilo environment ad esempio **env-local** e un cliente ad esempio **client-previgiorgio**.

### Aggiunta nuovi clienti
Se si vuole aggiungere un nuovo cliente si deve modificare il file Pom.xml del modulo **previ-suite-root** che è il 
Pom Padre di tutto il progetto.
nella sezione **<profiles>** aggiungere:

```xml
<profile>
    <id>client-***</id>
    <properties>
        <project.cod-client>***</project.cod-client>
    </properties>
</profile>
```
sostituendo agli *** il nome del cliente.

### Configurazione database e certificati
1) una volta scelto l'environment dentro la cartella **conf** si deve creare un file copiando il file **build.properties.EMPTY** 
e sostituire **EMPTY** con il nome dell'environment selezionato ad esempio **build.properties.local**.

2) editare il file appena creato alle voci:
- ext.quarkus.datasource.reactive.url: indicando la stringa di connessione al db postgresql
- ext.quarkus.datasource.username: indicando l'username per la connessione al db che deve usare l'applicativo
- ext.quarkus.datasource.password: indicando la password per la connessione al db che deve usare l'applicativo

3) Opzionale si possono modificare anche le voci:
- ext.smallrye.jwt.public.key: specificando la chiave publica di un vostro certificato
- ext.smallrye.jwt.private.key: specificando la chiave privata di un vostro certificato
- ext.quarkus.http.port: per cambiare in numero di porta usato da Quarkus
- ext.user.max.attemps: per modificare il numero massimo di tentativi di accesso falliti dall'utente in fase di login

### Preparazione tabelle database
1) eseguire lo script che si trova nel percorso **../db_migrations/scripts/2025/create_db_migration_table.psql** in una console sql

Procedura manuale:

2) eseguire manualmente lo script  **../db_migrations/scripts/2025/V202507250900_create_db_tables.psql** in una console sql
3) eseguire l'insert seguente: 

```sql
INSERT INTO public.db_migrations (script) VALUES('V202507250900_create_db_tables.psql');
```

Procedura automatica

2) dentro il modulo **../db_migrations/** è presente il file **environments.conf** editarlo adando ad inserire i parametri di connessione del proprio database
3) lanciare lo script python con il comando:

```python
python migrate.py -m -y=2025
```
poi selezionare l'environment e lo script eseguirà tutti gli script di migrazione in automatico.

4) è presente anche uno script sql che non verrà eseguito che contiene dei dati di prova, se eseguito creerà l'utente:
- username: SCHGRG86E19C351R
- password: password

### Procedura avvio Backend
eseguire i seguenti comandi da console:
1) spostarsi nel percorso **../previ-suite-root/** ed eseguire il comando:

```console
maven clean package
```

2) per avviare Quarkus in modalità DEV

```console
maven quarkus:dev -P client-dev,env-local
```

### Procedura avvio Frontend
1) spostarsi nel percorso **../previ-suite-webapp/** ed eseguire i comandi:

```console
npm i -g pnpm
pnpm i
```

2) per avviare il frontend spostarsi nel percorso **../previ-suite-webapp/apps/webapp** ed eseguire il comando:

```console
pnpm dev
```