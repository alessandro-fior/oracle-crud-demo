# Postman Todo Endpoints

Endpoint base: **`/api/clienti`**

| HTTP | URL | Example Body | Response | Notes |
|------|-----|---------------|----------|-------|
| **POST** | `/api/clienti` | `{"nome":"Mario","cognome":"Rossi","email":"m.rossi@example.com"}` | `201 Created` + created client JSON | Creates a new client |
| **GET** | `/api/clienti` | N/A | JSON array of clients | Retrieves all clients |
| **GET** | `/api/clienti/{id}` | N/A | Client JSON | Retrieves specific client by ID |
| **PUT** | `/api/clienti/{id}` | `{"nome":"Mario Updated","cognome":"Rossi","email":"m.rossi@example.com"}` | `204 No Content` | Updates client with given ID |
| **DELETE** | `/api/clienti/{id}` | N/A | `204 No Content` | Deletes client with given ID |
