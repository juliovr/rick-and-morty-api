package cl.julio.rickandmorty.repository;

import org.springframework.stereotype.Repository;

import cl.julio.rickandmorty.domain.characterinfo.CharacterInfo;
import cl.julio.rickandmorty.domain.characterinfo.Location;
import cl.julio.rickandmorty.util.Estado;
import cl.julio.rickandmorty.util.RestUtil;

@Repository
public class ApiCharacterRepository implements ICharacterRepository {
    
    @Override
    public Estado<CharacterInfo> getCharacterInfo(int id) {
        String url = "https://rickandmortyapi.com/api/character/" + id;
        return RestUtil.get(url, CharacterInfo.class);
    }
    
    @Override
    public Estado<Location> getLocation(String url) {
        return RestUtil.get(url, Location.class);
    }
    
}
