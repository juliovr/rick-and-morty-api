package cl.julio.rickandmorty.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.julio.rickandmorty.domain.model.CharacterInfo;
import cl.julio.rickandmorty.domain.port.input.FindCharacterById;

@RestController
@RequestMapping("/characters")
public class CharactersRestController {

    private final FindCharacterById findCharacterById;
    
    public CharactersRestController(FindCharacterById findCharacterById) {
        this.findCharacterById = findCharacterById;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CharacterInfo> findById(@PathVariable int id) {
        CharacterInfo response = findCharacterById.execute(id);
        
        return ResponseEntity.ok(response);
    }
}
