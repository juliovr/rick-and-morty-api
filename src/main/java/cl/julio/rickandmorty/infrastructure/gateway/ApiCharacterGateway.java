package cl.julio.rickandmorty.infrastructure.gateway;

import cl.julio.rickandmorty.delete.GetCharacterInfoException;
import cl.julio.rickandmorty.domain.model.CharacterInfo;
import cl.julio.rickandmorty.domain.port.output.CharacterGateway;
import cl.julio.rickandmorty.infrastructure.gateway.model.ApiCharacterInfo;
import cl.julio.rickandmorty.infrastructure.gateway.model.ApiLocation;
import cl.julio.rickandmorty.util.RestUtil;
import cl.julio.rickandmorty.util.State;
import cl.julio.rickandmorty.util.StringUtil;
import org.springframework.stereotype.Repository;

@Repository
public class ApiCharacterGateway implements CharacterGateway {

    private static final String CHARACTER_URL = "https://rickandmortyapi.com/api/character/";

    @Override
    public CharacterInfo getCharacterInfo(int id) {
        String url = CHARACTER_URL + id;
        State<ApiCharacterInfo> responseCharacterInfo = RestUtil.get(url, ApiCharacterInfo.class);

        if (responseCharacterInfo.withErrors()) {
            throw new GetCharacterInfoException("No se pudo obtener la información del personaje '" + id + "'");
        }

        CharacterInfo characterInfo = FindCharacterMapper.mapFrom(responseCharacterInfo.getData());

        ApiCharacterInfo apiCharacterInfo = responseCharacterInfo.getData();
        if (!StringUtil.isNullOrEmpty(apiCharacterInfo.getOrigin().getUrl())) {
            State<ApiLocation> responseLocation = RestUtil.get(url, ApiLocation.class);
            if (responseLocation.withErrors()) {
                throw new GetCharacterInfoException("No se pudo obtener la información de origen del personaje '" + id + "'");
            }

            characterInfo.setOrigin(FindLocationMapper.mapFrom(responseLocation.getData()));
        }

        return characterInfo;
    }

}
