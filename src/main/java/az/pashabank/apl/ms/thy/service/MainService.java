package az.pashabank.apl.ms.thy.service;

import az.pashabank.apl.ms.thy.client.ThyRestClient;
import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.logger.UFCLogger;
import az.pashabank.apl.ms.thy.model.*;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentRequest;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentResponse;
import az.pashabank.apl.ms.thy.model.ecomm.EcommPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.ecomm.EcommPaymentStatusResponse;
import az.pashabank.apl.ms.thy.model.thy.*;
import az.pashabank.apl.ms.thy.proxy.ECommServiceProxy;
import az.pashabank.apl.ms.thy.proxy.ThyServiceProxy;
import az.pashabank.apl.ms.thy.utils.Utils;
import az.pashabank.apl.ms.thy.validator.MainValidator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class MainService implements IMainService {

    private static final UFCLogger LOGGER = UFCLogger.getLogger(MainService.class);

    private static final String WRONG_OTP_4 = "WRONG OTP: OTP code does not match";
    private static final String WRONG_TK_4 = "WRONG TK: TK number is incorrect";
    private static final String WRONG_PAYMENT = "WRONG PAYMENT: Card payment is null";
    private static final String TRANSACTION_FAILED = "Transaction failed";
    private static final String SUCCESS = "Operation is successful";
    private static final String CORRECT_OTP = "SUCCESS: OTP code is correct";
    private static final String CORRECT_TK = "SUCCESS: TK number is correct";

    private static final int THY_APP_ID = 145;

    @Value("${sms.endpoint.url}")
    private String smsUrl;

    @Value("${ecomm.card.clientId}")
    private Integer ecommClientId;

    @Value("${ecomm.card.payment.description}")
    private String paymentDescription;

    @Value("${url.get_city_list}")
    private String cityListUrl;

    @Value("${request.body.city_list}")
    private String cityListRequestBody;

    @Value("${url.get_country_list}")
    private String countryListUrl;

    @Value("${request.body.country_list}")
    private String countryListRequestBody;

    @Value("${ldap.login.url}")
    private String ldapLoginUrl;

    @Autowired
    private MainDao mainDao;

    @Autowired
    private LocalMessageService localMessageService;

    @Autowired
    private ThyRestClient thyRestClient;

    @Autowired
    private ECommServiceProxy eCommServiceProxy;

    @Autowired
    private ThyServiceProxy thyServiceProxy;

    @Autowired
    protected MailService mailService;

    @Autowired
    private MainValidator validator;

    @Override
    public OperationResponse<MobileNumbersByPin> getMobileNumbersByPin(String pin) {
        OperationResponse<MobileNumbersByPin> operationResponse = new OperationResponse<>(ResultCode.ERROR);

        if (validator.isPinValid(pin, operationResponse)) {
            processPin(pin, operationResponse);
        }

        return operationResponse;
    }

    private void processPin(String pin, OperationResponse<MobileNumbersByPin> operationResponse) {
        MobileNumbersByPin mobileNumbersByPin = mainDao.getMobileNumbersByPin(pin, operationResponse);

        if (mobileNumbersByPin != null) {
            operationResponse.setData(mobileNumbersByPin);
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
        }
    }

    @Override
    public OperationResponse sendOTP(SendOtpRequest request) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);

        if (validator.isRequestValid(request, operationResponse)) {
            processRequest(request, operationResponse);
        }

        return operationResponse;
    }

    private void processRequest(SendOtpRequest request, OperationResponse operationResponse) {
        int randomNum = ThreadLocalRandom.current().nextInt(10000, 100000);

        if (mainDao.insertOtpCode(randomNum, request.getPin())) {
            Locale locale = new Locale(request.getLang());
            String smsText = localMessageService.get("otp.sms.text", locale);

            if (sendOtpCode(randomNum, request.getMobileNo(), smsText)) {
                operationResponse.setCode(ResultCode.OK);
                operationResponse.setMessage(SUCCESS);
            } else {
                operationResponse.setMessage(localMessageService.get("label.login.problem_send_otp", locale));
            }
        }
    }

    @Override
    public OperationResponse checkOTP(CheckOtpRequest request) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);

        if (validator.isRequestValid(request, operationResponse)) {
            processRequest(request, operationResponse);
        }

        return operationResponse;
    }

    private void processRequest(CheckOtpRequest request, OperationResponse operationResponse) {
        if (mainDao.checkOtpCode(request.getOtp(), request.getPin(), operationResponse)) {
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(CORRECT_OTP);
        } else {
            operationResponse.setMessage(WRONG_OTP_4);
        }
    }

    @Override
    public OperationResponse<CheckTkResponse> checkTK(String tk) {
        OperationResponse<CheckTkResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);

        if (validator.isTkValid(tk, operationResponse)) {
            processTk(tk, operationResponse);
        }

        return operationResponse;
    }

    private void processTk(String tk, OperationResponse<CheckTkResponse> operationResponse) {
        CheckTkRequest checkTkRequest = new CheckTkRequest(new MemberDetails(tk));
        CheckTkRestResponse checkTkRestResponse = thyRestClient.getMemberDetails(checkTkRequest);
        List<MemberData> memberDataKVPair = checkTkRestResponse.getCheckTkReturn().getMemberDataKVPair();

        if (!memberDataKVPair.isEmpty()) {
            String name = memberDataKVPair.get(1).getValue();
            String surname = memberDataKVPair.get(2).getValue();
            String totalMiles = memberDataKVPair.get(0).getValue();
            CheckTkResponse checkTkResponse = new CheckTkResponse(name, surname, totalMiles);
            operationResponse.setData(checkTkResponse);

            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(CORRECT_TK);
        } else {
            operationResponse.setMessage(WRONG_TK_4);
        }
    }

    @Override
    public String resendSms(int userId) {
        String sendSmsPasswordText = getDynamicVal("send_sms_user_password_text");
        if (sendSmsPasswordText != null) {
            ThyUserInfo thyUserInfo = mainDao.getThyUserInfoById(userId);
            if (thyUserInfo.getPhoneNumber() != null && thyUserInfo.getTkNumber() != null) {
                String smsText = sendSmsPasswordText.replace("_tkno_", thyUserInfo.getTkNumber()).replace("_password_", thyUserInfo.getPassword());
                if (sendPassword(thyUserInfo.getPhoneNumber(), smsText)) {
                    return "OK";
                } else {
                    return "ERROR";
                }
            } else {
                LOGGER.error("Phone number is: {}", thyUserInfo.getPhoneNumber());
                LOGGER.error("Tk number is: {}", thyUserInfo.getTkNumber());
                return "ERROR";
            }
        } else {
            LOGGER.error("Can't key send_sms_user_password_text value from DB! ");
            return "ERROR";
        }
    }

    @Override
    public OperationResponse<RegisterCustomerInThyResponse> registerCustomerInThy(RegisterCustomerInThyRequest request) {
        OperationResponse<RegisterCustomerInThyResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);

        if (validator.isRequestValid(request, operationResponse)) {
            processRequest(request, operationResponse);
        }

        return operationResponse;
    }

    @Override
    public OperationResponse<RegisterCustomerInThyResponse> registerCustomerInThyForUi(RegisterCustomerInThyRequest request) {
        OperationResponse<RegisterCustomerInThyResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);

        if (validator.isRequestValidForUi(request, operationResponse)) {
            processRequest(request, operationResponse);
        }

        return operationResponse;
    }

    private void processRequest(RegisterCustomerInThyRequest request, OperationResponse<RegisterCustomerInThyResponse> operationResponse) {
        String birthDate = request.getBirthDate();
        String birthDateDay = birthDate.substring(0, 2);
        String birthDateMonth = birthDate.substring(3, 5);
        String birthDateYear = birthDate.substring(6);

        String mobileNo = request.getMobileNo();
        String countryCode = mobileNo.substring(1, 4);
        String phoneNumber = mobileNo.substring(4);

        MemberProfileData memberProfileData =
                new MemberProfileData(
                        birthDateDay, birthDateMonth, birthDateYear,
                        request.getEmail(), new MobilePhone(countryCode, phoneNumber), request.getName(), request.getSurname(),
                        request.getNationality(), request.getSecurityQuestion(), request.getSecurityAnswer(), request.getPassword(),
                        "EN", request.getGender().toUpperCase(), request.getCountryCode(), request.getCityCode(), request.getAddress()
                );

        MemberOperations memberOperations = new MemberOperations("CREATE", new EnrollmentChannel(15, 196), memberProfileData);
        MemberOperationsRequest memberOperationsRequest = new MemberOperationsRequest(memberOperations);
        MemberOperationsResponse memberOperationsResponse = thyRestClient.createMember(memberOperationsRequest);
        memberProfileData = memberOperationsResponse.getMemberOperationsReturn().getMemberProfileData();
        operationResponse.setData(new RegisterCustomerInThyResponse(memberProfileData));

        if (memberProfileData != null) {
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
        } else {
            operationResponse.setMessage(memberOperationsResponse.getResponseHeader().getMessages().get(0).getLocalizedDescription());
        }
    }

    @Override
    public OperationResponse<CreateNewCustomerOrderResponse> createNewCustomerOrder(CreateNewCustomerOrderRequest request, String lang) {
        OperationResponse<CreateNewCustomerOrderResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);

        if (validator.isRequestValid(request, lang, operationResponse)) {
            processRequest(request, lang, operationResponse);
        }

        return operationResponse;
    }

    private void processRequest(CreateNewCustomerOrderRequest request, String lang, OperationResponse<CreateNewCustomerOrderResponse> operationResponse) {
        if (!validator.isStringNullOrEmpty(request.getTkNo())) {
            OperationResponse<CheckTkResponse> checkTkOperationResponse = new OperationResponse<>(ResultCode.ERROR);
            processTk(request.getTkNo(), checkTkOperationResponse);
            if (checkTkOperationResponse.getCode() != ResultCode.OK) {
                operationResponse.setMessage(checkTkOperationResponse.getMessage());
                return;
            } else {
                CheckTkResponse checkTkResponse = checkTkOperationResponse.getData();
                request.setPassportName(checkTkResponse != null ? checkTkResponse.getName() : null);
                request.setPassportSurname(checkTkResponse != null ? checkTkResponse.getSurname() : null);
            }
        } else {
            RegisterCustomerInThyRequest registerCustomerInThyRequest = new RegisterCustomerInThyRequest(
                    request.getPassportName(), request.getPassportSurname(), request.getNationality(), request.getBirthDate(),
                    request.getEmail(), request.getMobileNo(), request.getSecurityQuestion(), request.getSecurityAnswer(),
                    request.getPassword(), request.getRepeatPassword(), request.getCorrespondenceLanguage(), request.getGender(),
                    request.getNationality(), request.getRegistrationCity(), request.getRegistrationAddress()
            );
            OperationResponse<RegisterCustomerInThyResponse> registerCustomerInThyResponse = new OperationResponse<>(ResultCode.ERROR);
            processRequest(registerCustomerInThyRequest, registerCustomerInThyResponse);
            if (registerCustomerInThyResponse.getCode() != ResultCode.OK) {
                operationResponse.setMessage(registerCustomerInThyResponse.getMessage());
                return;
            } else {
                MemberProfileData memberProfileData = registerCustomerInThyResponse.getData().getMemberProfileData();
                request.setTkNo(memberProfileData != null ? memberProfileData.getMemberId() : null);
            }
        }

        mainDao.getBranchList(lang).stream().filter(b -> b.getBranchCode().equals(request.getBranchCode())).forEach(b -> request.setBranchName(b.getName()));

        CardProduct cardProduct = mainDao.getCardProductById(request.getCardType());
        BigDecimal cardProductPrice = cardProduct.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP);

        if (request.getUrgent()) {
            cardProductPrice = cardProductPrice.add(cardProduct.getUrgencyPrice());
        }
        request.setAmountToPay(cardProductPrice);
        Integer newAppId = mainDao.addNewApplication(request, operationResponse);

        if (newAppId != null) {
            ECommRegisterPaymentRequest eCommRegisterPaymentRequest = new ECommRegisterPaymentRequest();
            String ecommAmount = String.valueOf(cardProductPrice.multiply(new BigDecimal(100)).intValue());
            eCommRegisterPaymentRequest.setAmountInCents(ecommAmount);
            eCommRegisterPaymentRequest.setClientId(ecommClientId);
            eCommRegisterPaymentRequest.setCurrencyIsoName("944");
            eCommRegisterPaymentRequest.setDescription(paymentDescription);
            eCommRegisterPaymentRequest.setLanguage(lang);
            eCommRegisterPaymentRequest.setIpAddress(request.getIpAddress());
            ECommRegisterPaymentResponse eCommRegisterPaymentResponse = eCommServiceProxy.registerPayment(eCommRegisterPaymentRequest);
            String ecommTransactionId = eCommRegisterPaymentResponse.getTransactionId();

            Payment payment = new Payment();
            payment.setEcommTransaction(ecommTransactionId);
            payment.setClient(new PaymentClient(ecommClientId));
            payment.setAmount(cardProductPrice);
            payment.setDescription(paymentDescription);
            payment.setLanguage(lang);
            payment.setIpAddress(request.getIpAddress());
            payment.setCurrency("944");
            payment.setAppId(newAppId);

            LOGGER.info("registerPayment. payment: {}", payment);
            String paymentTransactionId = mainDao.registerPayment(payment);
            LOGGER.info("After registerPayment. paymentTransactionId: {}", paymentTransactionId);
//            payment = mainDao.getNewPaymentByEcommTransId(ecommTransactionId);

//            List<PaymentField> paymentFields = new ArrayList<>();
//            paymentFields.add(new PaymentField(payment.getId(), THY_APP_ID, String.valueOf(newAppId)));
//            LOGGER.info("insertPaymentFields. payment id: {}", payment.getId());
//            mainDao.insertPaymentFields(paymentFields, payment.getId());

            if (eCommRegisterPaymentResponse.getCode().equals("SUCCESS")) {
                try {
                    ecommTransactionId = URLEncoder.encode(ecommTransactionId, StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error(e.getMessage(), e);
                }
                operationResponse.setData(
                        new CreateNewCustomerOrderResponse(
                                newAppId, eCommRegisterPaymentResponse.getClientHandlerUrl(), ecommTransactionId
                        )
                );
                operationResponse.setCode(ResultCode.OK);
                operationResponse.setMessage(SUCCESS);
            } else {
                operationResponse.setData(new CreateNewCustomerOrderResponse(newAppId));
                operationResponse.setMessage(eCommRegisterPaymentResponse.getMessage());
            }
        }
    }

    @Override
    public OperationResponse<CheckPaymentStatusResponse> checkPaymentStatus(CheckPaymentStatusRequest request) {
        OperationResponse<CheckPaymentStatusResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);

        if (validator.isRequestValid(request, operationResponse)) {
            processRequest(request, operationResponse);
        }

        return operationResponse;
    }

    private void processRequest(CheckPaymentStatusRequest request, OperationResponse<CheckPaymentStatusResponse> operationResponse) {
        LOGGER.info("getPaymentByEcommTransId. ecommTransId: {}", request.getTransactionId());
        Payment payment = mainDao.getPaymentByEcommTransId(request.getTransactionId());
        LOGGER.info("After getPaymentByEcommTransId. payment: {}", payment);

        if (payment == null) {
            operationResponse.setMessage(WRONG_PAYMENT);
            return;
        }

        int appId = payment.getAppId();
        /*List<PaymentField> paymentFields = mainDao.getPaymentFieldsByPaymentId(payment.getId());
        for (PaymentField paymentField : paymentFields) {
            int paymentFieldId = paymentField.getId();
            if (paymentFieldId == THY_APP_ID) {
                appId = Integer.parseInt(paymentField.getValue());
                break;
            }
        }*/

        ThyApplication app = mainDao.getThyApplication(appId);
        String cardProductName = app != null ? app.getCardProduct().getName() : null;
        String branchName = app != null ? app.getBranch().getName() : null;
        String amount = Utils.formatBigDecimal(
                2, 2, false,
                payment.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
        );

        EcommPaymentStatusRequest ecommPaymentStatusRequest = new EcommPaymentStatusRequest(ecommClientId, request.getTransactionId(), request.getIpAddress());
        EcommPaymentStatusResponse ecommPaymentStatusResponse = eCommServiceProxy.getPaymentStatus(ecommPaymentStatusRequest);
        String message = ecommPaymentStatusResponse.getMessage();

        operationResponse.setData(new CheckPaymentStatusResponse(message, cardProductName, branchName, amount));
        if (ecommPaymentStatusResponse.getCode().equals("SUCCESS")) {
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
        } else {
            operationResponse.setMessage(TRANSACTION_FAILED);
        }

        if (payment.getStatus().getId() != 1) {
            return;
        }

        String messageAsMapFormat = message.substring(1, message.length() - 1);
        Map<String, String> parsedPaymentStatus = Arrays.stream(messageAsMapFormat.split(","))
                .map(s -> s.split("=", 2))
                .collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()));

        payment.setEcommResult(parsedPaymentStatus.get("result"));
        payment.setEcommResultCode(parsedPaymentStatus.get("result_code"));
        payment.setEcommRrn(parsedPaymentStatus.get("rrn"));
        payment.setEcommApprovalCode(parsedPaymentStatus.get("approval_code"));
        payment.setEcomm3dSecure(parsedPaymentStatus.get("3dsecure"));
        payment.setEcommCardNo(parsedPaymentStatus.get("card_number"));

        if (parsedPaymentStatus.get("result").equalsIgnoreCase("OK")) {
            payment.setCurrency("AZN");
            LOGGER.info("/ecomm/ok [POST] Mark application as paid. ApplicationId: {}", appId);
            if (!mainDao.markApplicationAsPaid(appId)) {
                LOGGER.error("markApplicationAsPaid. Application ID: {}", appId);
            }
            LOGGER.info("/ecomm/ok [POST] Update payment status in payments db. Payment: {}", payment);
            mainDao.updatePaymentStatus(payment, true, "Ecomm operation success");

            String email = mainDao.getEmailFromApp(appId);
            LOGGER.info("/ecomm/ok [POST] Send email [payment receipt] to customer. Payment: {}", payment);
            mailService.sendPlainEmail(payment, email);
        } else {
            LOGGER.info("/ecomm/ok [POST] Update payment status in payments db. Payment: {}", payment);
            mainDao.updatePaymentStatus(payment, false, "Ecomm operation error");
            LOGGER.error("Ecomm operation failed: {}", parsedPaymentStatus);
        }
    }

    private boolean sendOtpCode(int otpCode, String phoneNumber, String smsText) {
        phoneNumber = getCorrectPhoneNumber(phoneNumber);

        RestTemplate restTemplate = new RestTemplate();
        String smsBody = String.format(smsText, otpCode);
        String url = String.format(smsUrl, phoneNumber, smsBody);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        LOGGER.info(
                "Sending OTP. " +
                        "Request - phoneNumber: " + phoneNumber + ", otpCode: " + otpCode + ", " +
                        "Response - status: " + response.getStatusCodeValue() + ", body: " + response.getBody()
        );

        return response.getStatusCodeValue() == 202 && response.getBody().startsWith("0");
    }

    public boolean sendPassword(String phoneNumber, String smsText) {
        try {
            phoneNumber = getCorrectPhoneNumber(phoneNumber);

            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(smsUrl, phoneNumber, smsText);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            LOGGER.info(
                    "Sending PASSWORD. " +
                            "Request - phoneNumber: " + phoneNumber + ", " +
                            "Request - smsText: " + phoneNumber + ", " +
                            "Response - status: " + response.getStatusCodeValue() + ", body: " + response.getBody()
            );
            return response.getStatusCodeValue() == 202 && response.getBody().startsWith("0");
        } catch (Exception ex) {
            LOGGER.error("Exception occured in sendPassword{}", ex.toString());
            return false;
        }
    }

    private String getCorrectPhoneNumber(String phoneNumber) {
        StringBuilder sb = new StringBuilder(phoneNumber);
        int phoneLength = phoneNumber.length();

        if (phoneLength == 9) {
            sb.insert(0, "+994");
        }
        if (phoneLength == 10) {
            sb.deleteCharAt(0).insert(0, "+994");
        }
        if (phoneLength == 12) {
            sb.insert(0, "+");
        }

        return sb.toString();
    }

    @Override
    public String addTkUserInfo(RegisterCustomerInThyRequest request, String tkNumber) {
        OperationResponse<RegisterCustomerInThyResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        LOGGER.info("operationResponse {} ", operationResponse);
        if (validator.isRequestValidForUi(request, operationResponse)) {
            return mainDao.addUserInfo(request, tkNumber);
        } else {
            return operationResponse.getMessage();
        }
    }

    @Override
    public boolean updateTHYuserInfo(String email, String tkNo, int status) {
        return mainDao.updateTHYuserInfo(email, tkNo, status);
    }

    @Override
    public List<CRSQuestion> getCRSQuestions(String lang) {
        return mainDao.getCRSQuestions(lang);
    }

    @Override
    public List<Branch> getBranchList(String lang) {
        return mainDao.getBranchList(lang);
    }

    @Override
    public List<CardProduct> getCardProducts(String lang) {
        return mainDao.getCardProducts(lang);
    }

    @Override
    public OperationResponse<SecurityQuestionsResponse> getSecurityQuestions(String lang) {
        OperationResponse<SecurityQuestionsResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);

        SecurityQuestionsRequest securityQuestionsRequest = new SecurityQuestionsRequest(
                new SpecificComboBox("SECURITY_QUESTION"), new SecurityQuestionsRequestHeader(lang)
        );
        SecurityQuestionsRestResponse securityQuestionsRestResponse = thyRestClient.getSecurityQuestions(securityQuestionsRequest);
//        SecurityQuestionsRestResponse securityQuestionsRestResponse = thyServiceProxy.getSecurityQuestions(securityQuestionsRequest);
        List<SecurityQuestion> securityQuestions = securityQuestionsRestResponse.getSecurityQuestionsReturn().getSpecificComboBoxListArray();
        operationResponse.setData(new SecurityQuestionsResponse(securityQuestions));

        if (!securityQuestions.isEmpty()) {
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
        } else {
            operationResponse.setMessage(securityQuestionsRestResponse.getResponseHeader().getMessages().get(0).getLocalizedDescription());
        }

        return operationResponse;
    }

    @Override
    public String getDynamicVal(String key) {
        return mainDao.getDynamicVal(key);
    }

    @Override
    public List<Country> getCountryList() {
        List<Country> countryList = new ArrayList<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            String json = countryListRequestBody;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(countryListUrl, entity, String.class);
            JSONObject jo = new JSONObject(response.getBody());
            JSONObject jo1 = jo.getJSONObject("return");
            JSONArray jo2 = jo1.getJSONArray("countryList");

            for (int i = 0; i < jo2.length(); i++) {
                JSONObject obj = jo2.getJSONObject(i);
                Country country = new Country();
                country.setName(obj.getString("name"));
                country.setRegion(obj.getString("region"));
                country.setCode(obj.getString("code"));
                countryList.add(country);
            }
        } catch (Exception ex) {
            LOGGER.error("Exception occured: {} " + ex.toString());
        }
        return countryList;
    }

    @Override
    public List<ThyUserInfo> getThyUsersList(int page) {
        List<ThyUserInfo> thyUserInfoList = new ArrayList<>();
        try {
            thyUserInfoList = mainDao.getThyUsersList(page);
        } catch (Exception ex) {
            LOGGER.error("Exception occured: {} " + ex.toString());
        }
        return thyUserInfoList;
    }

    @Override
    public String getCity(String contryCode) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            String json = cityListRequestBody.replace("-countrycode-", contryCode.toUpperCase());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(cityListUrl, entity, String.class);
            JSONObject jo = new JSONObject(response.getBody());
            JSONObject jo1 = jo.getJSONObject("return");
            JSONArray jo2 = jo1.getJSONArray("cityList");
            return jo2.toString();
        } catch (Exception ex) {
            LOGGER.error("Exception occured: {} " + ex.toString());
            return null;
        }
    }

    @Override
    public String login(Login login) {
        RestTemplate restTemplate = new RestTemplate();
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", login.getUsername());
        map.add("password", login.getPassword());
        map.add("lang", "az");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String result = restTemplate.postForObject(ldapLoginUrl, request, String.class);
        JSONObject jsonObject = new JSONObject(result);
        return jsonObject.get("errorDesc").toString();
    }
}
