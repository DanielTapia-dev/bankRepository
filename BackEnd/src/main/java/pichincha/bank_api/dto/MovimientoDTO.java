package pichincha.bank_api.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MovimientoDTO {

    private Long id;

    @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime fecha;

    @NotNull(message = "El tipo de movimiento no puede ser nulo")
    private String tipoMovimiento;

    @NotNull(message = "El valor no puede ser nulo")
    private Double valor;

    @NotNull(message = "El saldo no puede ser nulo")
    private Double saldo;

    @NotNull(message = "El ID de la cuenta no puede ser nulo")
    private Long cuentaId;

    private String numeroCuenta;
    private Double saldoActual;
    private String clienteNombre;
    private Boolean estadoCuenta;

    public MovimientoDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public Double getSaldoActual() { return saldoActual; }
    public void setSaldoActual(Double saldoActual) { this.saldoActual = saldoActual; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public Boolean getEstadoCuenta() { return estadoCuenta; }
    public void setEstadoCuenta(Boolean estadoCuenta) { this.estadoCuenta = estadoCuenta; }
}
