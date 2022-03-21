package cl.julio.rickandmorty.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import cl.julio.rickandmorty.model.GetCharacterResponse;
import cl.julio.rickandmorty.repository.CharacterRepository;
import cl.julio.rickandmorty.repository.api.ApiGetSingleCharacter;
import cl.julio.rickandmorty.repository.api.ApiLocation;
import cl.julio.rickandmorty.repository.api.ApiNameUrl;
import cl.julio.rickandmorty.util.Estado;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    private CharacterService characterService;
    
    @Before
    public void setUp() {
        characterService = new CharacterService(characterRepository);
    }
    
    private Estado<ApiGetSingleCharacter> generarApiCharacterInfoOkSinOrigin() {
        ApiGetSingleCharacter apiCharacterInfo = new ApiGetSingleCharacter();
        apiCharacterInfo.setId(1);
        apiCharacterInfo.setName("nombre");
        apiCharacterInfo.setStatus("status");
        apiCharacterInfo.setSpecies("species");
        apiCharacterInfo.setType("type");
        apiCharacterInfo.setOrigin(new ApiNameUrl());
        apiCharacterInfo.setLocation(new ApiNameUrl());
        apiCharacterInfo.setEpisode(Arrays.asList("episode 1", "episode 2"));
        
        Estado<ApiGetSingleCharacter> estadoOk = new Estado<>();
        estadoOk.setData(apiCharacterInfo);
        
        return estadoOk;
    }
    
    private Estado<ApiGetSingleCharacter> generarApiCharacterInfoOkConOrigin() {
        ApiNameUrl origin = new ApiNameUrl();
        origin.setName("origin");
        origin.setUrl("url_origin");
        
        ApiGetSingleCharacter apiCharacterInfo = new ApiGetSingleCharacter();
        apiCharacterInfo.setId(1);
        apiCharacterInfo.setName("nombre");
        apiCharacterInfo.setStatus("status");
        apiCharacterInfo.setSpecies("species");
        apiCharacterInfo.setType("type");
        apiCharacterInfo.setOrigin(origin);
        apiCharacterInfo.setLocation(new ApiNameUrl());
        apiCharacterInfo.setEpisode(Arrays.asList("episode 1", "episode 2"));
        
        Estado<ApiGetSingleCharacter> estadoOk = new Estado<>();
        estadoOk.setData(apiCharacterInfo);
        
        return estadoOk;
    }
    
    private Estado<ApiGetSingleCharacter> generarApiCharacterInfoConError() {
        Estado<ApiGetSingleCharacter> estadoError = new Estado<>();
        estadoError.setMensajeError("Error");
        
        return estadoError;
    }
    
    @Test
    public void consultarCharacterInfoSinOrigin() {
        Estado<ApiGetSingleCharacter> responseCharacterInfo = generarApiCharacterInfoOkSinOrigin();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfo);
        
        Estado<GetCharacterResponse> characterInfo = characterService.getCharacterInfo(1);
        
        assertThat(characterInfo.getData().getId(), is(responseCharacterInfo.getData().getId()));
        assertThat(characterInfo.getData().getName(), is(responseCharacterInfo.getData().getName()));
        assertThat(characterInfo.getData().getStatus(), is(responseCharacterInfo.getData().getStatus()));
        assertThat(characterInfo.getData().getSpecies(), is(responseCharacterInfo.getData().getSpecies()));
        assertThat(characterInfo.getData().getType(), is(responseCharacterInfo.getData().getType()));
        assertThat(characterInfo.getData().getEpisodeCount(), is(responseCharacterInfo.getData().getEpisode().size()));
    }
    
    @Test
    public void errorAlObtenerCharacterInfoDesdeApi() {
        Estado<ApiGetSingleCharacter> responseCharacterInfoConError = generarApiCharacterInfoConError();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfoConError);
        
        Estado<GetCharacterResponse> characterInfo = characterService.getCharacterInfo(1);
        assertThat(characterInfo.conErrores(), is(true));
    }
    
    @Test
    public void consultarCharacterInfoConOrigenConError() {
        Estado<ApiGetSingleCharacter> responseCharacterInfo = generarApiCharacterInfoOkConOrigin();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfo);
        
        ApiNameUrl origin = responseCharacterInfo.getData().getOrigin();
        Estado<ApiLocation> responseLocation = new Estado<>();
        when(characterRepository.getLocation(origin.getUrl())).thenReturn(responseLocation);
        
        Estado<GetCharacterResponse> characterInfo = characterService.getCharacterInfo(1);
        assertThat(characterInfo.conErrores(), is(true));
    }
    
    @Test
    public void consultarCharacterInfoConOrigen() {
        Estado<ApiGetSingleCharacter> responseCharacterInfo = generarApiCharacterInfoOkConOrigin();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfo);
        
        ApiNameUrl origin = responseCharacterInfo.getData().getOrigin();
        
        ApiLocation apiLocation = new ApiLocation();
        apiLocation.setName("name");
        apiLocation.setUrl("url");
        apiLocation.setDimension("dimension");
        apiLocation.setResidents(Arrays.asList("resident 1", "resident 2"));
        Estado<ApiLocation> responseLocation = new Estado<>();
        responseLocation.setData(apiLocation);
        when(characterRepository.getLocation(origin.getUrl())).thenReturn(responseLocation);
        
        Estado<GetCharacterResponse> characterInfo = characterService.getCharacterInfo(1);
        
        assertThat(characterInfo.getData().getId(), is(responseCharacterInfo.getData().getId()));
        assertThat(characterInfo.getData().getName(), is(responseCharacterInfo.getData().getName()));
        assertThat(characterInfo.getData().getStatus(), is(responseCharacterInfo.getData().getStatus()));
        assertThat(characterInfo.getData().getSpecies(), is(responseCharacterInfo.getData().getSpecies()));
        assertThat(characterInfo.getData().getType(), is(responseCharacterInfo.getData().getType()));
        assertThat(characterInfo.getData().getEpisodeCount(), is(responseCharacterInfo.getData().getEpisode().size()));

        assertThat(characterInfo.getData().getOrigin().getName(), is(apiLocation.getName()));
        assertThat(characterInfo.getData().getOrigin().getUrl(), is(apiLocation.getUrl()));
        assertThat(characterInfo.getData().getOrigin().getDimension(), is(apiLocation.getDimension()));
        assertThat(characterInfo.getData().getOrigin().getResidents(), is(apiLocation.getResidents()));
    }
    
}
