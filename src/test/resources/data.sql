INSERT INTO department (name, description, phone_number)
VALUES ('FIN', 'finance', '2345678999')
ON CONFLICT (id) DO NOTHING;

INSERT INTO employee (first_name, name, email, phone_number, address, salary, status, start_date, end_date, department_id)
VALUES ('Pete', 'Parker', 'pete.parker@test.com', '1234567890', '123 Main St', 50000.0, 0, '2023-01-01', '2023-12-31', 1)

