package ir.niopdc.fms.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

@ControllerAdvice
public class FuelExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(NotFoundException exc) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(),
                System.currentTimeMillis());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleException(AlreadyExistsException exc) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), exc.getMessage(),
                System.currentTimeMillis());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleException(IllegalArgumentException exc) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(),
                System.currentTimeMillis());
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected @ResponseBody ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exc,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {

        ErrorResponse error = new ErrorResponse();

        ArrayList<String> defaultMessages = new ArrayList<>();

        exc.getBindingResult().getFieldErrors().forEach(fieldError -> defaultMessages.add(fieldError.getDefaultMessage()));

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(defaultMessages.toString());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /*
    --------------------------------------------------------------------------------------
    Global error handler
    */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse  handleException(Exception exc) {

        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        if (exc.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
            return new ErrorResponse(HttpStatus.CONFLICT.value(), exc.getMessage(),
                    System.currentTimeMillis());
        }

        if (exc.getCause().getCause() instanceof IllegalArgumentException) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(),
                    System.currentTimeMillis());
        }

        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exc.getMessage(),
                System.currentTimeMillis());
        }


}
