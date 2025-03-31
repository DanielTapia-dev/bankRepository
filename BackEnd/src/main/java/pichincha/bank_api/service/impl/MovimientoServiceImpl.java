package pichincha.bank_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pichincha.bank_api.dto.MovimientoDTO;
import pichincha.bank_api.dto.ReportesDTO;
import pichincha.bank_api.exception.ResourceNotFoundException;
import pichincha.bank_api.model.Cuenta;
import pichincha.bank_api.model.Movimiento;
import pichincha.bank_api.repository.CuentaRepository;
import pichincha.bank_api.repository.MovimientoRepository;
import pichincha.bank_api.service.MovimientoService;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;
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
        dto.setNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        dto.setSaldoActual(movimiento.getCuenta().getSaldoInicial());
        dto.setClienteNombre(movimiento.getCuenta().getCliente().getNombre());
        dto.setEstadoCuenta(movimiento.getCuenta().getEstado());
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
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento", "ID", id));
        movimientoRepository.deleteById(id);
    }

    @Override
    public List<ReportesDTO> generateReport(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Map<String, Object>> resultados = movimientoRepository.getReport(clienteId, fechaInicio, fechaFin);
        return resultados.stream().map(r -> {
            ReportesDTO dto = new ReportesDTO();
            dto.setNombreCliente((String) r.get("clienteNombre"));
            dto.setNumeroCuenta((String) r.get("numeroCuenta"));
            dto.setSaldoInicial(((Number) r.get("saldoInicial")).doubleValue());
            dto.setTotalCreditos(((Number) r.get("totalCreditos")).doubleValue());
            dto.setTotalDebitos(((Number) r.get("totalDebitos")).doubleValue());
            return dto;
        }).collect(Collectors.toList());
    }

}