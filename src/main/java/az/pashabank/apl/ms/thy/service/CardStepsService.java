package az.pashabank.apl.ms.thy.service;

import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.dao.CardStepsDao;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.entity.CRSAnswerEntity;
import az.pashabank.apl.ms.thy.entity.NetGrossIncomeEntity;
import az.pashabank.apl.ms.thy.entity.PromoCodeEntity;
import az.pashabank.apl.ms.thy.entity.SourceOfIncomeEntity;
import az.pashabank.apl.ms.thy.entity.ThyApplicationEntity;
import az.pashabank.apl.ms.thy.entity.UploadWrapperEntity;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderResponse;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderStep1Request;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderStep2Request;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderStep3Request;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderResponse;
import az.pashabank.apl.ms.thy.model.GetAnnualIncomeValuesResponse;
import az.pashabank.apl.ms.thy.model.GetPromoCodeRequest;
import az.pashabank.apl.ms.thy.model.GetPromoCodeResponse;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.Payment;
import az.pashabank.apl.ms.thy.model.PaymentClient;
import az.pashabank.apl.ms.thy.model.UploadFilesRequest;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentRequest;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentResponse;
import az.pashabank.apl.ms.thy.proxy.ECommServiceProxy;
import az.pashabank.apl.ms.thy.validator.CardStepsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class CardStepsService implements ICardStepsService {

    private static final MainLogger LOGGER = MainLogger.getLogger(CardStepsService.class);

    private static final String SUCCESS = "Operation is successful";
    private static final String WRONG_UPDATE = "Application is already active, CAN NOT be changed";

    @Value("${ecomm.card.clientId}")
    private Integer ecommCardClientId;

    @Value("${ecomm.card.payment.description}")
    private String cardPaymentDescription;

    @Autowired
    private CardStepsValidator cardStepsValidator;

    @Autowired
    private CardStepsDao cardStepsDao;

    @Autowired
    private LocalMessageService localMessageService;

    @Autowired
    private MainDao mainDao;

    @Autowired
    private ECommServiceProxy eCommServiceProxy;

    @Autowired
    private CardService cardService;

    @Override
    public OperationResponse<CreateNewCardOrderResponse> createNewCardOrderStep1(CreateNewCardOrderStep1Request request, String lang) {
        OperationResponse<CreateNewCardOrderResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        if (cardStepsValidator.isRequestValid(request, lang, operationResponse)) {
            processStep1Request(request, lang, operationResponse);
        }
        return operationResponse;
    }

    private void processStep1Request(CreateNewCardOrderStep1Request request, String lang, OperationResponse<CreateNewCardOrderResponse> operationResponse) {
        ThyApplicationEntity appEntity = new ThyApplicationEntity(
                request.getAppId(), request.getName(), request.getSurname(),
                request.getMobileNo(), request.getEmail(), 1
        );
        appEntity = saveAppAndProcessResponse(appEntity, lang, operationResponse);
        if (operationResponse.getCode() == ResultCode.OK) {
            operationResponse.setData(new CreateNewCardOrderResponse(appEntity.getId()));
        }
    }

    @Override
    public OperationResponse createNewCardOrderStep2(CreateNewCardOrderStep2Request request, String lang) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);
        if (cardStepsValidator.isRequestValid(request, lang, operationResponse)) {
            processStep2Request(request, lang, operationResponse);
        }
        return operationResponse;
    }

    private void processStep2Request(CreateNewCardOrderStep2Request request, String lang, OperationResponse operationResponse) {
        ThyApplicationEntity appEntity = cardStepsDao.getApplicationById(request.getAppId());
        if (!appEntity.isActive()) {
            appEntity.setCardProductId(request.getCardType());
            appEntity.setPaymentMethod(request.getPaymentMethod().toString());
            appEntity.setCouponId(request.getCouponId());
            appEntity.setBranchCode(request.getBranchCode());
            appEntity.setBranchName(request.getBranchName());
            appEntity.setUrgent(request.isUrgent());
            appEntity.setPromoCodeId(request.getPromoCodeId());
            appEntity.setPeriod(3);
            appEntity.setCurrency("AZN");
            appEntity.setAmountToPay(request.getAmountToPay());
            appEntity.setStep(2);
            saveAppAndProcessResponse(appEntity, lang, operationResponse);
        } else {
            operationResponse.setMessage(WRONG_UPDATE);
        }
    }


    @Override
    public OperationResponse<CreateNewCustomerOrderResponse> createNewCardOrderStep3(CreateNewCardOrderStep3Request request, String lang) {
        OperationResponse<CreateNewCustomerOrderResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        if (cardStepsValidator.isRequestValid(request, lang, operationResponse)) {
            processStep3Request(request, lang, operationResponse);
        }
        return operationResponse;
    }

    private void processStep3Request(CreateNewCardOrderStep3Request request, String lang, OperationResponse<CreateNewCustomerOrderResponse> operationResponse) {
        ThyApplicationEntity appEntity = cardStepsDao.getApplicationById(request.getAppId());
        if (!appEntity.isActive()) {
            appEntity.setName(request.getName());
            appEntity.setSurname(request.getSurname());
            appEntity.setMobileNumber(request.getMobileNo());
            appEntity.setEmail(request.getEmail());
            appEntity.setPin(request.getPin());
            appEntity.setBirthDate(request.getBirthDate());
            appEntity.setTkNo(request.getTkNo());
            appEntity.setPassportName(request.getPassportName());
            appEntity.setPassportSurname(request.getPassportSurname());
            appEntity.setAcceptedTerms(request.getAcceptedTerms() == 1 ? true : false);
            appEntity.setAcceptedGsa(request.getAcceptedGsa() == 1 ? true : false);
            appEntity.setAcceptedFatca(request.getAcceptedTerms() == 1 ? true : false);
            appEntity.setCrsAnswers(request.getCrsAnswerEntities());
            for (CRSAnswerEntity crsAnswerEntity : appEntity.getCrsAnswers()) {
                crsAnswerEntity.setApp(appEntity);
            }
            appEntity.setActive(true);
            appEntity.setStep(3);
            OperationResponse saveAppResponse = new OperationResponse(ResultCode.ERROR);
            saveAppAndProcessResponse(appEntity, lang, saveAppResponse);

            if (saveAppResponse.getCode() == ResultCode.OK) {
                Integer newAppId = appEntity.getId();

                if (appEntity.getPaymentMethod().equals("COUPON")) {
                    LOGGER.info("Set coupon app ID and change status to USED. Coupon ID: {}, App ID: {}", appEntity.getCouponId(), newAppId);
                    boolean isCouponUpdated = mainDao.updateCouponAppAndStatus(appEntity.getCouponId(), newAppId);
                    if (!isCouponUpdated) {
                        LOGGER.error("MainDao updateCouponAppAndStatus. Coupon ID: {}", appEntity.getCouponId());
                    }
                }

                String ecommTransactionId = null;
                if (appEntity.isUrgent() || appEntity.getPaymentMethod().equals("CARD")) {
                    String ecommAmount = String.valueOf(appEntity.getAmountToPay() * 100);
                    ECommRegisterPaymentRequest eCommRegisterPaymentRequest = new ECommRegisterPaymentRequest(
                            ecommCardClientId, ecommAmount, "944",
                            request.getIpAddress(), cardPaymentDescription, lang
                    );
                    ECommRegisterPaymentResponse eCommRegisterPaymentResponse = eCommServiceProxy.registerPayment(eCommRegisterPaymentRequest);
                    LOGGER.info("eCommRegisterPaymentResponse: {}", eCommRegisterPaymentResponse);
                    ecommTransactionId = eCommRegisterPaymentResponse.getTransactionId();
                    cardService.processRegisterPaymentResponse(operationResponse, newAppId, eCommRegisterPaymentResponse, ecommTransactionId);
                    if (operationResponse.getCode() == ResultCode.OK) {
                        registerDBPayment(lang, request.getIpAddress(), new BigDecimal(appEntity.getAmountToPay()), ecommTransactionId, newAppId);
                    }
                } else {
                    operationResponse.setCode(ResultCode.OK);
                    operationResponse.setMessage(SUCCESS);
                    operationResponse.setData(new CreateNewCustomerOrderResponse(newAppId));
                    registerDBPayment(lang, request.getIpAddress(), new BigDecimal(appEntity.getAmountToPay()), ecommTransactionId, newAppId);
                }
            } else {
                operationResponse.setMessage(saveAppResponse.getMessage());
            }
        } else {
            operationResponse.setMessage(WRONG_UPDATE);
        }
    }

    private void registerDBPayment(String lang, String ipAddress, BigDecimal amountToPay, String ecommTransId, int appId) {
        Payment payment = new Payment(
                new PaymentClient(ecommCardClientId), lang, ipAddress,"944",
                amountToPay, cardPaymentDescription, ecommTransId, appId
        );
        LOGGER.info("registerPayment. payment: {}", payment);
        String paymentTransId = mainDao.registerPayment(payment, "CARD");
        LOGGER.info("After registerPayment. paymentTransId: {}", paymentTransId);
    }

    private ThyApplicationEntity saveAppAndProcessResponse(ThyApplicationEntity appEntity, String lang, OperationResponse operationResponse) {
        appEntity = cardStepsDao.saveApplication(appEntity);
        if (appEntity == null || appEntity.getId() == null) {
            String msg = localMessageService.get("message.errors.global_error", new Locale(lang));
            operationResponse.setMessage(msg);
        } else {
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
        }
        return appEntity;
    }

    @Override
    public OperationResponse uploadFiles(UploadFilesRequest request, String lang) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);
        if (cardStepsValidator.isRequestValid(request, lang, operationResponse)) {
            processUploadingFiles(request, lang, operationResponse);
        }
        return operationResponse;
    }

    private void processUploadingFiles(UploadFilesRequest request, String lang, OperationResponse operationResponse) {
        ThyApplicationEntity appEntity = cardStepsDao.getApplicationById(request.getAppId());
        if (!appEntity.isActive()) {
            cardStepsDao.deleteUploadWrappers(appEntity.getUploadWrappers());
            appEntity.setUploadWrappers(request.getUploadWrapperEntities());
            for (UploadWrapperEntity uploadWrapperEntity : appEntity.getUploadWrappers()) {
                uploadWrapperEntity.setApp(appEntity);
            }
            saveAppAndProcessResponse(appEntity, lang, operationResponse);
        } else {
            operationResponse.setMessage(WRONG_UPDATE);
        }
    }

    @Override
    public OperationResponse<GetPromoCodeResponse> getPromoCode(GetPromoCodeRequest request, String lang) {
        OperationResponse<GetPromoCodeResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        if (cardStepsValidator.isRequestValid(request, lang, operationResponse)) {
            PromoCodeEntity promoCodeEntity = cardStepsDao.getPromoCodeByCode(request.getPromoCode());
            if (promoCodeEntity != null) {
                operationResponse.setCode(ResultCode.OK);
                operationResponse.setMessage(SUCCESS);
                operationResponse.setData(new GetPromoCodeResponse(promoCodeEntity));
            } else {
                String msg = localMessageService.get("message.errors.invalid.promo_code", new Locale(lang));
                operationResponse.setMessage(msg);
            }
        }
        return operationResponse;
    }

    @Override
    public OperationResponse<GetAnnualIncomeValuesResponse> getAnnualIncomeValues(String lang) {
        OperationResponse<GetAnnualIncomeValuesResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        List<NetGrossIncomeEntity> netGrossIncomeEntityList = cardStepsDao.getNetGrossIncomesByLang(lang);
        if (netGrossIncomeEntityList == null || netGrossIncomeEntityList.isEmpty()) {
            String msg = localMessageService.get("message.errors.global_error", new Locale(lang));
            operationResponse.setMessage(msg);
        } else {
            List<SourceOfIncomeEntity> sourceOfIncomeEntityList = cardStepsDao.getSourcesOfIncomeByLang(lang);
            if (sourceOfIncomeEntityList == null || sourceOfIncomeEntityList.isEmpty()) {
                String msg = localMessageService.get("message.errors.global_error", new Locale(lang));
                operationResponse.setMessage(msg);
            } else {
                operationResponse.setCode(ResultCode.OK);
                operationResponse.setMessage(SUCCESS);
                operationResponse.setData(new GetAnnualIncomeValuesResponse(netGrossIncomeEntityList, sourceOfIncomeEntityList));
            }
        }
        return operationResponse;
    }

}
