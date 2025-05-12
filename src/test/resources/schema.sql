DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;
CREATE TABLE IF NOT EXISTS department
(
    id           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name         VARCHAR(50) NOT NULL UNIQUE,
    description  VARCHAR(255),
    phone_number VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS employee
(
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name    VARCHAR(50)         NOT NULL,
    name          VARCHAR(50)         NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    phone_number  VARCHAR(15),
    address       VARCHAR(255),
    salary        NUMERIC(10, 2),
    status        VARCHAR(20),
    start_date    DATE,
    end_date      DATE,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES department (id)
);