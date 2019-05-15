DROP TABLE people IF EXISTS;
DROP TABLE ocorrencias IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

CREATE TABLE ocorrencias (
	id VARCHAR(255) NOT NULL PRIMARY KEY,
	descricao VARCHAR(25),
	data_criacao TIMESTAMP,
);