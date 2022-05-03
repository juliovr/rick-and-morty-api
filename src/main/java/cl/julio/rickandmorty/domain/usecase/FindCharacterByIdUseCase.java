package cl.julio.rickandmorty.domain.usecase;

import cl.julio.rickandmorty.domain.port.input.FindCharacterById;
import org.springframework.stereotype.Service;

import cl.julio.rickandmorty.delete.GetCharacterInfoException;
import cl.julio.rickandmorty.infrastructure.gateway.FindCharacterMapper;
import cl.julio.rickandmorty.infrastructure.gateway.model.ApiLocation;
import cl.julio.rickandmorty.domain.model.CharacterInfo;
import cl.julio.rickandmorty.domain.model.Origin;
import cl.julio.rickandmorty.domain.port.output.CharacterGateway;
import cl.julio.rickandmorty.util.State;
import cl.julio.rickandmorty.util.StringUtil;

public class FindCharacterByIdUseCase implements FindCharacterById {

    private final CharacterGateway characterGateway;

    public FindCharacterByIdUseCase(CharacterGateway characterGateway) {
        this.characterGateway = characterGateway;
    }

    @Override
    public CharacterInfo execute(int id) {
        CharacterInfo characterInfo = characterGateway.getCharacterInfo(id);

        return characterInfo;
    }

}
