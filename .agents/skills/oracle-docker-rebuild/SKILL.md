---
name: oracle-docker-rebuild
description: Maintain Oracle demo SQL, test, and Docker artifacts so the database can be stopped, recreated, initialized, and verified from documented project files.
metadata:
  short-description: Keep Oracle demo rebuildable
---

# Oracle Docker rebuild workflow

Use this skill when changing the Oracle demo database, its SQL schema or seed data, its integration tests, Docker Compose configuration, or the instructions needed to restore a working demo after removing/recreating containers.

## Required outcome for each relevant iteration

Keep a reproducible path from an empty database container to the tested demo state. Update the artifacts in the same change as the implementation:

- SQL setup, schema, seed, migration, and verification scripts under `sql/` as appropriate.
- A concise operator guide under `sql/` with the actual Docker commands to start, inspect, enter, initialize, verify, stop, and recreate the container.
- Tests or verification queries that demonstrate the changed database behavior.
- Compose and application connection settings when service name, port, image, credentials, volumes, or initialization behavior changes.

Do not create redundant numbered scripts for every edit. Extend the existing sequence where possible and record the order of execution. Keep scripts safe to rerun where practical: detect existing users/objects or state clearly when a script is one-time. Never silently drop schemas, tables, volumes, or user data as part of a rebuild workflow.

## Workflow

1. Inspect the current `docker-compose.yml`, `.env` (do not expose secrets), application properties, SQL scripts, tests, and SQL documentation. Treat the running Docker configuration and checked-in files as potentially divergent; identify the intended source of truth before editing.
2. Record the actual image, container/service name, host-to-container port mapping, database service/PDB, username, and where persistent data lives. Keep JDBC URLs and Docker network hostnames appropriate to whether the client runs on the host or inside Compose.
3. For each database change, provide/update ordered SQL files that initialize a fresh database and checks that confirm the resulting objects/data. Include cleanup/reset SQL only when needed and make destructive behavior explicit and opt-in.
4. Keep a single obvious operator guide in `sql/` (for example `DOCKER_XE_GUIDA.md`) with copyable commands for the current shell/OS: project directory, `docker compose up`, readiness/log inspection, `docker exec`/SQL client entry, script execution, verification, and stop/recreate commands. Explain volume persistence and warn against `down -v` where it would delete data.
5. Ensure the setup credentials and JDBC URLs in Compose, SQL instructions, and app configuration agree. If they cannot agree because host and container paths differ, document both paths explicitly.
6. Update tests so they target the intended profile and database state. State prerequisites for integration tests, and provide a deterministic bootstrap path before they run. Do not claim tests passed unless they were run and passed.
7. Review the final instructions from a clean/recreated-container perspective: a user should be able to follow only the documented steps and reach the same schema needed by the app/tests.

## Safety and boundaries

- Creating or editing local project files is expected for this workflow.
- Do not run destructive Docker operations such as `docker compose down -v`, `docker volume rm`, or database/schema drops unless the user explicitly requested data removal and the target volume/schema is identified.
- Prefer preserving the current named volume when recreating a container. Explain that changing an image or environment variable does not necessarily reinitialize an existing database volume.
- Do not change credentials or connection targets silently. Keep secrets out of committed guide examples when the repository uses environment variables; use clearly marked local demo values only when they are already part of the project configuration.
- Do not ask the user to repeat configuration that is already available in the repository or current conversation. Ask only when the intended database/image/data-preservation choice cannot be determined.

## Validation

Validate the SQL syntax and relevant Docker Compose configuration when the required clients are available. If Docker cannot be accessed or tests require unavailable services, report that limitation and provide the exact command for the user to run. Confirm the documented sequence matches the actual files and active configuration; do not rely on container labels alone if the Compose contents have since changed.
