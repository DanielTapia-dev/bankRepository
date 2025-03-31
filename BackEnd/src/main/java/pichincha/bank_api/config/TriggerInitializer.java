package pichincha.bank_api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TriggerInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createTrigger() {
        String sql = """
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
                        SELECT saldo_inicial INTO saldo_old FROM cuentas WHERE id = OLD.cuenta_id;

                        IF OLD.tipo_movimiento = 'Deposito' THEN
                            saldo_old := saldo_old - OLD.valor;
                        ELSIF OLD.tipo_movimiento = 'Retiro' THEN
                            saldo_old := saldo_old + OLD.valor;
                        END IF;

                        UPDATE cuentas SET saldo_inicial = saldo_old WHERE id = OLD.cuenta_id;

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
        """;

        jdbcTemplate.execute(sql);
    }
}
