package id.achfajar.challenge7.exception;

import id.achfajar.challenge7.dto.response.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler()
    public ResponseEntity<?> notValid(ConstraintViolationException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }
        return ResponseHandler.generateResponse(errors.toString(),HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceWithIdNotFoundException(ResourceNotFoundException exception, WebRequest req) {
        return ResponseHandler.generateResponse(
                exception.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(ResourceEmptyException.class)
    public ResponseEntity<?> handleInvalidCustomerRequestException(ResourceEmptyException exception, WebRequest req) {
        return ResponseHandler.generateResponse(
                exception.getMessage(), HttpStatus.OK, null);
    }

    @ExceptionHandler(value = ExistingResourceFoundException.class)
    public ResponseEntity<?> handleExistingResourceFoundException(ExistingResourceFoundException ex){
        return ResponseHandler.generateResponse(
                ex.getMessage(), HttpStatus.BAD_REQUEST, ex.getData());
    }
}
