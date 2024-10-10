package ir.niopdc.fms.exceptions;

public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }

    public NotFoundException() {
        super("Not found");
    }
}
