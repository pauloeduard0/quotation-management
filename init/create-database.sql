CREATE TABLE IF NOT EXISTS stock_quote (
id BINARY(16) NOT NULL,
stock_id VARCHAR(255) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS quote (
id BINARY(16) not null,
stock_quote_id BINARY(16),
date DATE not null,
value DECIMAL not null,
primary key(id)
);

CREATE DATABASE IF NOT EXISTS bootdb_test;

CREATE TABLE IF NOT EXISTS stock_quote (
id BINARY(16) NOT NULL,
stock_id VARCHAR(255) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS quote (
id BINARY(16) not null,
stock_quote_id BINARY(16),
date DATE not null,
value DECIMAL not null,
primary key(id)
);