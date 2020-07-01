package az.pashabank.apl.ms.thy.config;

import az.pashabank.apl.ms.thy.validator.MainValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public MainValidator validator() {
        return new MainValidator();
    }

}
