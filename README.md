# NHL-Explore
Exploring the public NHL APIs.

# Reference info

https://github.com/Zmalski/NHL-API-Reference

https://gitlab.com/dword4/nhlapi

# Dev Environment
## Postgres
Taking versions from https://hub.docker.com/_/postgres/

### Initial setup
- Create a directory in the project called `dbData`.
The database Docker container uses it as a volume, see `compose.yaml`.
- Run the Docker container: `docker compose up`
- Connect into the database Docker container: `docker exec -it general-db bash`
- Open psql cli: `psql -U postgres`
- Once inside psql create the user and database using the commands below.
```postgresql
create user nhlexplore with createdb login password 'localsecret';
create database nhlexplore with owner nhlexplore;
```

### Commands
Run development db in Docker: `docker compose up`

Connect terminal: `docker exec -it general-db bash`

Run psql when in terminal: `psql -U postgres`

Run sql script in terminal: `psql -U postgres -d nhlexplore -f /home/data.sql`