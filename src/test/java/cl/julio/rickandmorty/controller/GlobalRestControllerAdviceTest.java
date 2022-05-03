package cl.julio.rickandmorty.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cl.julio.rickandmorty.infrastructure.controller.GlobalRestControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import cl.julio.rickandmorty.delete.DetalleResponse;

@ExtendWith(MockitoExtension.class)
public class GlobalRestControllerAdviceTest {
    
    private static final int ERROR_CODE = 502;
    private static final String ERROR_MESSAGE = "Error de prueba";
    
    private GlobalRestControllerAdvice restController;
    
    @BeforeEach
    public void setUp() {
        restController = new GlobalRestControllerAdvice();
    }
    
    @Test
    public void should_return_502() {
        // when
        Throwable e = new Throwable(ERROR_MESSAGE);
        ResponseEntity<DetalleResponse> errorResponse = restController.errorException(e);

        // then
        assertEquals(ERROR_CODE, errorResponse.getStatusCodeValue());
    }
    
    @Test
    public void should_return_object_in_response_with_code_message_and_detail_from_the_exception_throwed() {
        // when
        Throwable e = new Throwable(ERROR_MESSAGE);
        ResponseEntity<DetalleResponse> errorResponse = restController.errorException(e);

        // then
        assertEquals(ERROR_CODE, errorResponse.getBody().getCodigo());
        assertEquals(ERROR_MESSAGE, errorResponse.getBody().getMensaje());
        assertEquals(e.toString(), errorResponse.getBody().getDetalle());
    }
    
}
