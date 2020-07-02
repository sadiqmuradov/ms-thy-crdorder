package az.pashabank.apl.ms.thy.service;

import az.pashabank.apl.ms.thy.client.ThyRestClient;
import az.pashabank.apl.ms.thy.constants.BRStatus;
import az.pashabank.apl.ms.thy.constants.PaymentMethod;
import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.entity.City;
import az.pashabank.apl.ms.thy.entity.Country;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.Branch;
import az.pashabank.apl.ms.thy.model.CRSQuestion;
import az.pashabank.apl.ms.thy.model.CardProduct;
import az.pashabank.apl.ms.thy.model.CardProductView;
import az.pashabank.apl.ms.thy.model.CheckOtpRequest;
import az.pashabank.apl.ms.thy.model.CheckPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.CheckPaymentStatusResponse;
import az.pashabank.apl.ms.thy.model.CouponCode;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderRequest;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderResponse;
import az.pashabank.apl.ms.thy.model.Login;
import az.pashabank.apl.ms.thy.model.MobileNumbersByPin;
import az.pashabank.apl.ms.thy.model.MobilePhone;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.Payment;
import az.pashabank.apl.ms.thy.model.PaymentClient;
import az.pashabank.apl.ms.thy.model.SendOtpRequest;
import az.pashabank.apl.ms.thy.model.ThyApplication;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeRequest;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeResponse;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentRequest;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentResponse;
import az.pashabank.apl.ms.thy.model.ecomm.EcommPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.ecomm.EcommPaymentStatusResponse;
import az.pashabank.apl.ms.thy.model.sms.BaseResponse;
import az.pashabank.apl.ms.thy.model.sms.Sms;
import az.pashabank.apl.ms.thy.model.thy.CheckTkRequest;
import az.pashabank.apl.ms.thy.model.thy.CheckTkResponse;
import az.pashabank.apl.ms.thy.model.thy.CheckTkRestResponse;
import az.pashabank.apl.ms.thy.model.thy.EnrollmentChannel;
import az.pashabank.apl.ms.thy.model.thy.MemberData;
import az.pashabank.apl.ms.thy.model.thy.MemberDetails;
import az.pashabank.apl.ms.thy.model.thy.MemberOperations;
import az.pashabank.apl.ms.thy.model.thy.MemberOperationsRequest;
import az.pashabank.apl.ms.thy.model.thy.MemberOperationsResponse;
import az.pashabank.apl.ms.thy.model.thy.MemberProfileData;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyRequest;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyResponse;
import az.pashabank.apl.ms.thy.model.thy.SecurityQuestion;
import az.pashabank.apl.ms.thy.model.thy.SecurityQuestionsRequest;
import az.pashabank.apl.ms.thy.model.thy.SecurityQuestionsRequestHeader;
import az.pashabank.apl.ms.thy.model.thy.SecurityQuestionsResponse;
import az.pashabank.apl.ms.thy.model.thy.SecurityQuestionsRestResponse;
import az.pashabank.apl.ms.thy.model.thy.SpecificComboBox;
import az.pashabank.apl.ms.thy.model.thy.ThyUserInfo;
import az.pashabank.apl.ms.thy.proxy.ECommServiceProxy;
import az.pashabank.apl.ms.thy.proxy.SmsServiceProxy;
import az.pashabank.apl.ms.thy.proxy.ThyServiceProxy;
import az.pashabank.apl.ms.thy.repository.CityRepository;
import az.pashabank.apl.ms.thy.repository.CountryRepository;
import az.pashabank.apl.ms.thy.utils.Utils;
import az.pashabank.apl.ms.thy.validator.CardStepsValidator;
import az.pashabank.apl.ms.thy.validator.CardValidator;
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

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Transactional
public class CardService implements ICardService {

    private static final MainLogger LOGGER = MainLogger.getLogger(CardService.class);

    private static final String WRONG_OTP_4 = "WRONG OTP: OTP code does not match";
    private static final String WRONG_TK_4 = "WRONG TK: TK number is incorrect";
    private static final String WRONG_PAYMENT = "WRONG PAYMENT: Payment is null";
    private static final String TRANSACTION_FAILED = "E-Comm transaction failed";
    private static final String SUCCESS = "Operation is successful";
    private static final String CORRECT_OTP = "SUCCESS: OTP code is correct";
    private static final String CORRECT_TK = "SUCCESS: TK number is correct";

    @Value("${sms.endpoint.url}")
    private String smsUrl;

    @Value("${ecomm.card.clientId}")
    private Integer ecommCardClientId;

    @Value("${ecomm.card.payment.description}")
    private String cardPaymentDescription;

    @Value("${ecomm.coupon.clientId}")
    private Integer ecommCouponClientId;

    @Value("${ecomm.coupon.payment.description}")
    private String couponPaymentDescription;

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

    @Value("${ms-sms.channel}")
    private String smsChannel;

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
    private SmsServiceProxy smsServiceProxy;

    @Autowired
    protected MailService mailService;

    @Autowired
    private CardValidator validator;

    @Autowired
    private CardStepsValidator cardStepsValidator;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

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
                operationResponse.setMessage(localMessageService.get("message.roaming.error.send_otp", locale));
            }
        }
    }

    private boolean sendOtpCodeNew(int otpCode, String phoneNumber, String smsText) {
        phoneNumber = getCorrectPhoneNumber(phoneNumber);
        String smsBody = String.format(smsText, otpCode);
        Sms sms = new Sms(phoneNumber, smsBody, smsChannel);
        BaseResponse baseResponse = smsServiceProxy.sendSMS(sms);
        LOGGER.info("Sending OTP. sms: {}, baseResponse: {}", sms, baseResponse);

        return baseResponse.getStatus() == BRStatus.SUCCESS;
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

    @Override
    public OperationResponse checkOTP(CheckOtpRequest request) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);
        if (validator.isRequestValid(request, operationResponse)) {
            processRequest(request, operationResponse);
        }
        return operationResponse;
    }

    private void processRequest(CheckOtpRequest request, OperationResponse operationResponse) {
        if (mainDao.checkOtpCode(request.getOtp(), request.getPin())) {
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

    public void processTk(String tk, OperationResponse<CheckTkResponse> operationResponse) {
        CheckTkRequest checkTkRequest = new CheckTkRequest(new MemberDetails(tk));
        CheckTkRestResponse checkTkRestResponse = thyRestClient.getMemberDetails(checkTkRequest);
        List<MemberData> memberDataKVPair = checkTkRestResponse.getCheckTkReturn().getMemberDataKVPair();

        if (!memberDataKVPair.isEmpty()) {
            String name = "";
            String surname = "";
            String totalMiles = "";

            for (MemberData memberData : memberDataKVPair) {
                switch (memberData.getKey()) {
                    case "D_OUT_TOTAL_MILES":
                        totalMiles = memberData.getValue();
                        break;
                    case "D_OUT_FFID":
                        break;
                    case "D_OUT_NAME":
                        name = memberData.getValue();
                        break;
                    case "D_OUT_SURNAME":
                        surname = memberData.getValue();
                        break;
                    default:
                        LOGGER.info("Key {} is not mapped in object MemberData", memberData.getKey());
                }
            }

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
                        birthDateDay,
                        birthDateMonth,
                        birthDateYear,
                        request.getEmail(),
                        new MobilePhone(countryCode, phoneNumber),
                        request.getName(),
                        request.getSurname(),
                        request.getNationality(),
                        request.getSecurityQuestion(),
                        request.getSecurityAnswer(),
                        request.getPassword(),
                        "EN",
                        request.getGender().toUpperCase(),
                        request.getCountryCode(),
                        request.getCityCode(),
                        request.getAddress(),
                        request.getIdentityCardNo() != null && !request.getIdentityCardNo().trim().isEmpty() ? request.getIdentityCardNo() : null
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
        CheckTkResponse checkTkResponse = new CheckTkResponse();
        if (!validator.isStringNullOrEmpty(request.getTkNo())) {
            OperationResponse<CheckTkResponse> checkTkOperationResponse = new OperationResponse<>(ResultCode.ERROR);
            processTk(request.getTkNo(), checkTkOperationResponse);
            if (checkTkOperationResponse.getCode() != ResultCode.OK) {
                operationResponse.setMessage(checkTkOperationResponse.getMessage());
                return;
            } else {
                checkTkResponse = checkTkOperationResponse.getData();
            }
        }

        mainDao.getBranchList(lang).stream().filter(b -> b.getBranchCode().equals(request.getBranchCode())).forEach(b -> request.setBranchName(b.getName()));

        CardProduct cardProduct = mainDao.getCardProductById(request.getCardType());
        BigDecimal amountToPay = new BigDecimal(0);

        if (request.getPaymentMethod() == PaymentMethod.CARD) {
            amountToPay = cardProduct.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        if (request.isUrgent()) {
            amountToPay = amountToPay.add(cardProduct.getUrgencyPrice());
        }

        request.setAmountToPay(amountToPay);
        Integer newAppId = mainDao.addNewApplication(request, operationResponse, checkTkResponse);

        if (newAppId != null) {
            if (request.getPaymentMethod() == PaymentMethod.COUPON) {
                LOGGER.info("Set coupon app ID and change status to USED. Coupon ID: {}, App ID: {}", request.getCouponId(), newAppId);
                boolean isCouponUpdated = mainDao.updateCouponAppAndStatus(request.getCouponId(), newAppId);
                if (!isCouponUpdated) {
                    LOGGER.error("MainDao updateCouponAppAndStatus. Coupon ID: {}", request.getCouponId());
                }
            }

            String ecommTransactionId = null;
            if (request.isUrgent() || request.getPaymentMethod() == PaymentMethod.CARD) {
                String ecommAmount = String.valueOf(amountToPay.multiply(new BigDecimal(100)).intValue());
                ECommRegisterPaymentRequest eCommRegisterPaymentRequest = new ECommRegisterPaymentRequest(
                        ecommCardClientId, ecommAmount, "944",
                        request.getIpAddress(), cardPaymentDescription, lang
                );
                ECommRegisterPaymentResponse eCommRegisterPaymentResponse = eCommServiceProxy.registerPayment(eCommRegisterPaymentRequest);
                LOGGER.info("eCommRegisterPaymentResponse: {}", eCommRegisterPaymentResponse);
                ecommTransactionId = eCommRegisterPaymentResponse.getTransactionId();
                processRegisterPaymentResponse(operationResponse, newAppId, eCommRegisterPaymentResponse, ecommTransactionId);
            } else {
                operationResponse.setCode(ResultCode.OK);
                operationResponse.setData(new CreateNewCustomerOrderResponse(newAppId));
            }

            Payment payment = new Payment(
                    new PaymentClient(ecommCardClientId), lang, request.getIpAddress(), "944",
                    amountToPay, cardPaymentDescription, ecommTransactionId, newAppId
            );
            LOGGER.info("registerPayment. payment: {}", payment);
            String paymentTransactionId = mainDao.registerPayment(payment, "CARD");
            LOGGER.info("After registerPayment. paymentTransactionId: {}", paymentTransactionId);
        }
    }

    public void processRegisterPaymentResponse(
            OperationResponse<CreateNewCustomerOrderResponse> operationResponse,
            Integer appId,
            ECommRegisterPaymentResponse eCommRegisterPaymentResponse,
            String ecommTransId
    ) {
        if (eCommRegisterPaymentResponse.getCode().equals("SUCCESS")) {
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
            String encodedEcommTransId = null;
            try {
                encodedEcommTransId = URLEncoder.encode(ecommTransId, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e.getMessage(), e);
            }
            operationResponse.setData(
                    new CreateNewCustomerOrderResponse(
                            appId, eCommRegisterPaymentResponse.getClientHandlerUrl(), encodedEcommTransId
                    )
            );
        } else {
            operationResponse.setMessage(eCommRegisterPaymentResponse.getMessage());
            operationResponse.setData(new CreateNewCustomerOrderResponse(appId));
        }
    }

    @Override
    public OperationResponse<CheckPaymentStatusResponse> checkPaymentStatus(String lang, CheckPaymentStatusRequest request) {
        OperationResponse<CheckPaymentStatusResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        if (validator.isRequestValid(request, operationResponse)) {
            processRequest(request, operationResponse, lang);
        }
        return operationResponse;
    }

    protected void processRequest(CheckPaymentStatusRequest request, OperationResponse<CheckPaymentStatusResponse> operationResponse, String lang) {
        Payment payment = null;
        boolean isEcommTransaction = false;

        if (request.getTransactionId() != null && !request.getTransactionId().trim().isEmpty()) {
            LOGGER.info("getPaymentByEcommTransId. ecommTransId: {}", request.getTransactionId());
            payment = mainDao.getPaymentByEcommTransId(request.getTransactionId(), "CARD");
            LOGGER.info("After getPaymentByEcommTransId. payment: {}", payment);
            isEcommTransaction = true;
        } else if (request.getAppId() != null && request.getAppId() > 0) {
            LOGGER.info("getPaymentByAppId. appId: {}", request.getAppId());
            payment = mainDao.getPaymentByAppId(request.getAppId(), "CARD");
            LOGGER.info("After getPaymentByAppId. payment: {}", payment);
        }

        if (payment == null) {
            operationResponse.setMessage(WRONG_PAYMENT);
            return;
        }

        ThyApplication app = mainDao.getThyApplication(payment.getAppId());
        String cardProductName = app != null ? app.getCardProduct().getName() : null;
        String branchName = app != null ? app.getBranch().getName() : null;
        String amount = Utils.formatBigDecimal(
                2, 2, false,
                payment.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
        );
        String message = null;

        if (isEcommTransaction) {
            EcommPaymentStatusRequest ecommPaymentStatusRequest = new EcommPaymentStatusRequest(request.getTransactionId(), payment.getIpAddress());
            ecommPaymentStatusRequest.setClientId(ecommCardClientId);
            EcommPaymentStatusResponse ecommPaymentStatusResponse = eCommServiceProxy.getPaymentStatus(lang, ecommPaymentStatusRequest);
            LOGGER.info("ecommPaymentStatusResponse: {}", ecommPaymentStatusResponse);
            message = ecommPaymentStatusResponse.getMessage();

            if (ecommPaymentStatusResponse.getCode().equals("SUCCESS")) {
                operationResponse.setCode(ResultCode.OK);
                operationResponse.setMessage(SUCCESS);
            } else {
                operationResponse.setMessage(TRANSACTION_FAILED);
            }
        } else {
            operationResponse.setCode(ResultCode.OK);
        }

        operationResponse.setData(new CheckPaymentStatusResponse(message, cardProductName, branchName, amount));

        if (payment.getStatus().getId() != 1) {
            return;
        }

        if (isEcommTransaction) {
            Map<String, String> parsedPaymentStatus = processEcommMessage(payment, message);
            if (parsedPaymentStatus.get("result").equalsIgnoreCase("OK")) {
                applyPostSuccessPaymentSteps(payment, true, "CARD", null);
            } else {
                LOGGER.info("/ecomm/ok [POST] Update payment status in payments db. Payment: {}", payment);
                mainDao.updatePaymentStatus(payment, false, "Ecomm operation error", "CARD");
                LOGGER.error("Ecomm operation failed: {}", parsedPaymentStatus);
            }
        } else {
            applyPostSuccessPaymentSteps(payment, false, "CARD", null);
        }
    }

    protected Map<String, String> processEcommMessage(Payment payment, String message) {
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

        return parsedPaymentStatus;
    }

    protected void applyPostSuccessPaymentSteps(Payment payment, boolean isEcommPayment, String orderType, List<CardProductView> cardProductViews) {
        String paymentPrefix = "";
        if (isEcommPayment) {
            paymentPrefix = "/ecomm/ok [POST].";
        } else {
            paymentPrefix = "Coupon Payment.";
        }

        int appId = payment.getAppId();

        LOGGER.info(paymentPrefix + " Mark application as paid. ApplicationId: {}", appId);
        if (!mainDao.markApplicationAsPaid(appId, orderType)) {
            LOGGER.error("MainDao markApplicationAsPaid. Application ID: {}, Order type: {}", appId, orderType);
        }

        if (isEcommPayment) {
            LOGGER.info(paymentPrefix + " Update payment status in payments db. Payment: {}", payment);
            mainDao.updatePaymentStatus(payment, true, "Ecomm operation success", orderType);
        }

        payment.setCurrency("AZN");
        LOGGER.info(paymentPrefix + " Send email [payment receipt] to customer. Order type: {}, Payment: {}", orderType, payment);
        mailService.sendPlainEmail(payment, orderType, cardProductViews);
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
        return mainDao.getCardProducts(lang, "CARD");
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
    public List<Country> getCountryList(String lang) {
        List<Country> countryList = new ArrayList<>();
        try {
            countryList = countryRepository.findAllByLangOrderByNameAsc(lang.toUpperCase());
            if (countryList.size() == 0) {
                refreshCountryList(lang);
                countryList = countryRepository.findAllByLangOrderByNameAsc(lang.toUpperCase());
            }
        } catch (Exception ex) {
            LOGGER.error("Exception occured: {} " + ex.toString());
        }
        return countryList;
    }

    @Override
    public void refreshCountryList(String lang) {
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
                country.setLang(lang.toUpperCase());
                if (!country.getRegion().equals("374")) {
                    countryList.add(country);
                }
            }
            Long deletedCount = countryRepository.deleteByLang(lang.toUpperCase());
            countryRepository.saveAll(countryList);
        } catch (Exception ex) {
            LOGGER.error("Exception occured: {} " + ex.toString());
        }
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
        JSONArray jo2;
        try {
            List<City> cityList = getCitiesFromDb(contryCode);
            if (cityList.size() == 0) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String json = cityListRequestBody.replace("-countrycode-", contryCode.toUpperCase());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<String>(json, headers);
                ResponseEntity<String> response = restTemplate.postForEntity(cityListUrl, entity, String.class);
                JSONObject jo = new JSONObject(response.getBody());
                JSONObject jo1 = jo.getJSONObject("return");
                jo2 = jo1.getJSONArray("cityList");
                for (int i = 0; i < jo2.length(); i++) {
                    JSONObject obj = jo2.getJSONObject(i);
                    City city = new City();
                    city.setCode(obj.getString("code"));
                    city.setName(obj.getString("name"));
                    city.setCountryCode(contryCode.toUpperCase());
                    cityList.add(city);
                }
                cityRepository.saveAll(cityList);
            } else {
                jo2 = new JSONArray(cityList);
                for (int i = 0; i < jo2.length(); i++) {
                    jo2.getJSONObject(i).remove("id");
                    jo2.getJSONObject(i).remove("countryCode");
                }
            }
            return jo2.toString();
        } catch (Exception ex) {
            LOGGER.error("Exception occured: {} " + ex.toString());
            return null;
        }
    }

    private List<City> getCitiesFromDb(String contryCode) {
        return cityRepository.findAllByCountryCodeOrderByNameAsc(contryCode.toUpperCase());
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

    @Override
    public OperationResponse<ValidateCouponCodeResponse> validateCouponCode(ValidateCouponCodeRequest validateCouponCodeRequest) {
        OperationResponse<ValidateCouponCodeResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        CouponCode couponCode = mainDao.getCouponCode(validateCouponCodeRequest);
        if (!Objects.isNull(couponCode)) {
            if (couponCode.getStatus() == 1 || couponCode.getStatus() == 2) {
                ValidateCouponCodeResponse validateCouponCodeResponse = new ValidateCouponCodeResponse();
                validateCouponCodeResponse.setCouponId(couponCode.getId());
                validateCouponCodeResponse.setCouponPrice(couponCode.getCouponPrice());
                operationResponse.setData(validateCouponCodeResponse);
                operationResponse.setCode(ResultCode.OK);
            } else {
                operationResponse.setMessage("Invalid coupon specified");
                LOGGER.info("CouponCode: coupon status is not 0 {} " + validateCouponCodeRequest);
            }
        } else {
            operationResponse.setMessage("Invalid coupon specified");
            LOGGER.info("CouponCode: coupon not found {} " + validateCouponCodeRequest);
        }

        return operationResponse;
    }
}
