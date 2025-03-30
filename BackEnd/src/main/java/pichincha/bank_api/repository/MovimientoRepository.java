package pichincha.bank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pichincha.bank_api.model.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {}