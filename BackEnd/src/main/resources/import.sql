
-- PERSONAS
INSERT INTO personas (id, nombre, direccion, telefono, identificacion, genero, edad)
VALUES
(1, 'Jose Lema', 'Otavalo sn y principal', '098254785', '1101', 'Masculino', 30),
(2, 'Marianela Montalvo', 'Amazonas y NNUU', '097548965', '1102', 'Femenino', 28),
(3, 'Juan Osorio', '13 junio y Equinoccial', '098874587', '1103', 'Masculino', 35);

INSERT INTO clientes (id, contrasena, estado)
VALUES
(1, '1234', true),
(2, '5678', true),
(3, '1245', true);


-- CLIENTES
INSERT INTO clientes (id, contrasena, estado)
VALUES
(1, '1234', true),
(2, '5678', true),
(3, '1245', true);

-- CUENTAS
INSERT INTO cuentas (id, numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id)
VALUES
(1, '478758', 'Ahorro', 2000, true, 1),
(2, '225487', 'Corriente', 100, true, 2),
(3, '495878', 'Ahorros', 0, true, 3),
(4, '496825', 'Ahorros', 540, true, 2),
(5, '585545', 'Corriente', 1000, true, 1);

-- MOVIMIENTOS
INSERT INTO movimientos (id, fecha, tipo_movimiento, valor, saldo, cuenta_id)
VALUES
(1, '2022-02-10T10:00:00', 'Deposito', 600, 700, 2),
(2, '2022-02-08T09:00:00', 'Retiro', 540, 0, 4),
(3, '2022-02-09T11:00:00', 'Deposito', 150, 150, 3),
(4, '2022-02-11T08:00:00', 'Retiro', 575, 1425, 1);


