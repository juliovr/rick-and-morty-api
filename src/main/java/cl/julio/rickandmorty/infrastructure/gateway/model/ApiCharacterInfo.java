package cl.julio.rickandmorty.infrastructure.gateway.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ApiCharacterInfo {

    private int id;
    private String name = "";
    private String status = "";
    private String species = "";
    private String type = "";
    private ApiNameUrl origin = new ApiNameUrl();
    private ApiNameUrl location = new ApiNameUrl();
    private List<String> episode = new ArrayList<>();

}
