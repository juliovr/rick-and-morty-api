package cl.julio.rickandmorty.domain.port.input;

import cl.julio.rickandmorty.domain.model.CharacterInfo;

public interface FindCharacterById {

    CharacterInfo execute(int id);

}
