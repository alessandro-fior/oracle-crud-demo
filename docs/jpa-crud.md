# JPA CRUD Guide – Oracle‑CRUD‑Demo

## 1. Project structure

```
JPA/
├─ pom.xml
├─ src/main
│  ├─ java
│  │  └─ com
│  │     └─ example
│  │        └─ jpa
│  │           ├─ Application.java
│  │           ├─ Contact.java
│  │           ├─ ContactRepository.java
│  │           ├─ ContactService.java
│  │           └─ ContactController.java
│  └─ resources
│     └─ application.properties
└─ docs/jpa-crud.md
```

## 2. Dependencies

Using Spring Boot 3.3.5 with JPA, Oracle JDBC and web starter. Add the pom as shown in `pom.xml`.

## 3. Data source configuration

`src/main/resources/application.properties` binds to Oracle database via JDBC URL. Adjust `spring.datasource.*` values for your environment.

## 4. Entity

`Contact` maps to `CONTACT` table. Primary key `id` uses `IDENTITY` with Oracle‑specific sequence.

## 5. Repository

extends `JpaRepository` with an additional finder by email.

## 6. Service & controller

Standard CRUD endpoints at `GET /api/contacts`, `POST`, `PUT` and `DELETE`.

## 7. Running

```bash
cd JPA
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api/contacts`.

## 8. Testing

Use JUnit 5; a sample test can be created under `src/test/java/com/example/jpa`.

---

Happy coding!