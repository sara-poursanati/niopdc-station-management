package ir.niopdc.fms.exceptions;

public class AlreadyExistsException extends RuntimeException {
    private String meesage;

    public AlreadyExistsException(String msg) {
        super(msg);
        this.meesage = msg;
    }
}
