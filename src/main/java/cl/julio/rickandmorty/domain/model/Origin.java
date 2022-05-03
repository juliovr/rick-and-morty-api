package cl.julio.rickandmorty.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Origin {

    private String name = "";
    private String url = "";
    private String dimension = "";
    private List<String> residents = new ArrayList<>();

}
