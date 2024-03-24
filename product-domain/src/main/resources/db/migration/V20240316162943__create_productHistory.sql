CREATE TABLE product_histories (
    product_history_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY ,
    product_id BIGINT NOT NULL ,
    name VARCHAR(51) NOT NULL,
    price INT NOT NULL,
    status VARCHAR(51) NOT NULL,
    created_by VARCHAR(51),
    created_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
