package cl.julio.rickandmorty.domain.port.output;

import cl.julio.rickandmorty.domain.model.CharacterInfo;
import cl.julio.rickandmorty.util.State;

public interface CharacterGateway {

    CharacterInfo getCharacterInfo(int id);

}
