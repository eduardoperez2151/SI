package uy.edu.ucu.si.grupo8.obligatorio3.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import uy.edu.ucu.si.grupo8.obligatorio3.dtos.ResponseAPI;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;
import static uy.edu.ucu.si.grupo8.obligatorio3.dtos.ResponseAPI.error;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UnapprovedClientAuthenticationException.class)
    public ResponseEntity<ResponseAPI> handleUnapprovedClientAuthenticationException() throws IOException {
        final ResponseAPI responseAPI = error("Upss algo ocurrio!!!");
        return status(HttpStatus.UNAUTHORIZED).body(responseAPI);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseAPI> handleMethodArgumentTypeMismatchException() throws IOException {
        final ResponseAPI responseAPI = error("Error en la petici\u00F3n del servicio");
        return badRequest().body(responseAPI);
    }

    @ExceptionHandler({InvalidFormatException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ResponseAPI> handleHttpMessageNotReadableException() throws IOException {
        final ResponseAPI responseAPI = error("Error en el proceso de deserializacion");
        return badRequest().body(responseAPI);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ResponseAPI> handleHttpMediaTypeNotAcceptableException() throws IOException {
        final ResponseAPI responseAPI = error("Error generar la respuesta");
        return badRequest().body(responseAPI);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseAPI> handleGenericException() throws IOException {
        final ResponseAPI responseAPI = error("No se pudo recuperar la receta");
        return status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseAPI);
    }

}