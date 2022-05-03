package cl.julio.rickandmorty.infrastructure.gateway.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ApiLocation {

    private int id;
    private String name = "";
    private String type = "";
    private String dimension = "";
    private List<String> residents = new ArrayList<>();
    private String url = "";
    private String created = "";

}
