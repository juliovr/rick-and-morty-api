package cl.julio.rickandmorty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.julio.rickandmorty.model.DetalleResponse;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
    
    private static final int CODIGO_ERROR_GENERICO = 500;

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<DetalleResponse> errorException(Throwable e) {
        DetalleResponse detalleResponse = new DetalleResponse(CODIGO_ERROR_GENERICO);
        detalleResponse.setMensaje(e.getMessage());
        detalleResponse.setDetalle(e.toString());

        return ResponseEntity.internalServerError().body(detalleResponse);
    }
    
}
