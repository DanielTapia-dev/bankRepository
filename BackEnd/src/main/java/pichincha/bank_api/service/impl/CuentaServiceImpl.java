package pichincha.bank_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pichincha.bank_api.dto.CuentaDTO;
import pichincha.bank_api.model.Cliente;
import pichincha.bank_api.model.Cuenta;
import pichincha.bank_api.repository.ClienteRepository;
import pichincha.bank_api.repository.CuentaRepository;
import pichincha.bank_api.service.CuentaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private CuentaDTO toDTO(Cuenta cuenta) {
        CuentaDTO dto = new CuentaDTO();
        dto.setId(cuenta.getId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setTipoCuenta(cuenta.getTipoCuenta());
        dto.setSaldoInicial(cuenta.getSaldoInicial());
        dto.setEstado(cuenta.getEstado());
        dto.setClienteId(cuenta.getCliente().getId());
        return dto;
    }

    private Cuenta toEntity(CuentaDTO dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(dto.getId());
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cuenta.setCliente(cliente);

        return cuenta;
    }

    @Override
    public List<CuentaDTO> findAll() {
        return cuentaRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CuentaDTO findById(Long id) {
        return cuentaRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Override
    public CuentaDTO create(CuentaDTO cuentaDTO) {
        Cuenta cuenta = toEntity(cuentaDTO);
        return toDTO(cuentaRepository.save(cuenta));
    }

    @Override
    public CuentaDTO update(Long id, CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.getEstado());

        Cliente cliente = clienteRepository.findById(cuentaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cuenta.setCliente(cliente);

        return toDTO(cuentaRepository.save(cuenta));
    }

    @Override
    public void delete(Long id) {
        cuentaRepository.deleteById(id);
    }
}
