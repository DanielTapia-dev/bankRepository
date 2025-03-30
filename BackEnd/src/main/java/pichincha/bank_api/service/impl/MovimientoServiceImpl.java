package pichincha.bank_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pichincha.bank_api.dto.MovimientoDTO;
import pichincha.bank_api.model.Cuenta;
import pichincha.bank_api.model.Movimiento;
import pichincha.bank_api.repository.CuentaRepository;
import pichincha.bank_api.repository.MovimientoRepository;
import pichincha.bank_api.service.MovimientoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    private MovimientoDTO toDTO(Movimiento movimiento) {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setId(movimiento.getId());
        dto.setFecha(movimiento.getFecha());
        dto.setTipoMovimiento(movimiento.getTipoMovimiento());
        dto.setValor(movimiento.getValor());
        dto.setSaldo(movimiento.getSaldo());
        dto.setCuentaId(movimiento.getCuenta().getId());
        return dto;
    }

    private Movimiento toEntity(MovimientoDTO dto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(dto.getId());
        movimiento.setFecha(dto.getFecha());
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setValor(dto.getValor());
        movimiento.setSaldo(dto.getSaldo());

        Cuenta cuenta = cuentaRepository.findById(dto.getCuentaId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        movimiento.setCuenta(cuenta);

        return movimiento;
    }

    @Override
    public List<MovimientoDTO> findAll() {
        return movimientoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public MovimientoDTO findById(Long id) {
        return movimientoRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    @Override
    public MovimientoDTO create(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = toEntity(movimientoDTO);
        return toDTO(movimientoRepository.save(movimiento));
    }

    @Override
    public MovimientoDTO update(Long id, MovimientoDTO movimientoDTO) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        movimiento.setFecha(movimientoDTO.getFecha());
        movimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        movimiento.setValor(movimientoDTO.getValor());
        movimiento.setSaldo(movimientoDTO.getSaldo());

        Cuenta cuenta = cuentaRepository.findById(movimientoDTO.getCuentaId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        movimiento.setCuenta(cuenta);

        return toDTO(movimientoRepository.save(movimiento));
    }

    @Override
    public void delete(Long id) {
        movimientoRepository.deleteById(id);
    }
}