package cl.julio.rickandmorty.service;

import org.springframework.stereotype.Service;

import cl.julio.rickandmorty.domain.characterinfo.CharacterInfo;
import cl.julio.rickandmorty.domain.characterinfo.GetCharacterInfoException;
import cl.julio.rickandmorty.domain.characterinfo.GetCharacterResponseBuilder;
import cl.julio.rickandmorty.domain.characterinfo.Location;
import cl.julio.rickandmorty.model.GetCharacterResponse;
import cl.julio.rickandmorty.model.Origin;
import cl.julio.rickandmorty.repository.ICharacterRepository;
import cl.julio.rickandmorty.util.Estado;
import cl.julio.rickandmorty.util.StringUtil;

@Service
public class CharacterService {

    private ICharacterRepository characterRepository;

    public CharacterService(ICharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public GetCharacterResponse getCharacterInfo(int id) {
        Estado<CharacterInfo> responseCharacterInfo = characterRepository.getCharacterInfo(id);
        if (responseCharacterInfo.conErrores()) {
            throw new GetCharacterInfoException("Ocurrió un error obteniendo la información del personaje '" + id + "'");
        }
        
        CharacterInfo characterInfo = responseCharacterInfo.getData();
        Origin origin = getOriginInfo(characterInfo);

        return GetCharacterResponseBuilder.newBuilder()
                .addCharacterInfo(characterInfo)
                .addOrigin(origin)
                .build();
    }
    
    private Origin getOriginInfo(CharacterInfo characterInfo) {
        Origin origin = new Origin();
        if (!StringUtil.isNullOrEmpty(characterInfo.getOrigin().getUrl())) {
            Estado<Location> responseLocation = characterRepository.getLocation(characterInfo.getOrigin().getUrl());
            if (responseLocation.conErrores()) {
                throw new GetCharacterInfoException("Ocurrió un error obteniendo la información de origen del personaje '" + characterInfo.getId() + "'");
            }

            Location location = responseLocation.getData();
            origin.setName(location.getName());
            origin.setUrl(location.getUrl());
            origin.setDimension(location.getDimension());
            origin.setResidents(location.getResidents());
        }
        
        return origin;
    }

}
