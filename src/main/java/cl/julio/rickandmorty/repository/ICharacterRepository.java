package cl.julio.rickandmorty.repository;

import cl.julio.rickandmorty.domain.characterinfo.CharacterInfo;
import cl.julio.rickandmorty.domain.characterinfo.Location;
import cl.julio.rickandmorty.util.Estado;

public interface ICharacterRepository {

    Estado<CharacterInfo> getCharacterInfo(int id);
    Estado<Location> getLocation(String url);

}
