package cl.julio.rickandmorty.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CharacterInfo {

    private int id;
    private String name = "";
    private String status = "";
    private String species = "";
    private String type = "";
    
    @JsonProperty("episode_count")
    private int episodeCount;
    private Origin origin = new Origin();

}
