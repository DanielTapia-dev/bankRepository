package pichincha.bank_api.controller;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import pichincha.bank_api.dto.MovimientoDTO;
import pichincha.bank_api.dto.ReportesDTO;
import pichincha.bank_api.service.MovimientoService;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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

    @GetMapping("/reportes/json")
    public List<ReportesDTO> obtenerReporteJson(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin
    ) {
        return movimientoService.generateReport(clienteId, fechaInicio, fechaFin);
    }

    @GetMapping("/reportes/pdf")
    public ResponseEntity<Map<String, String>> obtenerReportePdf(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin
    ) {
        try {
            List<ReportesDTO> reporte = movimientoService.generateReport(clienteId, fechaInicio, fechaFin);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Reporte de Estado de Cuenta"));
            if (!reporte.isEmpty()) {
                String nombreCliente = reporte.get(0).getNombreCliente();
                document.add(new Paragraph("Cliente: " + nombreCliente));
            }
            document.add(new Paragraph("Rango: " + fechaInicio + " a " + fechaFin));
            document.add(Chunk.NEWLINE);

            for (ReportesDTO dto : reporte) {
                document.add(new Paragraph("Cuenta: " + dto.getNumeroCuenta()));
                document.add(new Paragraph("Saldo Inicial: " + dto.getSaldoInicial()));
                document.add(new Paragraph("Total Créditos: " + dto.getTotalCreditos()));
                document.add(new Paragraph("Total Débitos: " + dto.getTotalDebitos()));
                document.add(Chunk.NEWLINE);
            }

            document.close();

            String base64Pdf = Base64.getEncoder().encodeToString(out.toByteArray());
            Map<String, String> response = new HashMap<>();
            response.put("base64", base64Pdf);

            return ResponseEntity.ok(response);

        } catch (DocumentException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error generando el PDF"));
        }
    }
}
