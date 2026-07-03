CREATE TABLE practica (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dni INTEGER NOT NULL,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    date TIMESTAMP NOT NULL DEFAULT now(),
    active BOOLEAN NOT NULL DEFAULT true
);

INSERT INTO practica (dni, firstname, lastname, email)
VALUES 
    (12345678, 'Juan', 'Pérez', 'juan.perez@example.com'),
    (87654321, 'María', 'García', 'maria.garcia@example.com');

    CREATE TABLE IF NOT EXISTS producto (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio NUMERIC(10,2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT true
);

INSERT INTO producto (nombre, precio, stock, active) VALUES
('Laptop Lenovo IdeaPad', 2499.90, 15, true),
('Mouse Inalámbrico Logitech', 79.50, 50, true);