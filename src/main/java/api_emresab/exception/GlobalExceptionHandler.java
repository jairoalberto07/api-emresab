package api_emresab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> manejarErroresValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> manejarReglasDeNegocio(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error_inventario", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    } 
}