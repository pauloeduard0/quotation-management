CREATE TABLE quote (
id BINARY(16) not null,
stock_quote_id BINARY(16),
date DATE not null,
value DECIMAL not null,
primary key(id)
);