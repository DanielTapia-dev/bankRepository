package pichincha.bank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pichincha.bank_api.model.Movimiento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    @Query(value = """
    SELECT 
        p.nombre AS clienteNombre,
        a.numero_cuenta AS numeroCuenta,
        a.saldo_inicial AS saldoInicial,
        SUM(CASE WHEN m.tipo_movimiento = 'Deposito' THEN m.valor ELSE 0 END) AS totalCreditos,
        SUM(CASE WHEN m.tipo_movimiento = 'Retiro' THEN m.valor ELSE 0 END) AS totalDebitos
    FROM movimientos m
    JOIN cuentas a ON a.id = m.cuenta_id
    JOIN clientes c ON c.id = a.cliente_id
    JOIN personas p ON p.id = c.id
    WHERE p.identificacion = CAST(:clienteId AS VARCHAR)
      AND m.fecha BETWEEN :fechaInicio AND :fechaFin
    GROUP BY p.nombre, a.numero_cuenta, a.saldo_inicial
""", nativeQuery = true)
    List<Map<String, Object>> getReport(@Param("clienteId") Long clienteId,
                                        @Param("fechaInicio") LocalDateTime fechaInicio,
                                        @Param("fechaFin") LocalDateTime fechaFin);
}