package cl.julio.rickandmorty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.julio.rickandmorty.model.GetCharacterResponse;
import cl.julio.rickandmorty.service.CharacterService;
import cl.julio.rickandmorty.util.Estado;

@RestController
@RequestMapping("/character")
public class CharacterRestController {

    private CharacterService characterService;
    
    public CharacterRestController(CharacterService characterService) {
        this.characterService = characterService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCharacterInfo(@PathVariable int id) {
        Estado<GetCharacterResponse> response = characterService.getCharacterInfo(id);
        if (response.conErrores()) {
            return ResponseEntity.internalServerError().body(response);
        }
        
        return ResponseEntity.ok(response.getData());
    }
}
