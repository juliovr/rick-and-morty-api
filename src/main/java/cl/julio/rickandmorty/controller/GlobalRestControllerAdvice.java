package cl.julio.rickandmorty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.julio.rickandmorty.model.DetalleResponse;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
    
    private static final int CODIGO_ERROR_GENERICO = 500;

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DetalleResponse errorException(Throwable e) {
        DetalleResponse detalleResponse = new DetalleResponse(CODIGO_ERROR_GENERICO);
        detalleResponse.setDetalle(e.toString());
        detalleResponse.setMensaje(e.getMessage());

        return detalleResponse;
    }
    
}
