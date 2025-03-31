CREATE TABLE personas (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  genero VARCHAR(20) NOT NULL,
  edad INT NOT NULL,
  identificacion VARCHAR(50) UNIQUE NOT NULL,
  direccion VARCHAR(255) NOT NULL,
  telefono VARCHAR(20) NOT NULL
);

CREATE TABLE clientes (
  id INT PRIMARY KEY,
  contrasena VARCHAR(255) NOT NULL,
  estado BOOLEAN NOT NULL,
  CONSTRAINT fk_cliente_persona FOREIGN KEY (id) REFERENCES personas(id) ON DELETE CASCADE
);

CREATE TABLE cuentas (
  id SERIAL PRIMARY KEY,
  numero_cuenta VARCHAR(50) NOT NULL UNIQUE,
  tipo_cuenta VARCHAR(50) NOT NULL,
  saldo_inicial NUMERIC(12, 2) NOT NULL,
  estado BOOLEAN NOT NULL,
  cliente_id INT NOT NULL,
  CONSTRAINT fk_cuenta_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);

CREATE TABLE movimientos (
  id SERIAL PRIMARY KEY,
  fecha TIMESTAMP NOT NULL,
  tipo_movimiento VARCHAR(50) NOT NULL,
  valor NUMERIC(12, 2) NOT NULL,
  saldo NUMERIC(12, 2) NOT NULL,
  cuenta_id INT NOT NULL,
  CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (cuenta_id) REFERENCES cuentas(id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION actualizar_saldo_cuenta()
RETURNS TRIGGER AS $$
DECLARE
    saldo_old DOUBLE PRECISION;
    saldo_new DOUBLE PRECISION;
BEGIN
    IF TG_OP = 'INSERT' THEN
        SELECT saldo_inicial INTO saldo_new FROM cuentas WHERE id = NEW.cuenta_id;

        IF NEW.tipo_movimiento = 'Deposito' THEN
            UPDATE cuentas SET saldo_inicial = saldo_new + NEW.valor WHERE id = NEW.cuenta_id;

        ELSIF NEW.tipo_movimiento = 'Retiro' THEN
            IF saldo_new - NEW.valor < 0 THEN
                RAISE EXCEPTION 'No se puede realizar el retiro. Saldo insuficiente.';
            END IF;
            UPDATE cuentas SET saldo_inicial = saldo_new - NEW.valor WHERE id = NEW.cuenta_id;
        END IF;

    ELSIF TG_OP = 'UPDATE' THEN
        -- Si la cuenta no cambió, simplemente revertimos el saldo anterior y aplicamos el nuevo
        IF OLD.cuenta_id = NEW.cuenta_id THEN
            SELECT saldo_inicial INTO saldo_new FROM cuentas WHERE id = NEW.cuenta_id;

            IF OLD.tipo_movimiento = 'Deposito' THEN
                saldo_new := saldo_new - OLD.valor;
            ELSIF OLD.tipo_movimiento = 'Retiro' THEN
                saldo_new := saldo_new + OLD.valor;
            END IF;

            IF NEW.tipo_movimiento = 'Deposito' THEN
                saldo_new := saldo_new + NEW.valor;
            ELSIF NEW.tipo_movimiento = 'Retiro' THEN
                IF saldo_new - NEW.valor < 0 THEN
                    RAISE EXCEPTION 'No se puede realizar el retiro. Saldo insuficiente.';
                END IF;
                saldo_new := saldo_new - NEW.valor;
            END IF;

            UPDATE cuentas SET saldo_inicial = saldo_new WHERE id = NEW.cuenta_id;

        ELSE
            -- Primero revertimos en la cuenta antigua
            SELECT saldo_inicial INTO saldo_old FROM cuentas WHERE id = OLD.cuenta_id;

            IF OLD.tipo_movimiento = 'Deposito' THEN
                saldo_old := saldo_old - OLD.valor;
            ELSIF OLD.tipo_movimiento = 'Retiro' THEN
                saldo_old := saldo_old + OLD.valor;
            END IF;

            UPDATE cuentas SET saldo_inicial = saldo_old WHERE id = OLD.cuenta_id;

            -- Luego aplicamos en la cuenta nueva
            SELECT saldo_inicial INTO saldo_new FROM cuentas WHERE id = NEW.cuenta_id;

            IF NEW.tipo_movimiento = 'Deposito' THEN
                saldo_new := saldo_new + NEW.valor;
            ELSIF NEW.tipo_movimiento = 'Retiro' THEN
                IF saldo_new - NEW.valor < 0 THEN
                    RAISE EXCEPTION 'No se puede realizar el retiro. Saldo insuficiente.';
                END IF;
                saldo_new := saldo_new - NEW.valor;
            END IF;

            UPDATE cuentas SET saldo_inicial = saldo_new WHERE id = NEW.cuenta_id;
        END IF;

    ELSIF TG_OP = 'DELETE' THEN
        SELECT saldo_inicial INTO saldo_old FROM cuentas WHERE id = OLD.cuenta_id;

        IF OLD.tipo_movimiento = 'Deposito' THEN
            UPDATE cuentas SET saldo_inicial = saldo_old - OLD.valor WHERE id = OLD.cuenta_id;
        ELSIF OLD.tipo_movimiento = 'Retiro' THEN
            UPDATE cuentas SET saldo_inicial = saldo_old + OLD.valor WHERE id = OLD.cuenta_id;
        END IF;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


DROP TRIGGER IF EXISTS trigger_actualizar_saldo ON movimientos;

CREATE TRIGGER trigger_actualizar_saldo
AFTER INSERT OR UPDATE OR DELETE ON movimientos
FOR EACH ROW
EXECUTE FUNCTION actualizar_saldo_cuenta();


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


