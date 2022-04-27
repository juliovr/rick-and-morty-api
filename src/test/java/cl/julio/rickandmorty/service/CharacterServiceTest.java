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
import cl.julio.rickandmorty.model.Origin;
import cl.julio.rickandmorty.repository.ICharacterRepository;
import cl.julio.rickandmorty.util.Estado;

@RunWith(MockitoJUnitRunner.class)
public class CharacterServiceTest {

    private static final Origin EMPTY_ORIGIN = new Origin();
    private static final Estado<Location> EMPTY_LOCATION_RESPONSE = new Estado<>();
        
    @Mock
    private ICharacterRepository characterRepository;

    private CharacterService characterService;
    
    @Before
    public void setUp() {
        characterService = new CharacterService(characterRepository);
    }
    
    @Test
    public void origin_field_should_be_empty_if_character_has_no_origin_info() {
        Estado<CharacterInfo> responseCharacterInfo = generarApiCharacterInfoOkSinOrigin();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfo);
        
        GetCharacterResponse characterInfo = characterService.getCharacterInfo(1);
        
        assertThat(characterInfo.getId(), is(responseCharacterInfo.getData().getId()));
        assertThat(characterInfo.getName(), is(responseCharacterInfo.getData().getName()));
        assertThat(characterInfo.getStatus(), is(responseCharacterInfo.getData().getStatus()));
        assertThat(characterInfo.getSpecies(), is(responseCharacterInfo.getData().getSpecies()));
        assertThat(characterInfo.getType(), is(responseCharacterInfo.getData().getType()));
        assertThat(characterInfo.getEpisodeCount(), is(responseCharacterInfo.getData().getEpisode().size()));
        
        assertThat(characterInfo.getOrigin(), is(EMPTY_ORIGIN));
    }
    
    @Test(expected = GetCharacterInfoException.class)
    public void should_throw_error_if_cannot_get_character_info() {
        Estado<CharacterInfo> responseCharacterInfoConError = generarApiCharacterInfoConError();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfoConError);
        
        characterService.getCharacterInfo(1);
    }
    
    @Test(expected = GetCharacterInfoException.class)
    public void should_throw_error_when_trying_to_obtain_origin_from_character_who_must_have_an_origin() {
        Estado<CharacterInfo> responseCharacterInfo = generarApiCharacterInfoOkConOrigin();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfo);
        
        NameUrl origin = responseCharacterInfo.getData().getOrigin();
        when(characterRepository.getLocation(origin.getUrl())).thenReturn(EMPTY_LOCATION_RESPONSE);
        
        characterService.getCharacterInfo(1);
    }
    
    @Test
    public void should_get_character_with_info_and_origin() {
        Estado<CharacterInfo> responseCharacterInfo = generarApiCharacterInfoOkConOrigin();
        when(characterRepository.getCharacterInfo(1)).thenReturn(responseCharacterInfo);
        
        NameUrl origin = responseCharacterInfo.getData().getOrigin();
        
        Estado<Location> responseLocation = generarResponseLocationConInfo();
        when(characterRepository.getLocation(origin.getUrl())).thenReturn(responseLocation);
        
        
        GetCharacterResponse characterInfo = characterService.getCharacterInfo(1);
        
        assertThat(characterInfo.getId(), is(responseCharacterInfo.getData().getId()));
        assertThat(characterInfo.getName(), is(responseCharacterInfo.getData().getName()));
        assertThat(characterInfo.getStatus(), is(responseCharacterInfo.getData().getStatus()));
        assertThat(characterInfo.getSpecies(), is(responseCharacterInfo.getData().getSpecies()));
        assertThat(characterInfo.getType(), is(responseCharacterInfo.getData().getType()));
        assertThat(characterInfo.getEpisodeCount(), is(responseCharacterInfo.getData().getEpisode().size()));

        Location location = responseLocation.getData();
        assertThat(characterInfo.getOrigin().getName(), is(location.getName()));
        assertThat(characterInfo.getOrigin().getUrl(), is(location.getUrl()));
        assertThat(characterInfo.getOrigin().getDimension(), is(location.getDimension()));
        assertThat(characterInfo.getOrigin().getResidents(), is(location.getResidents()));
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
    
    private Estado<Location> generarResponseLocationConInfo() {
        Location apiLocation = new Location();
        apiLocation.setName("name");
        apiLocation.setUrl("url");
        apiLocation.setDimension("dimension");
        apiLocation.setResidents(Arrays.asList("resident 1", "resident 2"));
        Estado<Location> responseLocation = new Estado<>();
        responseLocation.setData(apiLocation);
        
        return responseLocation;
    }
    
}
