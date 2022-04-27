package cl.julio.rickandmorty.model;

import lombok.Data;

@Data
public class DetalleResponse {

    private int codigo;
    private String mensaje;
    private String detalle;
    
    public DetalleResponse(int codigo) {
        this.codigo = codigo;
    }
    
}
