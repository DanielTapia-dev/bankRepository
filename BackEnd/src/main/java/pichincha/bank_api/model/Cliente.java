package pichincha.bank_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "clientes")
public class Cliente extends Persona {

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;

    public Cliente() {}

    public Cliente(String contrasena, Boolean estado) {
        this.contrasena = contrasena;
        this.estado = estado;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
