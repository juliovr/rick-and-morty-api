package cl.julio.rickandmorty.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import cl.julio.rickandmorty.model.GetCharacterResponse;
import cl.julio.rickandmorty.service.ICharacterService;

@ExtendWith(MockitoExtension.class)
public class CharacterRestControllerTest {
    
    private static final int OK_200 = 200;
    private static final int RICK_ID = 1;
    private static final GetCharacterResponse RICK_CHARACTER_INFO = new GetCharacterResponse();
    
    static {
        RICK_CHARACTER_INFO.setId(RICK_ID);
        RICK_CHARACTER_INFO.setName("Rick");
        RICK_CHARACTER_INFO.setStatus("OK");
        RICK_CHARACTER_INFO.setSpecies("");
        RICK_CHARACTER_INFO.setType("tipo");
        RICK_CHARACTER_INFO.setEpisodeCount(50);
    }
    
    @Mock
    private ICharacterService characterService;
    
    private CharacterRestController restController;
    
    @BeforeEach
    public void setUp() {
        restController = new CharacterRestController(characterService);
    }
    
    @Test
    public void should_return_200_when_call_rest_controller() {
        ResponseEntity<GetCharacterResponse> response = restController.getCharacterInfo(RICK_ID);
        
        assertEquals(OK_200, response.getStatusCodeValue());
    }

    @Test
    public void should_return_exact_object_returned_from_service_in_the_response_body() {
        when(characterService.getCharacterInfo(RICK_ID)).thenReturn(RICK_CHARACTER_INFO);
        ResponseEntity<GetCharacterResponse> response = restController.getCharacterInfo(RICK_ID);
        
        verify(characterService).getCharacterInfo(RICK_ID);
        assertEquals(RICK_CHARACTER_INFO, response.getBody());
    }
    
}
