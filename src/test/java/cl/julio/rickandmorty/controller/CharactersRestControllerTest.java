package cl.julio.rickandmorty.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.julio.rickandmorty.infrastructure.controller.CharactersRestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import cl.julio.rickandmorty.domain.model.CharacterInfo;
import cl.julio.rickandmorty.domain.port.input.FindCharacterById;

@ExtendWith(MockitoExtension.class)
public class CharactersRestControllerTest {
    
    private static final int OK_200 = 200;
    private static final int RICK_ID = 1;
    private static final CharacterInfo RICK_CHARACTER_INFO_INFO = new CharacterInfo();
    
    static {
        RICK_CHARACTER_INFO_INFO.setId(RICK_ID);
        RICK_CHARACTER_INFO_INFO.setName("Rick");
        RICK_CHARACTER_INFO_INFO.setStatus("OK");
        RICK_CHARACTER_INFO_INFO.setSpecies("");
        RICK_CHARACTER_INFO_INFO.setType("tipo");
        RICK_CHARACTER_INFO_INFO.setEpisodeCount(50);
    }
    
    @Mock
    private FindCharacterById characterService;
    
    private CharactersRestController restController;
    
    @BeforeEach
    public void setUp() {
        restController = new CharactersRestController(characterService);
    }
    
    @Test
    public void should_return_200_when_call_rest_controller() {
        // when
        ResponseEntity<CharacterInfo> response = restController.findById(RICK_ID);

        // then
        assertEquals(OK_200, response.getStatusCodeValue());
    }

    @Test
    public void should_return_exact_object_returned_from_service_in_the_response_body() {
        // given
        when(characterService.execute(RICK_ID)).thenReturn(RICK_CHARACTER_INFO_INFO);

        // when
        ResponseEntity<CharacterInfo> response = restController.findById(RICK_ID);

        // then
        verify(characterService).execute(RICK_ID);
        assertEquals(RICK_CHARACTER_INFO_INFO, response.getBody());
    }
    
}
