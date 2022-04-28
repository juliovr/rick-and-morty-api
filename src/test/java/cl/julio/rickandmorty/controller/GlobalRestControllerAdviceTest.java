package cl.julio.rickandmorty.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import cl.julio.rickandmorty.model.DetalleResponse;

@ExtendWith(MockitoExtension.class)
public class GlobalRestControllerAdviceTest {
    
    private static final int INTERNAL_SERVER_ERROR_CODE = 500;
    private static final String MENSAJE_ERROR = "Error de prueba";
    
    private GlobalRestControllerAdvice restController;
    
    @BeforeEach
    public void setUp() {
        restController = new GlobalRestControllerAdvice();
    }
    
    @Test
    public void should_return_500() {
        Throwable e = new Throwable(MENSAJE_ERROR);
        ResponseEntity<DetalleResponse> errorResponse = restController.errorException(e);
        
        assertEquals(INTERNAL_SERVER_ERROR_CODE, errorResponse.getStatusCodeValue());
    }
    
    @Test
    public void should_return_object_in_response_with_code_message_and_detail_from_the_exception_throwed() {
        Throwable e = new Throwable(MENSAJE_ERROR);
        ResponseEntity<DetalleResponse> errorResponse = restController.errorException(e);
        
        assertEquals(INTERNAL_SERVER_ERROR_CODE, errorResponse.getBody().getCodigo());
        assertEquals(MENSAJE_ERROR, errorResponse.getBody().getMensaje());
        assertEquals(e.toString(), errorResponse.getBody().getDetalle());
    }
    
}
