CREATE TABLE user_health (
    id BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    health_score INT
);
