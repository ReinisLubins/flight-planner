--liquibase formatted sql

--changeset reinis:2

CREATE TABLE flight
(
    flight_id INT PRIMARY KEY,
    from_id VARCHAR(255) NOT NULL,
    to_id VARCHAR(255) NOT NULL,
    carrier VARCHAR(255) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    CONSTRAINT flight_from_id_fkey FOREIGN KEY (from_id) REFERENCES airport (airport),
    CONSTRAINT flight_to_id_fkey FOREIGN KEY (to_id) REFERENCES airport (airport)
);

--changeset reinis:3
CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT 1;



