package cl.julio.rickandmorty.service;

import cl.julio.rickandmorty.model.GetCharacterResponse;

public interface ICharacterService {

    GetCharacterResponse getCharacterInfo(int id);

}
