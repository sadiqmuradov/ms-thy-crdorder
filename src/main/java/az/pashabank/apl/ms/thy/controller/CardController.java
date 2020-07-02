package az.pashabank.apl.ms.thy.controller;

import az.pashabank.apl.ms.thy.constants.URL;
import az.pashabank.apl.ms.thy.entity.Country;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.Branch;
import az.pashabank.apl.ms.thy.model.CRSQuestion;
import az.pashabank.apl.ms.thy.model.CardProduct;
import az.pashabank.apl.ms.thy.model.CheckOtpRequest;
import az.pashabank.apl.ms.thy.model.CheckPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.CheckPaymentStatusResponse;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderRequest;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderResponse;
import az.pashabank.apl.ms.thy.model.MobileNumbersByPin;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.SendOtpRequest;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeRequest;
import az.pashabank.apl.ms.thy.model.thy.CheckTkResponse;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyRequest;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyResponse;
import az.pashabank.apl.ms.thy.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@Api("PashaBank THY Miles & Smiles Card order Api")
public class CardController {

    private static final MainLogger LOGGER = MainLogger.getLogger(CardController.class);

    @Autowired
    private CardService cardService;

    @ApiOperation("Get customer mobile numbers by pin")
    @GetMapping(URL.GET_MOBILE_NUMBERS_BY_PIN)
    public OperationResponse<MobileNumbersByPin> getMobileNumbersByPin(@PathVariable final String pin) {
        OperationResponse<MobileNumbersByPin> response = cardService.getMobileNumbersByPin(pin);
        LOGGER.info("getMobileNumbersByPin. PIN: {}, response: {}", pin, response);
        return response;
    }

    @ApiOperation("Send OTP code to a customer mobile number")
    @PostMapping(URL.POST_SEND_OTP)
    public OperationResponse sendOTP(@RequestBody final SendOtpRequest request) {
        OperationResponse response = cardService.sendOTP(request);
        LOGGER.info("sendOTP. request: {}, response: {}", request, response);
        return response;
    }

    @ApiOperation("Check the OTP code sent to the customer mobile number")
    @PostMapping(URL.POST_CHECK_OTP)
    public OperationResponse checkOTP(@RequestBody final CheckOtpRequest request) {
        OperationResponse response = cardService.checkOTP(request);
        LOGGER.info("checkOTP. request: {}, response: {}", request, response);
        return response;
    }

    @ApiOperation("Check a TK number of a customer")
    @GetMapping(URL.GET_CHECK_TK)
    public OperationResponse checkTK(@PathVariable final String tk) {
        OperationResponse<CheckTkResponse> response = cardService.checkTK(tk);
        LOGGER.info("checkTK. TK: {}, response: {}", tk, response);
        return response;
    }

    @ApiOperation("Register a customer in the THY")
    @PostMapping(URL.POST_REGISTER_CUSTOMER_IN_THY)
    public OperationResponse registerCustomerInThy(
            @PathVariable final String lang,
            @RequestBody final RegisterCustomerInThyRequest request) {
        LOGGER.info("registerCustomerInThy. request: {}, lang {}", request, lang);
        OperationResponse<RegisterCustomerInThyResponse> response = cardService.registerCustomerInThy(request);
        LOGGER.info("registerCustomerInThy. response: {}", response);
        return response;
    }

    @ApiOperation("Create a new customer order")
    @PostMapping(URL.POST_CREATE_NEW_CUSTOMER_ORDER)
    public OperationResponse createNewCustomerOrder(
            @RequestBody final CreateNewCustomerOrderRequest request,
            @PathVariable final String lang
    ) {
        LOGGER.info("createNewCustomerOrder. request: {}, lang: {}", request, lang);
        OperationResponse<CreateNewCustomerOrderResponse> response = cardService.createNewCustomerOrder(request, lang);
        LOGGER.info("createNewCustomerOrder. response: {}", response);
        return response;
    }

    @ApiOperation("Check payment status")
    @PostMapping(URL.POST_CHECK_PAYMENT_STATUS)
    public OperationResponse checkPaymentStatus(@PathVariable final String lang,
                                                @RequestBody CheckPaymentStatusRequest request) {
        LOGGER.info("checkStatusPayment. request: {}, lang: {}", request, lang);
        OperationResponse<CheckPaymentStatusResponse> response = cardService.checkPaymentStatus(lang, request);
        LOGGER.info("checkStatusPayment. response: {}", response);
        return response;
    }

    @GetMapping(path = URL.GET_CITY_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCity(@PathVariable String contryCode) {
        return cardService.getCity(contryCode);
    }

    @GetMapping(path = URL.GET_COUNTRY_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> getCountryList(@PathVariable String lang) {
        return cardService.getCountryList(lang);
    }

    @GetMapping(path = URL.REFRESH_COUNTRY_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void refreshCountryList(@PathVariable String lang) {cardService.refreshCountryList(lang);}

    @ApiOperation("Get CRS Questions list")
    @GetMapping(URL.GET_CRS_QUESTIONS)
    public List<CRSQuestion> getCRSQuestions(@PathVariable final String lang) {
        List<CRSQuestion> crsQuestionList = cardService.getCRSQuestions(lang);
        LOGGER.info("getCRSQuestions. request: {}, response: {}", lang, crsQuestionList);
        return crsQuestionList;
    }

    @ApiOperation("Get Branch list")
    @GetMapping(URL.GET_BRANCH_LIST)
    public List<Branch> getBranchList(@PathVariable final String lang) {
        List<Branch> branchList = cardService.getBranchList(lang);
        LOGGER.info("getBranchList. request: {}, response: {}", lang, branchList);
        return branchList;
    }

    @ApiOperation("Get Card Product list")
    @GetMapping(URL.GET_CARD_PRODUCTS)
    public List<CardProduct> getCardProductList(@PathVariable final String lang) {
        List<CardProduct> cardProductList = cardService.getCardProducts(lang);
        LOGGER.info("getCardProductList. request: {}, response: {}", lang, cardProductList);
        return cardProductList;
    }

    @ApiOperation("Validate Coupon Code")
    @PostMapping(URL.POST_VALIDATE_COUPON_CODE)
    public OperationResponse validateCouponCode(@PathVariable final String lang,
                                                @RequestBody ValidateCouponCodeRequest validateCouponCodeRequest) {
        OperationResponse operationResponse = cardService.validateCouponCode(validateCouponCodeRequest);
        LOGGER.info("validateCouponCode. request: {} - {}, response: {}", lang, validateCouponCodeRequest, operationResponse);
        return operationResponse;
    }
}
