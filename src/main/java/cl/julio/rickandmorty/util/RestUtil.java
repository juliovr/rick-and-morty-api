package cl.julio.rickandmorty.util;

import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestUtil {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    
    public static <T> Estado<T> get(String url, Class<T> clase) {
        log.info("Consumiendo WS get -> URL: {}", url);
        Estado<T> estado = new Estado<>();
        try {
            T response = REST_TEMPLATE.getForObject(url, clase);
            estado.setData(response);
        } catch (Exception e) {
            estado.setDetalleError(e.getMessage());
        }
        
        return estado;
    }
    
}
