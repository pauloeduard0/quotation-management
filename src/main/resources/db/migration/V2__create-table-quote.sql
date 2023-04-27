CREATE TABLE quote (
    id VARCHAR(36) PRIMARY KEY,
    stock_quote_id VARCHAR(36),
    date DATE,
    value DECIMAL(10,2),
    FOREIGN KEY (stock_quote_id) REFERENCES stock_quote(id)
);
