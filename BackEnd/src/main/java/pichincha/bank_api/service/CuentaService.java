package pichincha.bank_api.service;

import pichincha.bank_api.dto.CuentaDTO;

import java.util.List;

public interface CuentaService {
    List<CuentaDTO> findAll();
    CuentaDTO findById(Long id);
    CuentaDTO create(CuentaDTO cuentaDTO);
    CuentaDTO update(Long id, CuentaDTO cuentaDTO);
    void delete(Long id);
}
