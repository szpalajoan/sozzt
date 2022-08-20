--liquibase formatted sql
--changeset jkap:4
INSERT INTO users (id, username, password, enabled)
VALUES (1, 'test', '$10$upzXFsFUOClFRR69OMKF8eajGMRs0vhcSHqvNDKy9yfW45w7o9z6O', 1);

INSERT INTO authorities (username, authority)
VALUES ('test', 'ROLE_USER');
