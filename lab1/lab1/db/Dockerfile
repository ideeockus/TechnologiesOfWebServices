FROM postgres

ENV POSTGRES_USER itmo_user
ENV POSTGRES_PASSWORD pass
ENV POSTGRES_DB lab_db

COPY init_db.sql /docker-entrypoint-initdb.d/