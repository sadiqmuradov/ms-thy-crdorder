package az.pashabank.apl.ms.thy.service;

import az.pashabank.apl.ms.thy.constants.CouponType;
import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.BranchCouponPaymentStatusResponse;
import az.pashabank.apl.ms.thy.model.CardProduct;
import az.pashabank.apl.ms.thy.model.CardProductRequest;
import az.pashabank.apl.ms.thy.model.CardProductView;
import az.pashabank.apl.ms.thy.model.CheckCouponPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.CheckCouponPaymentStatusResponse;
import az.pashabank.apl.ms.thy.model.CouponCode;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder1Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder2Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder3Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrderStep1Response;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrderStep3Response;
import az.pashabank.apl.ms.thy.model.ElectronCardProductView;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.Payment;
import az.pashabank.apl.ms.thy.model.PaymentClient;
import az.pashabank.apl.ms.thy.model.ThyCouponApplication;
import az.pashabank.apl.ms.thy.model.ThyCouponCodes;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeRequest;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeResponse;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentRequest;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentResponse;
import az.pashabank.apl.ms.thy.model.ecomm.EcommPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.ecomm.EcommPaymentStatusResponse;
import az.pashabank.apl.ms.thy.proxy.ECommServiceProxy;
import az.pashabank.apl.ms.thy.utils.Utils;
import az.pashabank.apl.ms.thy.validator.CouponValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class CouponService implements ICouponService {

    private static final MainLogger LOGGER = MainLogger.getLogger(CouponService.class);

    private static final String WRONG_PAYMENT = "WRONG PAYMENT: Payment is null";
    private static final String TRANSACTION_FAILED = "E-Comm transaction failed";
    private static final String SUCCESS = "Operation is successful";
    private static final String WRONG_UPDATE = "Application is already active, CAN NOT be changed";

    @Value("${ecomm.coupon.clientId}")
    private Integer ecommCouponClientId;

    @Value("${ecomm.coupon.payment.description}")
    private String couponPaymentDescription;

    @Autowired
    private MainDao mainDao;

    @Autowired
    private ECommServiceProxy eCommServiceProxy;

    @Autowired
    protected MailService mailService;

    @Autowired
    private CouponValidator validator;

    @Autowired
    private CardService cardService;

    @Override
    public OperationResponse<CreateNewCouponOrderStep1Response> createNewCouponOrderStep1(CreateNewCouponOrder1Request request) {
        OperationResponse<CreateNewCouponOrderStep1Response> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        if (validator.isRequestValid(request, "", operationResponse)) {
            processStep1(request, operationResponse);
        }
        return operationResponse;
    }

    private void processStep1(CreateNewCouponOrder1Request request, OperationResponse<CreateNewCouponOrderStep1Response> operationResponse) {
        Integer appId = request.getAppId();
        if (appId == null || appId <= 0) {
            appId = mainDao.addNewCouponApplication(request, operationResponse);
            if (appId != null) {
                operationResponse.setCode(ResultCode.OK);
                operationResponse.setMessage(SUCCESS);
            }
        } else {
            if (mainDao.updateCouponApplicationStep1(request, operationResponse)) {
                operationResponse.setCode(ResultCode.OK);
                operationResponse.setMessage(SUCCESS);
            }
        }
        operationResponse.setData(new CreateNewCouponOrderStep1Response(appId));
    }

    @Override
    public OperationResponse createNewCouponOrderStep2(CreateNewCouponOrder2Request request, String lang) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);
        if (validator.isRequestValid(request, lang, operationResponse)) {
            processStep2(request, lang, operationResponse);
        }
        return operationResponse;
    }

    private void processStep2(CreateNewCouponOrder2Request request, String lang, OperationResponse operationResponse) {
        ThyCouponApplication app = mainDao.getThyCouponApplication(request.getAppId());
        if (app == null) {
            operationResponse.setMessage("Coupon application not found");
            return;
        }

        if (app.isActive()) {
            operationResponse.setMessage(WRONG_UPDATE);
            return;
        }

        if (request.getCouponType() == CouponType.BRANCH) {
            mainDao.getBranchList(lang).stream().filter(b -> b.getBranchCode().equals(request.getBranchCode())).forEach(b -> request.setBranchName(b.getName()));
        }
        BigDecimal amountToPay = new BigDecimal(0);
        for (CardProductRequest cardProductRequest : request.getCardProductRequests()) {
            CardProduct cardProduct = mainDao.getCardProductById(cardProductRequest.getCardType());
            BigDecimal totalProductAmount = cardProduct.getPrice().multiply(new BigDecimal(cardProductRequest.getCardCount())).setScale(2, BigDecimal.ROUND_HALF_UP);
            amountToPay = amountToPay.add(totalProductAmount);
        }
        request.setAmountToPay(amountToPay);

        if (mainDao.updateCouponApplicationStep2(request, operationResponse)) {
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
        }
    }

    @Override
    public OperationResponse<CreateNewCouponOrderStep3Response> createNewCouponOrderStep3(CreateNewCouponOrder3Request request, String lang) {
        OperationResponse<CreateNewCouponOrderStep3Response> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        if (validator.isRequestValid(request, lang, operationResponse)) {
            processStep3(request, lang, operationResponse);
        }
        return operationResponse;
    }

    private void processStep3(CreateNewCouponOrder3Request request, String lang, OperationResponse<CreateNewCouponOrderStep3Response> operationResponse) {
        ThyCouponApplication app = mainDao.getThyCouponApplication(request.getAppId());
        if (app == null) {
            operationResponse.setMessage("Coupon application not found");
            return;
        }

        if (app.isActive()) {
            operationResponse.setMessage(WRONG_UPDATE);
            return;
        }

        if (mainDao.updateCouponApplicationStep3(request.getAppId(), operationResponse)) {
            String ecommAmount = String.valueOf(app.getAmountToPay().multiply(new BigDecimal(100)).intValue());
            ECommRegisterPaymentRequest eCommRegisterPaymentRequest = new ECommRegisterPaymentRequest(
                    ecommCouponClientId, ecommAmount, "944",
                    request.getIpAddress(), couponPaymentDescription, lang
            );
            ECommRegisterPaymentResponse eCommRegisterPaymentResponse = eCommServiceProxy.registerPayment(eCommRegisterPaymentRequest);
            String ecommTransId = eCommRegisterPaymentResponse.getTransactionId();
            LOGGER.info("eCommServiceProxy. ecommTransId: {}", ecommTransId);

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
                        new CreateNewCouponOrderStep3Response(
                                eCommRegisterPaymentResponse.getClientHandlerUrl(), encodedEcommTransId
                        )
                );

                Payment payment = new Payment(
                        new PaymentClient(ecommCouponClientId), lang, request.getIpAddress(), "944",
                        app.getAmountToPay(), couponPaymentDescription, ecommTransId, app.getId()
                );
                LOGGER.info("registerPayment. payment: {}", payment);
                String paymentTransId = mainDao.registerPayment(payment, "COUPON");
                LOGGER.info("After registerPayment. Payment transaction ID: {}", paymentTransId);
            } else {
                operationResponse.setMessage(eCommRegisterPaymentResponse.getMessage());
            }
        }
    }

    @Override
    public OperationResponse<CheckCouponPaymentStatusResponse> checkCouponPaymentStatus(String lang, CheckCouponPaymentStatusRequest request) {
        OperationResponse<CheckCouponPaymentStatusResponse> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        if (validator.isRequestValid(request, operationResponse)) {
            processRequest(request, operationResponse, lang);
        }
        return operationResponse;
    }

    protected void processRequest(CheckCouponPaymentStatusRequest request, OperationResponse<CheckCouponPaymentStatusResponse> operationResponse, String lang) {
        Payment payment = null;

        if (request.getTransactionId() != null && !request.getTransactionId().trim().isEmpty()) {
            LOGGER.info("getPaymentByEcommTransId. ecommTransId: {}", request.getTransactionId());
            payment = mainDao.getPaymentByEcommTransId(request.getTransactionId(), "COUPON");
            LOGGER.info("After getPaymentByEcommTransId. payment: {}", payment);
        }

        if (payment == null) {
            operationResponse.setMessage(WRONG_PAYMENT);
            return;
        }

        String amount = Utils.formatBigDecimal(
                2, 2, false,
                payment.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)
        );

        ThyCouponApplication app = mainDao.getActiveCouponApplication(payment.getAppId());
        List<CardProductView> cardProductViews = app != null ? mainDao.getCardProductViews(app.getId()) : null;

        EcommPaymentStatusRequest ecommPaymentStatusRequest = new EcommPaymentStatusRequest(request.getTransactionId(), request.getIpAddress());
        ecommPaymentStatusRequest.setClientId(ecommCouponClientId);
        EcommPaymentStatusResponse ecommPaymentStatusResponse = eCommServiceProxy.getPaymentStatus(lang, ecommPaymentStatusRequest);
        LOGGER.info("ecommPaymentStatusResponse: {}", ecommPaymentStatusResponse);
        String message = ecommPaymentStatusResponse.getMessage();

        if (ecommPaymentStatusResponse.getCode().equals("SUCCESS")) {
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
        } else {
            operationResponse.setMessage(TRANSACTION_FAILED);
        }
        CheckCouponPaymentStatusResponse paymentStatusResponse = new CheckCouponPaymentStatusResponse(message, amount, cardProductViews);
        if (app.getCouponType() == CouponType.BRANCH) {
            String branchName = app != null ? app.getBranch().getName() : null;
            paymentStatusResponse = new BranchCouponPaymentStatusResponse(message, amount, cardProductViews, branchName);
        } else {
            for (int i = 0; i < cardProductViews.size(); i++) {
                CardProductView cardProductView = cardProductViews.get(i);
                ElectronCardProductView electronCardProductView = new ElectronCardProductView(cardProductView);
                List<String> couponCodes = new ArrayList<>();
                for (int j = 0; j < cardProductView.getCardCount(); j++) {
                    CouponCode couponCode = mainDao.getElectronCouponCode(cardProductView.getCardProductId());
                    mainDao.updateElectronCouponCode(couponCode.getId());
                    couponCodes.add(couponCode.getCouponCode());
                }
                electronCardProductView.setCouponCodes(couponCodes);
                cardProductViews.set(i, electronCardProductView);
            }
        }
        operationResponse.setData(paymentStatusResponse);

        if (payment.getStatus().getId() != 1) {
            return;
        }

        Map<String, String> parsedPaymentStatus = cardService.processEcommMessage(payment, message);
        if (parsedPaymentStatus.get("result").equalsIgnoreCase("OK")) {
            cardService.applyPostSuccessPaymentSteps(payment, true, "COUPON", cardProductViews);
        } else {
            LOGGER.info("/ecomm/ok [POST] Update payment status in payments db. Payment: {}", payment);
            mainDao.updatePaymentStatus(payment, false, "Ecomm operation error", "COUPON");
            LOGGER.error("Ecomm operation failed: {}", parsedPaymentStatus);
        }
    }

    @Override
    public List<ThyCouponCodes> getThyCouponCodes(String serialNo) {
        List<ThyCouponCodes> couponCodes = new ArrayList<>();
        try {
            couponCodes = mainDao.getThyCouponCodes(serialNo);
        } catch (Exception ex) {
            LOGGER.error("Exception occured: {} " + ex.toString());
        }
        return couponCodes;
    }

    @Override
    public void updateCouponCodes(String id, String username) {
        ThyCouponCodes coupon = new ThyCouponCodes();
        try {
            mainDao.updateCouponCodes(id, username);
        } catch (Exception ex) {
            LOGGER.error("Exception occured: {} " + ex.toString());
        }
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

    @Override
    public List<CardProduct> getCouponCardProducts(String lang) {
        return mainDao.getCardProducts(lang, "COUPON");
    }
}
