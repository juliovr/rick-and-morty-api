package cl.julio.rickandmorty.repository.api;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ApiGetSingleCharacter {

    private int id;
    private String name = "";
    private String status = "";
    private String species = "";
    private String type = "";
    private ApiNameUrl origin;
    private ApiNameUrl location;
    private List<String> episode = new ArrayList<>();

}
