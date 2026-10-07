# Guida rapida: Docker, Oracle XE e database demo

Questa guida descrive la configurazione attuale del progetto: container `oracle-demo`, immagine Oracle XE 21c, porta host `1521` e servizio `XEPDB1`.

## 1. Aprire il terminale nella cartella del progetto

Da CMD:

```cmd
cd /d C:\projects\oracle-crud-demo
```

## 2. Avviare Oracle

```cmd
docker compose up -d oracle
docker compose ps
docker logs -f oracle-demo
```

Aspettare `DATABASE IS READY TO USE!` e `Pluggable database XEPDB1 opened read write`. Uscire dalla visualizzazione dei log con `Ctrl+C`: il container rimane in esecuzione.

Per controllare in seguito se il container è avviato:

```cmd
docker ps --filter name=oracle-demo
```

## 3. Entrare in Oracle con SQL*Plus

```cmd
docker exec -it oracle-demo sqlplus system/demo_pass@XEPDB1
```

Credenziali amministrative confermate:

- utente: `SYSTEM`
- password: `demo_pass`
- servizio: `XEPDB1`

Al prompt `SQL>` si possono eseguire query SQL. Per uscire:

```sql
EXIT
```

Per aprire una shell Linux nel container, invece:

```cmd
docker exec -it oracle-demo bash
```

Uscire dalla shell con `exit`.

## 4. Collegarsi da DBeaver

- Database: Oracle
- Host: `localhost`
- Porta: `1521`
- Selezionare **Service name** e inserire `XEPDB1` (non SID)
- Utente amministrativo: `SYSTEM`
- Password: `demo_pass`

Per l'applicazione, lo script prepara l'utente `CLIENTE` con password `Cliente1234!`.

## 5. Creare/verificare l'utente e la tabella demo

In DBeaver, aprire `00_verifica_ripristino.sql` e usare **Esegui script** con la connessione `SYSTEM` a `XEPDB1`. Lo script crea l'utente `CLIENTE` e la tabella `CLIENTE.CLIENTE` solo se mancano; non elimina né sovrascrive i dati esistenti.

Poi collegarsi a DBeaver come `CLIENTE` / `Cliente1234!` e verificare:

```sql
SELECT COUNT(*) AS righe FROM cliente;
```

La URL JDBC per questo database è:

```text
jdbc:oracle:thin:@localhost:1521/XEPDB1
```

Se vuoi impostare o reimpostare la password manualmente, entra come `SYSTEM` e lancia:

```sql
ALTER USER CLIENTE IDENTIFIED BY "Cliente1234!" ACCOUNT UNLOCK;
```

## 6. Fermare Oracle e consultare i log

```cmd
docker compose stop oracle
docker compose start oracle
docker logs --tail 100 oracle-demo
```

`docker compose down` rimuove il container ma normalmente conserva il volume nominato; **non usare `docker compose down -v`** se vuoi conservare i dati del database.

## Nota sui dati

La password `ORACLE_PASSWORD` nel Compose viene usata all'inizializzazione iniziale del database. Cambiarla successivamente nel file Compose non modifica automaticamente la password memorizzata nel database.
