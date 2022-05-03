package cl.julio.rickandmorty.infrastructure.configuration;

import cl.julio.rickandmorty.domain.port.input.FindCharacterById;
import cl.julio.rickandmorty.domain.port.output.CharacterGateway;
import cl.julio.rickandmorty.domain.usecase.FindCharacterByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public FindCharacterById findCharacterById(CharacterGateway characterGateway) {
        return new FindCharacterByIdUseCase(characterGateway);
    }

}
