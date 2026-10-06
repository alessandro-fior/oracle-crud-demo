# Oracle CRUD Demo

This is a minimal Spring Boot application demonstrating CRUD operations on an **Oracle** database using **JDBC**. It exposes REST endpoints that can be exercised with `curl`.

## Project structure

- `src/main/java/...`: Java source code.
- `src/main/resources/application.properties`: DB configuration.
- `sql/`: SQL scripts to set up the schema and tables.
- `pom.xml`: Maven build file.


## Prerequisites

- Java 21
- Maven 3.10
- Oracle 26ai Free Edition
- Docker (optional: for running Oracle in a container)

## Running locally with Docker

1. **Start Oracle container**
   ```bash
   docker run --name freepdb1 -p 1521:1521 -e ORACLE_PWD=oracle -d wnameless/oracle-xe-11g
   ```

2. **Initialize schema** (run manually once)
   ```bash
   sqlplus sys/oracle as sysdba @sql/01_schema.sql @sql/02_tables.sql
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/clienti` | Create new client |
| GET    | `/api/clienti` | List all clients |
| GET    | `/api/clienti/{id}` | Get client by ID |
| PUT    | `/api/clienti/{id}` | Update client |
| DELETE | `/api/clienti/{id}` | Delete client |

## Example `curl` Commands

```bash
# Create
curl -X POST http://localhost:8080/api/clienti \
     -H "Content-Type: application/json" \
     -d '{"nome":"Mario","cognome":"Rossi","email":"mario@example.com","telefono":"+390123456"}'

# List
curl http://localhost:8080/api/clienti

# Get
curl http://localhost:8080/api/clienti/1

# Update
curl -X PUT http://localhost:8080/api/clienti/1 \
     -H "Content-Type: application/json" \
     -d '{"nome":"Mario","cognome":"Rossi","email":"new@example.com","telefono":"+390987654"}'

# Delete
curl -X DELETE http://localhost:8080/api/clienti/1
```

## Notes

- Update `application.properties` with your Oracle user credentials before running.
- The `RETURNING id` clause works with Oracle 12c+ to fetch generated primary key.
- No persistence code is committed; credentials should use environment variables.
