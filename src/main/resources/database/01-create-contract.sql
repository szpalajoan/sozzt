--liquibase formatted sql
--changeset jkap:1
CREATE TABLE contract (
  id bigint NOT NULL AUTO_INCREMENT,
  invoice_number varchar(400) NOT NULL,
  location varchar(400) DEFAULT NULL,
  executive varchar(2000) DEFAULT NULL,
  created timestamp NULL DEFAULT NULL,
  contract_step varchar(200) DEFAULT NULL,
  is_scan_from_tauron_upload tinyint DEFAULT NULL,
  is_preliminary_map_upload tinyint DEFAULT NULL,
  PRIMARY KEY (id))
