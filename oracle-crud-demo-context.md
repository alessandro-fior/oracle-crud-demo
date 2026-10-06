# Project Context – Oracle CRUD Demo

This repository hosts a small, self‑contained Spring Boot application that exposes a REST API to perform CRUD operations on a **Cliente** entity backed by an Oracle 26ai database. The goal is to provide a clean, demonstrable example that can be shown during a technical interview.

## High‑level architecture

```
Client (curl) → REST Controller → Service → Repository
        └─ JDBC → Oracle
```

* **No ORM** – we use plain JDBC (`NamedParameterJdbcTemplate`) to keep SQL visible.
* **Spring Boot 3.3** – removes boilerplate, auto‑configures the JDBC data source.
* **Docker** – an Oracle 18c XA container is spun up with `docker compose`.
* **Maven** – the only build tool required.

## Code layout

```
src/main/java/com/example/oraclecrud/
 ├─ OracleCrudDemoApplication.java      // Spring Boot bootstrap
 ├─ controller/ClienteController.java   // REST endpoints
 ├─ service/ ClienteService.java      // Business layer
 ├─ repository/ClienteRepository.java  // JDBC access
 └─ model/Cliente.java                 // POJO

src/main/resources/ application.properties // DB config if not using .env

sql/01_schema.sql   // create user
sql/02_tables.sql   // create table

docker-compose.yml // runs Oracle in Docker

README.md & howto.md   // documentation

.env                // example DB credentials
```

## How to spin it up

Follow the steps in **howto.md**:

1. `docker compose up -d`
2. Initialise the schema once via `sqlplus`.
3. Create `.env` with the credentials.
4. `mvn spring-boot:run`.
5. Use `curl` or Postman to exercise the endpoints.

## Why this structure works for interviews

* **Fast feedback loop** – a single `mvn spring-boot:run` starts the server.
* **Predictable behaviour** – direct JDBC shows how SQL → Java conversion works.
* **No external services** – everything runs locally under Docker.
* **Clear separation of concerns** – each layer has a single responsibility.

Feel free to refer to this file when explaining the design during a conversation.
