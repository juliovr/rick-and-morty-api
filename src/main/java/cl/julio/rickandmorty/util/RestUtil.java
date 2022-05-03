package cl.julio.rickandmorty.util;

import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestUtil {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    
    public static <T> State<T> get(String url, Class<T> clase) {
        log.info("Consumiendo WS get -> URL: {}", url);
        State<T> state = new State<>();
        try {
            T response = REST_TEMPLATE.getForObject(url, clase);
            state.setData(response);
        } catch (Exception e) {
            state.setErrorDetail(e.getMessage());
        }
        
        return state;
    }
    
}
