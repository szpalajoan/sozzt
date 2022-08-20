--liquibase formatted sql
--changeset jkap:3
CREATE TABLE authorities (
  username varchar(50) NOT NULL,
  authority varchar(50) NOT NULL,
  UNIQUE KEY username_authority (username,authority),
  CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
)
