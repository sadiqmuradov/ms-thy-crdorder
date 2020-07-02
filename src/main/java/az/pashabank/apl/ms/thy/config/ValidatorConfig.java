package az.pashabank.apl.ms.thy.config;

import az.pashabank.apl.ms.thy.validator.CardStepsValidator;
import az.pashabank.apl.ms.thy.validator.CardValidator;
import az.pashabank.apl.ms.thy.validator.CouponValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public CardValidator validator() {
        return new CardValidator();
    }

    @Bean
    public CouponValidator couponValidator() {
        return new CouponValidator();
    }

    @Bean
    public CardStepsValidator cardStepsValidator() {
        return new CardStepsValidator();
    }

}
