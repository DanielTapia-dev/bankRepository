package pichincha.bank_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import pichincha.bank_api.dto.MovimientoDTO;
import pichincha.bank_api.service.MovimientoService;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getAll() {
        return ResponseEntity.ok(movimientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovimientoDTO> create(@Valid @RequestBody MovimientoDTO movimientoDTO) {
        return ResponseEntity.ok(movimientoService.create(movimientoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoDTO> update(@PathVariable Long id, @Valid @RequestBody MovimientoDTO movimientoDTO) {
        return ResponseEntity.ok(movimientoService.update(id, movimientoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
