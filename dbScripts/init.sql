DO
$do$
BEGIN
    IF NOT EXISTS ( SELECT FROM pg_roles  WHERE  rolname = 'nhlexplore') THEN
        create user nhlexplore with createdb login password 'localsecret';
    END IF;
--     This doesn't work, you can't create a db inside a function.
--     IF NOT EXISTS ( SELECT FROM pg_database  WHERE  datname = 'nhlexplore') THEN
--            create database nhlexplore with owner nhlexplore;
--     END IF;
END
$do$;