package cl.julio.rickandmorty.repository;

import cl.julio.rickandmorty.domain.characterinfo.CharacterInfo;
import cl.julio.rickandmorty.domain.characterinfo.Location;
import cl.julio.rickandmorty.util.Estado;

public interface ICharacterRepository {

    public Estado<CharacterInfo> getCharacterInfo(int id);
    public Estado<Location> getLocation(String url);

}
