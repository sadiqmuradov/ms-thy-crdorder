package az.pashabank.apl.ms.thy.client;

import az.pashabank.apl.ms.thy.constants.URL;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentRequest;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ECommRestClient {
    private static final MainLogger LOGGER = MainLogger.getLogger(ECommRestClient.class);

    @Value("${ecomm.card.clientId}")
    private String ecommClientId;

    @Value("${ecomm.rest.endpoint}")
    private String ecommRestEndpoint;


    public ECommRegisterPaymentResponse registerPayment(ECommRegisterPaymentRequest request) {
        ECommRegisterPaymentResponse eCommRegisterPaymentResponse = new ECommRegisterPaymentResponse();
        LOGGER.info("ecommRestEndpoint {}{}", ecommRestEndpoint, URL.ECOMM_REGISTER_PAYMENT);
        try {
            eCommRegisterPaymentResponse =
                    getWebClient().post()
                            .uri(URL.ECOMM_REGISTER_PAYMENT)
                            .body(Mono.just(request), ECommRegisterPaymentRequest.class)
                            .retrieve()
                            .bodyToMono(ECommRegisterPaymentResponse.class)
                            .block();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return eCommRegisterPaymentResponse;
    }

    private WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(ecommRestEndpoint)
                .build();
    }
}
