package cl.julio.rickandmorty.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetCharacterResponse {

    private int id;
    private String name = "";
    private String status = "";
    private String species = "";
    private String type = "";
    
    @JsonProperty("episode_count")
    private int episodeCount;
    private Origin origin;

}
