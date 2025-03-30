package pichincha.bank_api.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String resource, String field, Object value) {
        super(String.format("Ya existe un(a) %s con %s: '%s'", resource, field, value));
    }
}
