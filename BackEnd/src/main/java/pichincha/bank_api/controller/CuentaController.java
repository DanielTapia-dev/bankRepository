package pichincha.bank_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import pichincha.bank_api.dto.CuentaDTO;
import pichincha.bank_api.service.CuentaService;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> getAll() {
        return ResponseEntity.ok(cuentaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CuentaDTO> create(@Valid @RequestBody CuentaDTO cuentaDTO) {
        return ResponseEntity.ok(cuentaService.create(cuentaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> update(@PathVariable Long id, @Valid @RequestBody CuentaDTO cuentaDTO) {
        return ResponseEntity.ok(cuentaService.update(id, cuentaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuentaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}