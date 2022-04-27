package cl.julio.rickandmorty.domain.characterinfo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CharacterInfo {

    private int id;
    private String name = "";
    private String status = "";
    private String species = "";
    private String type = "";
    private NameUrl origin;
    private NameUrl location;
    private List<String> episode = new ArrayList<>();

}
