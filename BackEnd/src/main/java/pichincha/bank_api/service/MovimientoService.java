package pichincha.bank_api.service;

import pichincha.bank_api.dto.MovimientoDTO;
import pichincha.bank_api.dto.ReportesDTO;

import java.util.Map;
import java.util.List;
import java.time.LocalDateTime;

public interface MovimientoService {
    List<MovimientoDTO> findAll();
    MovimientoDTO findById(Long id);
    MovimientoDTO create(MovimientoDTO movimientoDTO);
    MovimientoDTO update(Long id, MovimientoDTO movimientoDTO);
    List<ReportesDTO> generateReport(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    void delete(Long id);
}
