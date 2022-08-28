--liquibase formatted sql
--changeset jkap:5
CREATE TABLE consent (
        id BINARY(16) NOT NULL,
        id_contract BINARY(16) NOT NULL,
        location VARCHAR(255) NULL,
        contact VARCHAR(255) NULL,
        status VARCHAR(255) NULL,
        comment VARCHAR(255) NULL,
        PRIMARY KEY (`id`));