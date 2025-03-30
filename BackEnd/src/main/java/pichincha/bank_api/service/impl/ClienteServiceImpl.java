package pichincha.bank_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pichincha.bank_api.dto.ClienteDTO;
import pichincha.bank_api.exception.DuplicateResourceException;
import pichincha.bank_api.exception.ResourceNotFoundException;
import pichincha.bank_api.model.Cliente;
import pichincha.bank_api.repository.ClienteRepository;
import pichincha.bank_api.service.ClienteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setGenero(cliente.getGenero());
        dto.setEdad(cliente.getEdad());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setContrasena(cliente.getContrasena());
        dto.setEstado(cliente.getEstado());
        return dto;
    }

    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.getEstado());
        return cliente;
    }

    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO findById(Long id) {
        return clienteRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public ClienteDTO create(ClienteDTO clienteDTO) {
        if (clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion()).isPresent()) {
            throw new DuplicateResourceException("cliente", "identificación", clienteDTO.getIdentificacion());
        }

        Cliente cliente = toEntity(clienteDTO);
        cliente.setContrasena(passwordEncoder.encode(cliente.getContrasena()));
        return toDTO(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "ID", id));

        clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion()).ifPresent(otro -> {
            if (!otro.getId().equals(id)) {
                throw new DuplicateResourceException("cliente", "identificación", clienteDTO.getIdentificacion());
            }
        });

        existente.setNombre(clienteDTO.getNombre());
        existente.setGenero(clienteDTO.getGenero());
        existente.setEdad(clienteDTO.getEdad());
        existente.setIdentificacion(clienteDTO.getIdentificacion());
        existente.setDireccion(clienteDTO.getDireccion());
        existente.setTelefono(clienteDTO.getTelefono());
        existente.setEstado(clienteDTO.getEstado());

        if (clienteDTO.getContrasena() != null && !clienteDTO.getContrasena().trim().isEmpty()) {
            existente.setContrasena(passwordEncoder.encode(clienteDTO.getContrasena()));
        }

        return toDTO(clienteRepository.save(existente));
    }



    @Override
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "ID", id));
        clienteRepository.delete(cliente);
    }
}
