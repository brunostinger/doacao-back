package com.cp5a.doacao.exception;
import com.cp5a.doacao.dto.commondto.ErrorDTO;
import com.cp5a.doacao.dto.commondto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.ArrayList;

@ControllerAdvice
public class ResponseExceptionHandler{
    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<Object> handleFieldValidationExceptions(FieldValidationException exception, WebRequest webRequest){
        ErrorDTO error = new ErrorDTO(exception.getField(),exception.getDefaultMessage());
        ArrayList<ErrorDTO> errors = new ArrayList<>();
        errors.add(error);
        ResponseDTO response = new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), errors);
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<Object> handleInvalidAccessExceptions(InvalidAccessException exception, WebRequest webRequest){
        ErrorDTO error = new ErrorDTO(exception.getField(),exception.getDefaultMessage());
        ArrayList<ErrorDTO> errors = new ArrayList<>();
        errors.add(error);
        ResponseDTO response = new ResponseDTO(HttpStatus.UNAUTHORIZED.toString(), errors);
        return new ResponseEntity(response,HttpStatus.UNAUTHORIZED);
    }
}
