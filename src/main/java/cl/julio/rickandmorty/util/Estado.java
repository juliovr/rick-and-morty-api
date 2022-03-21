package cl.julio.rickandmorty.util;

import lombok.Data;

@Data
public class Estado<T> {

    private T data;
    private String mensajeError;
    private String detalleError;
    
    public boolean conErrores() {
        return (data == null || 
                !StringUtil.isNullOrEmpty(mensajeError) || 
                !StringUtil.isNullOrEmpty(detalleError));
    }

}
