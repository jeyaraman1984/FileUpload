CREATE TABLE FILE_DTLS(
File_id  INT auto_increment PRIMARY KEY,
File_name VARCHAR(80),
uploaded TIMESTAMP,
customer_id int);

CREATE TABLE CUSTOMER(
customer_id INT auto_increment PRIMARY KEY,
first_name VARCHAR(80),
last_name VARCHAR(80),
address  VARCHAR(80),
File_id int
);

ALTER TABLE FILE_DTLS ADD FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id);