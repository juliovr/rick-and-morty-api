package cl.julio.rickandmorty.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.julio.rickandmorty.domain.characterinfo.GetCharacterInfoException;
import cl.julio.rickandmorty.model.GetCharacterResponse;
import cl.julio.rickandmorty.service.ICharacterService;

@RunWith(MockitoJUnitRunner.class)
public class CharacterRestControllerTest {

    private static final int RICK_ID = 1;
        
    @Mock
    private ICharacterService characterService;
    
    private CharacterRestController restController;
    
    @Before
    public void setUp() {
        restController = new CharacterRestController(characterService);
    }
    
    @Test
    public void should_return_200_when_call_rest_controller() {
        ResponseEntity<GetCharacterResponse> response = restController.getCharacterInfo(RICK_ID);
        
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
    
//    @Test
//    public void should_return_500_if_an_exception_is_thrown() {
//        when(characterService.getCharacterInfo(RICK_ID)).thenThrow(GetCharacterInfoException.class);
//        
//        ResponseEntity<GetCharacterResponse> response = restController.getCharacterInfo(RICK_ID);
//        
//        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
//    }
    
}
