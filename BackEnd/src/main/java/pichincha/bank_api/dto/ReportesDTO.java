package pichincha.bank_api.dto;

public class ReportesDTO {

    private String nombreCliente;
    private String numeroCuenta;
    private Double saldoInicial;
    private Double totalCreditos;
    private Double totalDebitos;

    public ReportesDTO() {
    }

    public ReportesDTO(String nombreCliente, String numeroCuenta, Double saldoInicial, Double totalCreditos, Double totalDebitos) {
        this.nombreCliente = nombreCliente;
        this.numeroCuenta = numeroCuenta;
        this.saldoInicial = saldoInicial;
        this.totalCreditos = totalCreditos;
        this.totalDebitos = totalDebitos;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Double getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(Double totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public Double getTotalDebitos() {
        return totalDebitos;
    }

    public void setTotalDebitos(Double totalDebitos) {
        this.totalDebitos = totalDebitos;
    }
}
