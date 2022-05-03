package cl.julio.rickandmorty.infrastructure.gateway;

import cl.julio.rickandmorty.domain.model.CharacterInfo;
import cl.julio.rickandmorty.infrastructure.gateway.model.ApiCharacterInfo;
import cl.julio.rickandmorty.util.State;

public final class FindCharacterMapper {

    private FindCharacterMapper() {
        // Non-instantiable class
    }

    public static State<CharacterInfo> mapApiResponse(State<ApiCharacterInfo> apiCharacterInfoState) {
        State<CharacterInfo> characterInfoState = new State<>();
        if (apiCharacterInfoState.withErrors()) {
            characterInfoState.setErrorMessage(apiCharacterInfoState.getErrorMessage());
            characterInfoState.setErrorDetail(apiCharacterInfoState.getErrorDetail());
            return characterInfoState;
        }

        CharacterInfo characterInfo = mapFrom(apiCharacterInfoState.getData());
        characterInfoState.setData(characterInfo);

        return characterInfoState;
    }

    public static CharacterInfo mapFrom(ApiCharacterInfo apiCharacterInfo) {
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setId(apiCharacterInfo.getId());
        characterInfo.setName(apiCharacterInfo.getName());
        characterInfo.setStatus(apiCharacterInfo.getStatus());
        characterInfo.setSpecies(apiCharacterInfo.getSpecies());
        characterInfo.setType(apiCharacterInfo.getType());
        characterInfo.setEpisodeCount(apiCharacterInfo.getEpisode().size());
        
        return characterInfo;
    }

}
