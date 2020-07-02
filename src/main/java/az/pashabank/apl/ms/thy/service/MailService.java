package az.pashabank.apl.ms.thy.service;


import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.CRSAnswer;
import az.pashabank.apl.ms.thy.model.CRSQuestion;
import az.pashabank.apl.ms.thy.model.CardProductView;
import az.pashabank.apl.ms.thy.model.ElectronCardProductView;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.Payment;
import az.pashabank.apl.ms.thy.model.ThyApplication;
import az.pashabank.apl.ms.thy.model.ThyCouponApplication;
import az.pashabank.apl.ms.thy.utils.Utils;
import az.pashabank.apl.ms.thy.validator.CardValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private static final MainLogger LOGGER = MainLogger.getLogger(MailService.class);

    @Value("${mail.service.username}")
    private String mailUsername;

    @Value("${mail.service.password}")
    private String mailPass;

    @Value("${mail.service.from}")
    private String fromMail;

    /*@Value("${mail.service.message_template.payment.amount_az}")
    private String emailPaymentAmountAzPlainTemplate;

    @Value("${mail.service.message_template.payment.card_az}")
    private String emailPaymentCardAzPlainTemplate;

    @Value("${mail.service.message_template.payment.amount_en}")
    private String emailPaymentAmountEnPlainTemplate;

    @Value("${mail.service.message_template.payment.card_en}")
    private String emailPaymentCardEnPlainTemplate;

    @Value("${mail.service.message_template.payment.end}")
    private String emailPaymentEndPlainTemplate;

    @Value("${mail.service.message_template.payment.card.begin}")
    private String emailPaymentBeginPlainTemplate;

    @Value("${mail.service.message_template.payment.card.coupon_serial_az}")
    private String emailPaymentCouponSerialAzPlainTemplate;

    @Value("${mail.service.message_template.payment.card.middle}")
    private String emailPaymentMiddlePlainTemplate;

    @Value("${mail.service.message_template.payment.card.coupon_serial_en}")
    private String emailPaymentCouponSerialEnPlainTemplate;

    @Value("${mail.service.message_template.payment.coupon.begin}")
    private String emailCouponPaymentBeginPlainTemplate;

    @Value("${mail.service.message_template.payment.coupon.middle}")
    private String emailCouponPaymentMiddlePlainTemplate;

    @Value("${mail.service.message_template.thy_otrs_request}")
    private String emailOtrsRequestPlainTemplate;

    @Value("${mail.service.message_template.thy_informative}")
    private String emailInformativePlainTemplate;*/

    @Value("${mail.service.url}")
    private String plainEmailUrl;

    @Value("${mail.service.multipart.url}")
    private String emailUrl;

    @Autowired
    private CardValidator validator;

    @Autowired
    private MainDao mainDao;

    public OperationResponse sendPlainEmail(Payment payment, String orderType, List<CardProductView> cardProductViews) {
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
            String subject;
            String message;
            String toMail;

            String paymentAmountAzValue = mainDao.getValueFromEmailConfig("payment.amount_az");
            String paymentCardAzValue = mainDao.getValueFromEmailConfig("payment.card_az");
            String paymentAmountEnValue = mainDao.getValueFromEmailConfig("payment.amount_en");
            String paymentCardEnValue = mainDao.getValueFromEmailConfig("payment.card_en");
            String paymentEnd1Value = mainDao.getValueFromEmailConfig("payment.end1");
            String paymentEnd2Value = mainDao.getValueFromEmailConfig("payment.end2");

            int appId = payment.getAppId();

            if (orderType.equals("COUPON")) {
                ThyCouponApplication app = mainDao.getActiveCouponApplication(appId);
                String orderCode = padLeftZeros(app.getId().toString(), 9);

                String couponPaymentBeginValue = mainDao.getValueFromEmailConfig("payment.coupon.begin");
                String couponPaymentMiddle1Value = mainDao.getValueFromEmailConfig("payment.coupon.middle1");
                String couponPaymentMiddle2Value = mainDao.getValueFromEmailConfig("payment.coupon.middle2");
                String couponPaymentEndValue = mainDao.getValueFromEmailConfig("payment.coupon.end");

                StringBuilder couponsValueBuilder = new StringBuilder();
                if (cardProductViews != null) {
                    couponsValueBuilder.append("<br> <table>");
                    cardProductViews.stream().forEach(cardProductView -> {
                        couponsValueBuilder.append(
                                String.format(
                                        "<tr> <td>%s</td> <td>%d</td> <td >%s</td> </tr>",
                                        cardProductView.getCardProductName(), cardProductView.getCardCount(), cardProductView.getTotalAmount()
                                )
                        );
                        if (cardProductView instanceof ElectronCardProductView) {
                            List<String> couponCodes = ((ElectronCardProductView) cardProductView).getCouponCodes();
                            if (couponCodes != null) {
                                for (int i = 0; i < couponCodes.size(); i++) {
                                    couponsValueBuilder.append(
                                            String.format("<tr> <td class=\"right\">%d. %s</td> <td colspan=\"2\"></td> </tr>", i + 1, couponCodes.get(i))
                                    );
                                }
                            }
                        }
                    });
                    couponsValueBuilder.append("</table>");
                }

                StringBuilder plainTemplateBuilder = new StringBuilder(couponPaymentBeginValue);
                plainTemplateBuilder.append(paymentAmountAzValue);
                plainTemplateBuilder.append(paymentCardAzValue);
                plainTemplateBuilder.append(couponPaymentMiddle1Value);
                plainTemplateBuilder.append(couponsValueBuilder.toString());
                plainTemplateBuilder.append(couponPaymentMiddle2Value);
                plainTemplateBuilder.append(paymentAmountEnValue);
                plainTemplateBuilder.append(paymentCardEnValue);
                plainTemplateBuilder.append(couponPaymentEndValue);
                plainTemplateBuilder.append(couponsValueBuilder.toString());
                plainTemplateBuilder.append(paymentEnd2Value);

                message = String.format(
                        plainTemplateBuilder.toString(),
                        /*for Azerbaijan language*/
                        payment.getTransactionId(),
                        amount,
                        payment.getCurrency(),
                        payment.getEcommCardNo(),
                        date,
                        orderCode,
                        /*for English language*/
                        payment.getTransactionId(),
                        amount,
                        payment.getCurrency(),
                        payment.getEcommCardNo(),
                        date,
                        orderCode
                );

                subject = "PASHA THY Miles & Smiles Coupon Order payment receipt";
                toMail = app.getEmail();
            } else {
                ThyApplication app = mainDao.getThyApplication(appId);

                String paymentMethod = null;
                boolean isUrgent = false;

                if (app != null) {
                    paymentMethod = app.getPaymentMethod();
                    isUrgent = app.getUrgent().equals("Y");
                }

                String cardPaymentBeginValue = mainDao.getValueFromEmailConfig("payment.card.begin");

                StringBuilder plainTemplateBuilder = new StringBuilder(cardPaymentBeginValue);
                buildMiddlePlainTemplate(
                        paymentMethod, isUrgent, plainTemplateBuilder, "az",
                        paymentAmountAzValue, paymentCardAzValue, paymentAmountEnValue, paymentCardEnValue
                );
                plainTemplateBuilder.append(paymentEnd1Value);
                plainTemplateBuilder.append(paymentEnd2Value);

                String couponSerialNo = null;
                if (paymentMethod != null && paymentMethod.equalsIgnoreCase("COUPON")) {
                    couponSerialNo = mainDao.getCouponSerialNo(appId);
                }

                if (paymentMethod != null && paymentMethod.equalsIgnoreCase("CARD")) {
                    message = String.format(
                            plainTemplateBuilder.toString(),
                            /*for Azerbaijan language*/
                            payment.getTransactionId(),
                            "Kart",
                            amount,
                            payment.getCurrency(),
                            payment.getEcommCardNo(),
                            date,
                            /*for English language*/
                            payment.getTransactionId(),
                            "Card",
                            amount,
                            payment.getCurrency(),
                            payment.getEcommCardNo(),
                            date
                    );
                } else if (isUrgent) {
                    message = String.format(
                            plainTemplateBuilder.toString(),
                            /*for Azerbaijan language*/
                            payment.getTransactionId(),
                            "Kupon",
                            couponSerialNo != null ? couponSerialNo : "",
                            amount,
                            payment.getCurrency(),
                            payment.getEcommCardNo(),
                            date,
                            /*for English language*/
                            payment.getTransactionId(),
                            "Coupon",
                            couponSerialNo != null ? couponSerialNo : "",
                            amount,
                            payment.getCurrency(),
                            payment.getEcommCardNo(),
                            date
                    );
                } else {
                    message = String.format(
                            plainTemplateBuilder.toString(),
                            /*for Azerbaijan language*/
                            payment.getTransactionId(),
                            "Kupon",
                            couponSerialNo != null ? couponSerialNo : "",
                            amount,
                            payment.getCurrency(),
                            date,
                            /*for English language*/
                            payment.getTransactionId(),
                            "Coupon",
                            couponSerialNo != null ? couponSerialNo : "",
                            amount,
                            payment.getCurrency(),
                            date
                    );
                }

                subject = "PASHA THY Miles & Smiles Card Order payment receipt";
                toMail = app.getEmail();
            }

            map.add("message", URLEncoder.encode(message, "UTF-8"));
            map.add("subject", subject);
            map.add("to", toMail);
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

    private String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    private void buildMiddlePlainTemplate(
            String paymentMethod, boolean isUrgent, StringBuilder plainTemplateBuilder, String lang,
            String paymentAmountAzValue, String paymentCardAzValue, String paymentAmountEnValue, String paymentCardEnValue
    ) {
        String cardPaymentCouponSerialAzValue = mainDao.getValueFromEmailConfig("payment.card.coupon_serial_az");
        String cardPaymentMiddleValue = mainDao.getValueFromEmailConfig("payment.card.middle");
        String cardPaymentCouponSerialEnValue = mainDao.getValueFromEmailConfig("payment.card.coupon_serial_en");

        if (lang.equals("az")) {
            if (paymentMethod != null && paymentMethod.equalsIgnoreCase("COUPON")) {
                plainTemplateBuilder.append(cardPaymentCouponSerialAzValue);
            }
            plainTemplateBuilder.append(paymentAmountAzValue);
            if (isUrgent || (paymentMethod != null && paymentMethod.equalsIgnoreCase("CARD"))) {
                plainTemplateBuilder.append(paymentCardAzValue);
            }
            plainTemplateBuilder.append(cardPaymentMiddleValue);
            buildMiddlePlainTemplate(
                    paymentMethod, isUrgent, plainTemplateBuilder, "en",
                    paymentAmountAzValue, paymentCardAzValue, paymentAmountEnValue, paymentCardEnValue
            );
        } else if (lang.equals("en")) {
            if (paymentMethod != null && paymentMethod.equalsIgnoreCase("COUPON")) {
                plainTemplateBuilder.append(cardPaymentCouponSerialEnValue);
            }
            plainTemplateBuilder.append(paymentAmountEnValue);
            if (isUrgent || (paymentMethod != null && paymentMethod.equalsIgnoreCase("CARD"))) {
                plainTemplateBuilder.append(paymentCardEnValue);
            }
        }
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
            String subject = String.format("Miles&Smiles card order %s %s", application.getName(), application.getSurname());
            map.add("subject", subject);
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
        String thyOtrsRequestValue = mainDao.getValueFromEmailConfig("thy_otrs_request");
        String couponSerialNo = "";
        if (application.getPaymentMethod() != null && application.getPaymentMethod().equalsIgnoreCase("COUPON")) {
            couponSerialNo = mainDao.getCouponSerialNo(application.getId());
        }
        String promoCode = mainDao.getPromoCodeById(application.getPromoCodeId());
        String plainTemplate = String.format(
                thyOtrsRequestValue,
//                application.getResidency(),
//                application.getNationality(),
                application.getName(),
                application.getSurname(),
//                application.getMiddleName() != null ? application.getMiddleName() : "",
                application.getPin() != null ? application.getPin() : "",
//                application.getGender(),
                application.getBirthDate(),
//                application.getRegistrationCity(),
//                application.getRegistrationAddress(),
//                application.getDomicileCity(),
//                application.getDomicileAddress(),
                application.getMobileNo(),
                application.getEmail(),
//                application.getSecretCode(),
//                application.getWorkplace(),
//                application.getPosition(),
                application.getUrgent().equals("1") ? "Y" : "N",
                application.getTkNo(),
                application.getPassportName(),
                application.getPassportSurname(),
                application.getCurrency(),
                application.getCardProduct().getName(),
                application.getPeriod(),
                application.getBranch().getName(),
                application.getPaymentMethod(),
                couponSerialNo != null ? couponSerialNo : "",
                promoCode != null ? promoCode : "",
                String.valueOf(application.getAmountToPay().intValue()),
                "AZN",
                payment.getPaymentDesc() != null ? payment.getPaymentDesc() : ""
//                application.getRoamingNo() != null ? application.getRoamingNo() : ""
        );

        StringBuilder output = new StringBuilder(plainTemplate);

        // Generating self certification form questions and the user's answers to them
        try {
            List<CRSAnswer> answers = application.getCrsAnswers();
            int crsQuestionsCount = crsQuestions.size();
            for (int i = 1; i <= crsQuestionsCount; i++) {
                CRSAnswer answer = answers.get(i - 1);
                String row;
                if (i < 12) {
                    row = String.format(
                            "%d. %s%n%s. %s%n",
                            i,
                            crsQuestions.get(i - 1).getQuestion(),
                            answer.getAnswer() == 1 ? "Yes" : "No",
                            validator.isStringNullOrEmpty(answer.getDescription()) ? "" : answer.getDescription().trim()
                    );
                } else {
                    row = String.format(
                            "%d. %s%n%s%n",
                            i,
                            crsQuestions.get(i - 1).getQuestion(),
                            validator.isStringNullOrEmpty(answer.getDescription()) ? "" : answer.getDescription().trim()
                    );
                }
                output.append(row);
            }
            String row = String.format("%d. %s%n%s.%n", 15, "OWNER_INFORMATION", "Yes");
            output.append(row);
            row = String.format("%d. %s%n%s.%n", 16, "REAL_UBO", "Yes");
            output.append(row);
            row = String.format("%d. %s%n%s.%n", 17, "REASON_FOR_OPENING_ACCOUNT", "MAIN BANKNG RELATIONSHIP");
            output.append(row);
            row = String.format("%d. %s%n%s.%n", 18, "VAT_TAXPAYER", "No");
            output.append(row);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return output.toString();
    }

}
