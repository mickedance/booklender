package se.lexicon.micke.booklender.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import se.lexicon.micke.booklender.exception.response.MyExceptionResponse;


import java.net.BindException;


@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyExceptionResponse> generalException(Exception e){
        MyExceptionResponse response = new MyExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Internal error" );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MyExceptionResponse> illegalArgumentException(IllegalArgumentException e){
        MyExceptionResponse response = new MyExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder errors =  new StringBuilder();
        for(FieldError er: ex.getFieldErrors()){
            errors.append(er.getField());
            errors.append(" ");
            errors.append(er.getDefaultMessage());
            errors.append("; ");
        }
        MyExceptionResponse response = new MyExceptionResponse(HttpStatus.BAD_REQUEST.value(), errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
