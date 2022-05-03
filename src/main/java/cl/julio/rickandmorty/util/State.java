package cl.julio.rickandmorty.util;

import lombok.Data;

@Data
public class State<T> {

    private T data;
    private String errorMessage;
    private String errorDetail;
    
    public boolean withErrors() {
        return (data == null || 
                !StringUtil.isNullOrEmpty(errorMessage) ||
                !StringUtil.isNullOrEmpty(errorDetail));
    }

}
