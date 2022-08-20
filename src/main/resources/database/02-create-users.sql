--liquibase formatted sql
--changeset jkap:2
CREATE TABLE users (
  id bigint NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  enabled tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username (username)
);
