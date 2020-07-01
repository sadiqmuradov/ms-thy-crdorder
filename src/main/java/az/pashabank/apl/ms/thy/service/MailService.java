package az.pashabank.apl.ms.thy.service;


import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.logger.UFCLogger;
import az.pashabank.apl.ms.thy.model.*;
import az.pashabank.apl.ms.thy.utils.Utils;
import az.pashabank.apl.ms.thy.validator.MainValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class MailService {
    private static final UFCLogger LOGGER = UFCLogger.getLogger(MailService.class);

    @Value("${mail.service.username}")
    private String mailUsername;

    @Value("${mail.service.password}")
    private String mailPass;

    @Value("${mail.service.from}")
    private String fromMail;

    @Value("${mail.service.message_template.payment.card.begin}")
    private String emailPaymentPlainTemplate;

    @Value("${mail.service.url}")
    private String plainEmailUrl;

    @Value("${mail.service.message_template.thy_otrs_request}")
    private String emailOtrsRequestPlainTemplate;

    @Value("${mail.service.multipart.url}")
    private String emailUrl;

    @Autowired
    private MainValidator validator;

    public OperationResponse sendPlainEmail(Payment payment, String toEmail) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);

        try {
            RestTemplate restTemplate = new RestTemplate();
            LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("user", mailUsername);
            map.add("pass", mailPass);
            map.add("html", "1");

            String date = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(payment.getCreateDate());
            String amount = Utils.formatBigDecimal(
                    2, 2, false, payment.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
            );

            String message = String.format(
                    emailPaymentPlainTemplate,
                    /*for Azerbaijan language*/
                    payment.getTransactionId(),
                    amount,
                    payment.getCurrency(),
                    payment.getEcommCardNo(),
                    date,
                    /*for English language*/
                    payment.getTransactionId(),
                    amount,
                    payment.getCurrency(),
                    payment.getEcommCardNo(),
                    date
            );
            String subject = "PASHA THY Miles & Smiles Card Order payment receipt";

            map.add("message", URLEncoder.encode(message, "UTF-8"));
            map.add("subject", subject);
            map.add("to", toEmail);
            map.add("from", fromMail);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            String result = restTemplate.postForObject(plainEmailUrl, request, String.class);

            JSONObject jsono = new JSONObject(result);
            operationResponse.setCode(jsono.getEnum(ResultCode.class, "code"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return operationResponse;
    }

    public OperationResponse sendEmail(ThyApplication application, List<CRSQuestion> crsQuestions, Payment payment, List<String> toEmails, List<Resource> uploads) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);

        try {
            LOGGER.info("Send email. ThyApplication: " + application + ", Emails: " + toEmails + ", Uploads: " + uploads);
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("user", mailUsername);
            map.add("pass", mailPass);
            for (Resource r : uploads) {
                map.add("attachments", r);
            }
            map.add("message", URLEncoder.encode(generateMailContent(application, crsQuestions, payment), "UTF-8"));
            map.add("subject", "PASHA THY Miles & Smiles Card Order Request");
            for (String s : toEmails) {
                map.add("to", s);
            }
            map.add("from", fromMail);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(emailUrl + "?user=pasha_life_app&pass=pA5haL!f3", HttpMethod.POST, requestEntity, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String result = responseEntity.getBody();
                JSONObject jsono = new JSONObject(result);
                operationResponse.setCode(jsono.getEnum(ResultCode.class, "code"));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return operationResponse;
    }

    private @NotNull String generateMailContent(@NotNull ThyApplication application, List<CRSQuestion> crsQuestions, Payment payment) {
        String plainTemplate = String.format(
                emailOtrsRequestPlainTemplate,
                application.getResidency(),
                application.getNationality(),
                application.getName(),
                application.getSurname(),
                application.getMiddleName(),
                application.getGender(),
                application.getBirthDate(),
                application.getRegistrationCity(),
                application.getRegistrationAddress(),
                application.getDomicileCity(),
                application.getDomicileAddress(),
                application.getMobileNo(),
                application.getEmail(),
                application.getSecretCode(),
                application.getWorkplace(),
                application.getPosition(),
                application.getUrgent(),
                application.getTkNo(),
                application.getPassportName(),
                application.getPassportSurname(),
                application.getCurrency(),
                application.getCardProduct().getName(),
                application.getPeriod(),
                application.getBranch().getName(),
                String.valueOf(application.getAmountToPay().intValue()),
                "AZN",
                payment.getPaymentDesc()
        );

        StringBuilder output = new StringBuilder(plainTemplate);

        // Generating self certification form questions and the user's answers to them
        try {
            List<CRSAnswer> answers = application.getCrsAnswers();
            int crsQuestionsCount = crsQuestions.size();
            for (int i = 1; i <= crsQuestionsCount; i++) {
                CRSAnswer answer = answers.get(i - 1);
                String row = String.format(
                        "%d. %s%n%s. %s%n",
                        i,
                        crsQuestions.get(i - 1).getQuestion(),
                        answer.getAnswer() == 1 ? "Yes" : "No",
                        validator.isStringNullOrEmpty(answer.getDescription()) ? "" : answer.getDescription().trim()
                );
                output.append(row);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return output.toString();
    }

}
