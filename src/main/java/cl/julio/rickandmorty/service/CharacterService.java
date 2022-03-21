package cl.julio.rickandmorty.service;

import org.springframework.stereotype.Service;

import cl.julio.rickandmorty.model.GetCharacterResponse;
import cl.julio.rickandmorty.model.Origin;
import cl.julio.rickandmorty.repository.CharacterRepository;
import cl.julio.rickandmorty.repository.api.ApiGetSingleCharacter;
import cl.julio.rickandmorty.repository.api.ApiLocation;
import cl.julio.rickandmorty.util.Estado;
import cl.julio.rickandmorty.util.StringUtil;

@Service
public class CharacterService {

    private CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public Estado<GetCharacterResponse> getCharacterInfo(int id) {
        Estado<GetCharacterResponse> estadoResponse = new Estado<>();
        
        Estado<ApiGetSingleCharacter> responseCharacterInfo = characterRepository.getCharacterInfo(id);
        if (responseCharacterInfo.conErrores()) {
            estadoResponse.setMensajeError("Ocurrió un error obteniendo la información del personaje '" + id + "'");
            return estadoResponse;
        }
        
        ApiGetSingleCharacter characterInfo = responseCharacterInfo.getData();
        
        Origin origin = new Origin();
        if (!StringUtil.isNullOrEmpty(characterInfo.getOrigin().getUrl())) {
            Estado<ApiLocation> responseLocation = characterRepository.getLocation(characterInfo.getOrigin().getUrl());
            if (responseLocation.conErrores()) {
                estadoResponse.setMensajeError("Ocurrió un error obteniendo la información de origen del personaje '" + id + "'");
                return estadoResponse;
            }

            ApiLocation location = responseLocation.getData();
            origin.setName(location.getName());
            origin.setUrl(location.getUrl());
            origin.setDimension(location.getDimension());
            origin.setResidents(location.getResidents());
        }
        
        GetCharacterResponse response = new GetCharacterResponse();
        response.setId(characterInfo.getId());
        response.setName(characterInfo.getName());
        response.setStatus(characterInfo.getStatus());
        response.setSpecies(characterInfo.getSpecies());
        response.setType(characterInfo.getType());
        response.setEpisodeCount(characterInfo.getEpisode().size());
        response.setOrigin(origin);
        
        estadoResponse.setData(response);
        return estadoResponse;
    }

}
