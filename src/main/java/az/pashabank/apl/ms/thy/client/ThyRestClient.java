package az.pashabank.apl.ms.thy.client;

import az.pashabank.apl.ms.thy.constants.URL;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.thy.CheckTkRequest;
import az.pashabank.apl.ms.thy.model.thy.CheckTkRestResponse;
import az.pashabank.apl.ms.thy.model.thy.CheckTkReturn;
import az.pashabank.apl.ms.thy.model.thy.MemberOperationsRequest;
import az.pashabank.apl.ms.thy.model.thy.MemberOperationsResponse;
import az.pashabank.apl.ms.thy.model.thy.MemberOperationsReturn;
import az.pashabank.apl.ms.thy.model.thy.SecurityQuestionsRequest;
import az.pashabank.apl.ms.thy.model.thy.SecurityQuestionsRestResponse;
import az.pashabank.apl.ms.thy.model.thy.SecurityQuestionsReturn;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class ThyRestClient {

    private static final MainLogger LOGGER = MainLogger.getLogger(ThyRestClient.class);

    @Value("${thy.rest.endpoint}")
    private String thyRestEndpoint;

    public CheckTkRestResponse getMemberDetails(CheckTkRequest request) {
        CheckTkRestResponse checkTkRestResponse = new CheckTkRestResponse(new CheckTkReturn(new ArrayList<>()));

        try {
            checkTkRestResponse =
                    getWebClient().post()
                            .uri(URL.THY_POST_GET_MEMBER_DETAILS)
                            .body(Mono.just(request), CheckTkRequest.class)
                            .retrieve()
                            .bodyToMono(CheckTkRestResponse.class)
                            .block();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return checkTkRestResponse;
    }

    public MemberOperationsResponse createMember(MemberOperationsRequest request) {
        MemberOperationsResponse memberOperationsResponse = new MemberOperationsResponse(new MemberOperationsReturn());

        try {
            memberOperationsResponse =
                    getWebClient().post()
                            .uri(URL.THY_POST_MEMBER_OPERATIONS)
                            .body(Mono.just(request), MemberOperationsRequest.class)
                            .retrieve()
                            .bodyToMono(MemberOperationsResponse.class)
                            .block();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return memberOperationsResponse;
    }

    public SecurityQuestionsRestResponse getSecurityQuestions(SecurityQuestionsRequest request) {
        SecurityQuestionsRestResponse securityQuestionsRestResponse = new SecurityQuestionsRestResponse(new SecurityQuestionsReturn(new ArrayList<>()));

        try {
            securityQuestionsRestResponse =
                    getWebClient().post()
                            .uri(URL.THY_POST_GET_SPECIFIC_COMBOBOX)
                            .body(Mono.just(request), SecurityQuestionsRequest.class)
                            .retrieve()
                            .bodyToMono(SecurityQuestionsRestResponse.class)
                            .block();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return securityQuestionsRestResponse;
    }

    private WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(thyRestEndpoint)
                .build();
    }

}
