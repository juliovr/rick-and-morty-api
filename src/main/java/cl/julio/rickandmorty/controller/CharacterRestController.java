package cl.julio.rickandmorty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.julio.rickandmorty.model.GetCharacterResponse;
import cl.julio.rickandmorty.service.ICharacterService;

@RestController
@RequestMapping("/character")
public class CharacterRestController {

    private ICharacterService characterService;
    
    public CharacterRestController(ICharacterService characterService) {
        this.characterService = characterService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GetCharacterResponse> getCharacterInfo(@PathVariable int id) {
        GetCharacterResponse response = characterService.getCharacterInfo(id);
        
        return ResponseEntity.ok(response);
    }
}
