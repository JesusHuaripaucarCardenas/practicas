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