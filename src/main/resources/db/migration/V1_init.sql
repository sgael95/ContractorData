CREATE TABLE IF NOT EXISTS employee(
    employee_UUID UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    home_address VARCHAR(100),
    phone_number VARCHAR(10)
);