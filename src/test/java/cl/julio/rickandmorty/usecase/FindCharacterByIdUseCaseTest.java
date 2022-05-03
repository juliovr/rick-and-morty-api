package cl.julio.rickandmorty.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import cl.julio.rickandmorty.infrastructure.gateway.model.ApiLocation;
import cl.julio.rickandmorty.infrastructure.gateway.model.ApiCharacterInfo;
import cl.julio.rickandmorty.domain.port.output.CharacterGateway;
import cl.julio.rickandmorty.domain.usecase.FindCharacterByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.julio.rickandmorty.delete.GetCharacterInfoException;
import cl.julio.rickandmorty.infrastructure.gateway.model.ApiNameUrl;
import cl.julio.rickandmorty.domain.model.CharacterInfo;
import cl.julio.rickandmorty.domain.model.Origin;
import cl.julio.rickandmorty.util.State;

@ExtendWith(MockitoExtension.class)
public class FindCharacterByIdUseCaseTest {

    private static final int RICK_ID = 1;
    private static final Origin EMPTY_ORIGIN = new Origin();
    private static final State<Origin> EMPTY_ORIGIN_STATE = new State<>();
        
    @Mock
    private CharacterGateway characterRepository;

    private FindCharacterByIdUseCase useCase;
    
    @BeforeEach
    public void setUp() {
        useCase = new FindCharacterByIdUseCase(characterRepository);
    }
    
    @Test
    public void origin_field_should_be_empty_if_character_has_no_origin_info() {
        // given
        State<CharacterInfo> expectedCharacterInfo = generarApiCharacterInfoOkSinOrigin();
//        when(characterRepository.getCharacterInfo(RICK_ID)).thenReturn(expectedCharacterInfo);

        // when
        CharacterInfo characterInfo = useCase.execute(RICK_ID);

        // then
        assertEquals(expectedCharacterInfo.getData().getId(), characterInfo.getId());
        assertEquals(expectedCharacterInfo.getData().getName(), characterInfo.getName());
        assertEquals(expectedCharacterInfo.getData().getStatus(), characterInfo.getStatus());
        assertEquals(expectedCharacterInfo.getData().getSpecies(), characterInfo.getSpecies());
        assertEquals(expectedCharacterInfo.getData().getType(), characterInfo.getType());
        assertEquals(expectedCharacterInfo.getData().getEpisodeCount(), characterInfo.getEpisodeCount());
        
        assertEquals(EMPTY_ORIGIN, characterInfo.getOrigin());
    }
    
    @Test
    public void should_throw_error_if_cannot_get_character_info() {
        // given
        State<CharacterInfo> responseCharacterInfoConError = generarApiCharacterInfoConError();
//        when(characterRepository.getCharacterInfo(RICK_ID)).thenReturn(responseCharacterInfoConError);

        // when
        GetCharacterInfoException exceptionThrew = assertThrowsExactly(GetCharacterInfoException.class, () -> {
            useCase.execute(RICK_ID);
        });

        // then
        String messageExpected = "Ocurrió un error obteniendo la información del personaje '1'";
        assertEquals(messageExpected, exceptionThrew.getMessage());
    }
    
    @Test
    public void should_throw_error_when_trying_to_obtain_origin_from_character_who_must_have_an_origin() {
        // given
//        State<CharacterInfo> responseCharacterInfo = generarApiCharacterInfoOkConOrigin();
//        when(characterRepository.getCharacterInfo(RICK_ID)).thenReturn(responseCharacterInfo);
        
//        Origin origin = responseCharacterInfo.getData().getOrigin();
//        when(characterRepository.getLocation(origin.getUrl())).thenReturn(EMPTY_ORIGIN_STATE);

        // when
//        GetCharacterInfoException exceptionThrew = assertThrowsExactly(GetCharacterInfoException.class, () -> {
//            useCase.execute(RICK_ID);
//        });
//
//        // then
//        String messageExpected = "Ocurrió un error obteniendo la información de origen del personaje '1'";
//        assertEquals(messageExpected, exceptionThrew.getMessage());
    }
    
    @Test
    public void should_get_character_with_info_and_origin() {
        // given
        State<CharacterInfo> responseCharacterInfo = generarApiCharacterInfoOkConOrigin();
//        when(characterRepository.getCharacterInfo(RICK_ID)).thenReturn(responseCharacterInfo);
        
        Origin origin = responseCharacterInfo.getData().getOrigin();
        
//        State<Origin> responseLocation = generarOriginConInfo();
//        when(characterRepository.getLocation(origin.getUrl())).thenReturn(responseLocation);
        
        // when
        CharacterInfo characterInfo = useCase.execute(RICK_ID);

        // then
        CharacterInfo expectedApiCharacterInfoInfo = responseCharacterInfo.getData();
        assertEquals(expectedApiCharacterInfoInfo.getId(), characterInfo.getId());
        assertEquals(expectedApiCharacterInfoInfo.getName(), characterInfo.getName());
        assertEquals(expectedApiCharacterInfoInfo.getStatus(), characterInfo.getStatus());
        assertEquals(expectedApiCharacterInfoInfo.getSpecies(), characterInfo.getSpecies());
        assertEquals(expectedApiCharacterInfoInfo.getType(), characterInfo.getType());
        assertEquals(expectedApiCharacterInfoInfo.getEpisodeCount(), characterInfo.getEpisodeCount());

//        Origin expectedApiLocation = responseLocation.getData();
//        assertEquals(expectedApiLocation.getName(), characterInfo.getOrigin().getName());
//        assertEquals(expectedApiLocation.getUrl(), characterInfo.getOrigin().getUrl());
//        assertEquals(expectedApiLocation.getDimension(), characterInfo.getOrigin().getDimension());
//        assertEquals(expectedApiLocation.getResidents(), characterInfo.getOrigin().getResidents());
    }
    
    
    private State<CharacterInfo> generarApiCharacterInfoOkSinOrigin() {
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setId(1);
        characterInfo.setName("nombre");
        characterInfo.setStatus("status");
        characterInfo.setSpecies("species");
        characterInfo.setType("type");
        characterInfo.setEpisodeCount(2);
        characterInfo.setOrigin(EMPTY_ORIGIN);

        State<CharacterInfo> stateOk = new State<>();
        stateOk.setData(characterInfo);
        
        return stateOk;
    }
    
    private State<CharacterInfo> generarApiCharacterInfoOkConOrigin() {
        Origin origin = generarOriginConInfo().getData();
        origin.setName("origin");
        origin.setUrl("url_origin");
        
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setId(1);
        characterInfo.setName("nombre");
        characterInfo.setStatus("status");
        characterInfo.setSpecies("species");
        characterInfo.setType("type");
        characterInfo.setOrigin(origin);
        characterInfo.setEpisodeCount(2);
        
        State<CharacterInfo> stateOk = new State<>();
        stateOk.setData(characterInfo);
        
        return stateOk;
    }
    
    private State<CharacterInfo> generarApiCharacterInfoConError() {
        State<CharacterInfo> stateError = new State<>();
        stateError.setErrorMessage("Error");
        
        return stateError;
    }
    
    private State<Origin> generarOriginConInfo() {
        Origin origin = new Origin();
        origin.setName("name");
        origin.setUrl("url");
        origin.setDimension("dimension");
        origin.setResidents(Arrays.asList("resident 1", "resident 2"));
        State<Origin> response = new State<>();
        response.setData(origin);
        
        return response;
    }
    
}
