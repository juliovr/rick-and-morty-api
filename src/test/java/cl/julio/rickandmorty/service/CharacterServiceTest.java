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

import cl.julio.rickandmorty.domain.characterinfo.CharacterInfo;
import cl.julio.rickandmorty.domain.characterinfo.GetCharacterInfoException;
import cl.julio.rickandmorty.domain.characterinfo.Location;
import cl.julio.rickandmorty.domain.characterinfo.NameUrl;
import cl.julio.rickandmorty.model.GetCharacterResponse;
import cl.julio.rickandmorty.repository.ICharacterRepository;
import cl.julio.rickandmorty.util.Estado;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceTest {

    @Mock
    private ICharacterRepository characterRepository;

    private CharacterService characterService;
    
    @Before
    public void setUp() {
        characterService = new CharacterService(characterRepository);
    }
    
    private Estado<CharacterInfo> generarApiCharacterInfoOkSinOrigin() {
        CharacterInfo apiCharacterInfo = new CharacterInfo();
        apiCharacterInfo.setId(1);
        apiCharacterInfo.setName("nombre");
        apiCharacterInfo.setStatus("status");
        apiCharacterInfo.setSpecies("species");
        apiCharacterInfo.setType("type");
        apiCharacterInfo.setOrigin(new NameUrl());
        apiCharacterInfo.setLocation(new NameUrl());
        apiCharacterInfo.setEpisode(Arrays.asList("episode 1", "episode 2"));
        
        Estado<CharacterInfo> estadoOk = new Estado<>();
        estadoOk.setData(apiCharacterInfo);
        
        return estadoOk;
    }
    
    private Estado<CharacterInfo> generarApiCharacterInfoOkConOrigin() {
        NameUrl origin = new NameUrl();
        origin.setName("origin");
        origin.setUrl("url_origin");
        
        CharacterInfo apiCharacterInfo = new CharacterInfo();
        apiCharacterInfo.setId(1);
        apiCharacterInfo.setName("nombre");
        apiCharacterInfo.setStatus("status");
        apiCharacterInfo.setSpecies("species");
        apiCharacterInfo.setType("type");
        apiCharacterInfo.setOrigin(origin);
        apiCharacterInfo.setLocation(new NameUrl());
        apiCharacterInfo.setEpisode(Arrays.asList("episode 1", "episode 2"));
        
        Estado<CharacterInfo> estadoOk = new Estado<>();
        estadoOk.setData(apiCharacterInfo);
        
        return estadoOk;
    }
    
    private Estado<CharacterInfo> generarApiCharacterInfoConError() {
        Estado<CharacterInfo> estadoError = new Estado<>();
        estadoError.setMensajeError("Error");
        
        return estadoError;
    }
    
    @Test
    public void consultarCharacterInfoSinOrigin() {
        Estado<CharacterInfo> responseCharacterInfo = generarApiCharacterInfoOkSinOrigin();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfo);
        
        GetCharacterResponse characterInfo = characterService.getCharacterInfo(1);
        
        assertThat(characterInfo.getId(), is(responseCharacterInfo.getData().getId()));
        assertThat(characterInfo.getName(), is(responseCharacterInfo.getData().getName()));
        assertThat(characterInfo.getStatus(), is(responseCharacterInfo.getData().getStatus()));
        assertThat(characterInfo.getSpecies(), is(responseCharacterInfo.getData().getSpecies()));
        assertThat(characterInfo.getType(), is(responseCharacterInfo.getData().getType()));
        assertThat(characterInfo.getEpisodeCount(), is(responseCharacterInfo.getData().getEpisode().size()));
    }
    
    @Test(expected = GetCharacterInfoException.class)
    public void errorAlObtenerCharacterInfoDesdeApi() {
        Estado<CharacterInfo> responseCharacterInfoConError = generarApiCharacterInfoConError();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfoConError);
        
        characterService.getCharacterInfo(1);
    }
    
    @Test(expected = GetCharacterInfoException.class)
    public void consultarCharacterInfoConOrigenConError() {
        Estado<CharacterInfo> responseCharacterInfo = generarApiCharacterInfoOkConOrigin();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfo);
        
        NameUrl origin = responseCharacterInfo.getData().getOrigin();
        Estado<Location> responseLocation = new Estado<>();
        when(characterRepository.getLocation(origin.getUrl())).thenReturn(responseLocation);
        
        characterService.getCharacterInfo(1);
    }
    
    @Test
    public void consultarCharacterInfoConOrigen() {
        Estado<CharacterInfo> responseCharacterInfo = generarApiCharacterInfoOkConOrigin();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfo);
        
        NameUrl origin = responseCharacterInfo.getData().getOrigin();
        
        Location apiLocation = new Location();
        apiLocation.setName("name");
        apiLocation.setUrl("url");
        apiLocation.setDimension("dimension");
        apiLocation.setResidents(Arrays.asList("resident 1", "resident 2"));
        Estado<Location> responseLocation = new Estado<>();
        responseLocation.setData(apiLocation);
        when(characterRepository.getLocation(origin.getUrl())).thenReturn(responseLocation);
        
        GetCharacterResponse characterInfo = characterService.getCharacterInfo(1);
        
        assertThat(characterInfo.getId(), is(responseCharacterInfo.getData().getId()));
        assertThat(characterInfo.getName(), is(responseCharacterInfo.getData().getName()));
        assertThat(characterInfo.getStatus(), is(responseCharacterInfo.getData().getStatus()));
        assertThat(characterInfo.getSpecies(), is(responseCharacterInfo.getData().getSpecies()));
        assertThat(characterInfo.getType(), is(responseCharacterInfo.getData().getType()));
        assertThat(characterInfo.getEpisodeCount(), is(responseCharacterInfo.getData().getEpisode().size()));

        assertThat(characterInfo.getOrigin().getName(), is(apiLocation.getName()));
        assertThat(characterInfo.getOrigin().getUrl(), is(apiLocation.getUrl()));
        assertThat(characterInfo.getOrigin().getDimension(), is(apiLocation.getDimension()));
        assertThat(characterInfo.getOrigin().getResidents(), is(apiLocation.getResidents()));
    }
    
}
