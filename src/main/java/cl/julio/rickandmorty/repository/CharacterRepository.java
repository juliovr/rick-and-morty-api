package cl.julio.rickandmorty.repository;

import org.springframework.stereotype.Repository;

import cl.julio.rickandmorty.repository.api.ApiGetSingleCharacter;
import cl.julio.rickandmorty.repository.api.ApiLocation;
import cl.julio.rickandmorty.util.Estado;
import cl.julio.rickandmorty.util.RestUtil;

@Repository
public class CharacterRepository {
    
    public Estado<ApiGetSingleCharacter> getCharacterInfo(int id) {
        String url = "https://rickandmortyapi.com/api/character/" + id;
        return RestUtil.get(url, ApiGetSingleCharacter.class);
    }
    
    public Estado<ApiLocation> getLocation(String url) {
        return RestUtil.get(url, ApiLocation.class);
    }
    
}
