package cl.julio.rickandmorty.model;

import lombok.Data;

@Data
public class GetCharacterResponse {

    private int id;
    private String name = "";
    private String status = "";
    private String species = "";
    private String type = "";
    private int episodeCount;
    private Origin origin;

}
