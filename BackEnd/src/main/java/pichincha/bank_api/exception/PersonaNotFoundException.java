package pichincha.bank_api.exception;

public class PersonaNotFoundException extends RuntimeException {

    public PersonaNotFoundException(Long id) {
        super("No se encontró ninguna persona con ID: " + id);
    }

    public PersonaNotFoundException(String mensaje) {
        super(mensaje);
    }
}