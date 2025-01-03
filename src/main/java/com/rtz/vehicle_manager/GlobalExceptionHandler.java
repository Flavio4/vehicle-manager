package com.rtz.vehicle_manager;

import com.rtz.vehicle_manager.errors.BrandNotfoundException;
import com.rtz.vehicle_manager.errors.CarNotfoundException;
import com.rtz.vehicle_manager.errors.DuplicateBrandException;
import com.rtz.vehicle_manager.errors.ModelNotfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> body = getBody(ex, HttpStatus.BAD_REQUEST, "Bad Request", request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DuplicateBrandException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateBrandException(DuplicateBrandException ex) {
        Map<String, Object> body = getBody(ex, HttpStatus.BAD_REQUEST, "Marca duplicada");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = getBody(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(BrandNotfoundException.class)
    public ResponseEntity<Map<String, Object>> handleBrandNotFoundException(BrandNotfoundException ex) {
        Map<String, Object> body = getBody(ex, HttpStatus.NOT_FOUND, "Marca no encontrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ModelNotfoundException.class)
    public ResponseEntity<Map<String, Object>> handleModelNotFoundException(ModelNotfoundException ex) {
        Map<String, Object> body = getBody(ex, HttpStatus.NOT_FOUND, "Modelo no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(CarNotfoundException.class)
    public ResponseEntity<Map<String, Object>> handleBrandNotFoundException(CarNotfoundException ex) {
        Map<String, Object> body = getBody(ex, HttpStatus.NOT_FOUND, "Vehiculo no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // Handle empty body exceptions
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleEmptyBodyException(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", "El cuerpo de la solicitud no puede estar vacío o está mal formateado");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


    private Map<String, Object> getBody(Exception ex, HttpStatus status, String error) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", ex.getMessage());
        return body;
    }

    private Map<String, Object> getBody(Exception ex, HttpStatus status, String error, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));
        return body;
    }

}