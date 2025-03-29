package pichincha.bank_api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String nombreRecurso, String campo, Object valor) {
        super(String.format("%s no encontrado con %s: '%s'", nombreRecurso, campo, valor));
    }
}
