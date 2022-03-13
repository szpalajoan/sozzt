CREATE TABLE ORDERS (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
invoice_number VARCHAR(400) not null,
location VARCHAR(400) null,
executive VARCHAR(2000) null,
created timestamp
);


