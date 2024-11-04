package ir.niopdc.station.exceptions;

public class AlreadyExistsException extends RuntimeException {
    private String meesage;

    public AlreadyExistsException(String msg) {
        super(msg);
        this.meesage = msg;
    }
}
