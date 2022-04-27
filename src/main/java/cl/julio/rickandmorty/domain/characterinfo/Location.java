package cl.julio.rickandmorty.domain.characterinfo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Location {

    private int id;
    private String name = "";
    private String type = "";
    private String dimension = "";
    private List<String> residents = new ArrayList<>();
    private String url = "";
    private String created = "";

}
