package cl.julio.rickandmorty.domain.characterinfo;

import cl.julio.rickandmorty.model.GetCharacterResponse;
import cl.julio.rickandmorty.model.Origin;

public final class GetCharacterResponseBuilder {

    private GetCharacterResponse response = new GetCharacterResponse();
    
    private GetCharacterResponseBuilder() {
        // Non-instantiable class
    }
    
    public static GetCharacterResponseBuilder newBuilder() {
        return new GetCharacterResponseBuilder();
    }
    
    public GetCharacterResponseBuilder addCharacterInfo(CharacterInfo characterInfo) {
        response.setId(characterInfo.getId());
        response.setName(characterInfo.getName());
        response.setStatus(characterInfo.getStatus());
        response.setSpecies(characterInfo.getSpecies());
        response.setType(characterInfo.getType());
        response.setEpisodeCount(characterInfo.getEpisode().size());
        
        return this;
    }
    
    public GetCharacterResponseBuilder addOrigin(Origin origin) {
        response.setOrigin(origin);
        
        return this;
    }
    
    public GetCharacterResponse build() {
        return response;
    }
    
}
