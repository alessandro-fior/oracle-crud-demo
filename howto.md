# Come verificare il demo Oracle CRUD

Questa procedura avvia l'istanza Oracle configurata in Docker Compose, crea/verifica lo schema e verifica gli endpoint REST.

## Prerequisiti

- JDK 25 (`java -version`)
- Maven 3.10 o successivo (`mvn -version`)
- Docker Desktop con Docker Compose (`docker compose version`)
- `curl` (su Windows è disponibile anche `curl.exe`)

## 1. Avviare Oracle

Dalla cartella del progetto:

```bash
docker compose up -d
docker compose ps
docker compose logs -f oracle
```

Attendere `DATABASE IS READY TO USE!`. La configurazione attuale usa Oracle XE nel container `oracle-demo`, porta host `1521`, servizio `XEPDB1`, utente amministrativo `SYSTEM` e password `demo_pass`. Per la guida Docker e il ripristino dello schema vedere `sql/DOCKER_XE_GUIDA.md` e `sql/00_verifica_ripristino.sql`.

Se Docker segnala che la porta `1521` è già occupata, lascia attivo il servizio che la usa e scegli una porta host libera. Per esempio in PowerShell:

```powershell
$env:ORACLE_PORT = "1522"
docker compose up -d
```

La porta interna Oracle resta `1521`; cambia solo la porta usata dal computer host. Nei comandi SQL e nell'URL JDBC sostituisci quindi `localhost:1521` con `localhost:1522`, per esempio `jdbc:oracle:thin:@localhost:1522/FREEPDB1`. In alternativa controlla il proprietario con `Get-NetTCPConnection -LocalPort 1521 -State Listen | Select-Object LocalAddress,LocalPort,OwningProcess` e `Get-Process -Id <PID>`.

## 2. Creare utente e tabella

Con SQLcl/SQL*Plus installato, creare l'utente collegandosi al PDB come amministratore:

```bash
sql system/oracle@localhost:1521/FREEPDB1 @sql/01_schema.sql
sql CLIENTE/oracle@localhost:1521/FREEPDB1 @sql/02_tables.sql
```

In alternativa, aprire una sessione SQL nel container con `docker exec -it <nome-container> sqlplus system/oracle@FREEPDB1` e poi eseguire i due script, uno alla volta, dal client locale.

Gli script non sono pensati per essere passati insieme al comando `sqlplus sys/... as sysdba`: il secondo script deve essere eseguito dopo aver effettuato la connessione come `CLIENTE`.

## 3. Configurare e avviare l'applicazione

La configurazione predefinita dell'applicazione usa `CLIENTE` / `Cliente1234!` su `localhost:1521/XEPDB1`. Maven non carica automaticamente `.env`. Se devi impostare esplicitamente le variabili nella sessione PowerShell, usa:

```powershell
$env:SPRING_DATASOURCE_USERNAME = "CLIENTE"
$env:SPRING_DATASOURCE_PASSWORD = "Cliente1234!"
$env:SPRING_DATASOURCE_URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1"
mvn spring-boot:run
```

Con Oracle XE avviato e lo script `sql/00_verifica_ripristino.sql` eseguito come `SYSTEM`, avvia direttamente `mvn spring-boot:run`. L'applicazione si autentica come `CLIENTE`, proprietario della tabella.

## 4. Verificare il CRUD

In un secondo terminale, creare un record:

```bash
curl.exe -i -X POST http://localhost:8080/api/clienti -H "Content-Type: application/json" -d "{\"nome\":\"Mario\",\"cognome\":\"Rossi\",\"email\":\"mario@example.com\",\"telefono\":\"+390123456\"}"
```

La risposta attesa è `201 Created` con l'oggetto creato e il suo `id`. Usare quell'ID nei passaggi successivi:

```bash
curl.exe -i http://localhost:8080/api/clienti
curl.exe -i http://localhost:8080/api/clienti/1
curl.exe -i -X PUT http://localhost:8080/api/clienti/1 -H "Content-Type: application/json" -d "{\"nome\":\"Mario\",\"cognome\":\"Rossi\",\"email\":\"nuovo@example.com\",\"telefono\":\"+390987654\"}"
curl.exe -i -X DELETE http://localhost:8080/api/clienti/1
```

Attese: `200 OK` per GET, `204 No Content` per PUT e DELETE. Un GET o DELETE con ID inesistente risponde `404 Not Found`.

## 5. Build e arresto

Verificare la compilazione con `mvn clean package`. Per fermare Oracle:

```bash
docker compose down
```

Per eliminare anche i dati persistenti si può aggiungere `-v` (`docker compose down -v`).
