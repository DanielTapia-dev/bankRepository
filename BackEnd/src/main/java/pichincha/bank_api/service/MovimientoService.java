package pichincha.bank_api.service;

import pichincha.bank_api.dto.MovimientoDTO;

import java.util.List;

public interface MovimientoService {
    List<MovimientoDTO> findAll();
    MovimientoDTO findById(Long id);
    MovimientoDTO create(MovimientoDTO movimientoDTO);
    MovimientoDTO update(Long id, MovimientoDTO movimientoDTO);
    void delete(Long id);
}
