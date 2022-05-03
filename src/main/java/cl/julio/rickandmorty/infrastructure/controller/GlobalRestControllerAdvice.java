package cl.julio.rickandmorty.infrastructure.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.julio.rickandmorty.delete.DetalleResponse;

@RestControllerAdvice
@Slf4j
public class GlobalRestControllerAdvice {
    
    private static final int CODIGO_ERROR_GENERICO = 502;

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<DetalleResponse> errorException(Throwable e) {
        log.error("Error", e);
        DetalleResponse detalleResponse = new DetalleResponse(CODIGO_ERROR_GENERICO);
        detalleResponse.setMensaje(e.getMessage());
        detalleResponse.setDetalle(e.toString());

        return ResponseEntity.status(CODIGO_ERROR_GENERICO).body(detalleResponse);
    }
    
}
