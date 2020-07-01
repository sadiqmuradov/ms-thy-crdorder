package az.pashabank.apl.ms.thy.controller;

import az.pashabank.apl.ms.thy.constants.URL;
import az.pashabank.apl.ms.thy.logger.UFCLogger;
import az.pashabank.apl.ms.thy.model.*;
import az.pashabank.apl.ms.thy.model.thy.*;
import az.pashabank.apl.ms.thy.service.MainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@Api("PashaBank THY Miles & Smiles Api")
public class MainController {

    private static final UFCLogger LOGGER = UFCLogger.getLogger(MainController.class);

    @Autowired
    private MainService mainService;

    @ApiOperation("Get customer mobile numbers by pin")
    @GetMapping(URL.GET_MOBILE_NUMBERS_BY_PIN)
    public OperationResponse<MobileNumbersByPin> getMobileNumbersByPin(@PathVariable final String pin) {
        OperationResponse<MobileNumbersByPin> response = mainService.getMobileNumbersByPin(pin);
        LOGGER.info("getMobileNumbersByPin. PIN: {}, response: {}", pin, response);
        return response;
    }

    @ApiOperation("Send OTP code to a customer mobile number")
    @PostMapping(URL.POST_SEND_OTP)
    public OperationResponse sendOTP(@RequestBody final SendOtpRequest request) {
        OperationResponse response = mainService.sendOTP(request);
        LOGGER.info("sendOTP. request: {}, response: {}", request, response);
        return response;
    }

    @ApiOperation("Check the OTP code sent to the customer mobile number")
    @PostMapping(URL.POST_CHECK_OTP)
    public OperationResponse checkOTP(@RequestBody final CheckOtpRequest request) {
        OperationResponse response = mainService.checkOTP(request);
        LOGGER.info("checkOTP. request: {}, response: {}", request, response);
        return response;
    }

    @ApiOperation("Check a TK number of a customer")
    @GetMapping(URL.GET_CHECK_TK)
    public OperationResponse checkTK(@PathVariable final String tk) {
        OperationResponse<CheckTkResponse> response = mainService.checkTK(tk);
        LOGGER.info("checkTK. TK: {}, response: {}", tk, response);
        return response;
    }

    @ApiOperation("Register a customer in the THY")
    @PostMapping(URL.POST_REGISTER_CUSTOMER_IN_THY)
    public OperationResponse registerCustomerInThy(@RequestBody final RegisterCustomerInThyRequest request) {
        OperationResponse<RegisterCustomerInThyResponse> response = mainService.registerCustomerInThy(request);
        LOGGER.info("registerCustomerInThy. request: {}, response: {}", request, response);
        return response;
    }

    @ApiOperation("Create a new customer order")
    @PostMapping(URL.POST_CREATE_NEW_CUSTOMER_ORDER)
    public OperationResponse createNewCustomerOrder(
            @RequestBody final CreateNewCustomerOrderRequest request,
            @PathVariable final String lang
    ) {
        LOGGER.info("createNewCustomerOrder. request: {}, lang: {}", request, lang);
        OperationResponse<CreateNewCustomerOrderResponse> response = mainService.createNewCustomerOrder(request, lang);
        LOGGER.info("createNewCustomerOrder. response: {}", response);
        return response;
    }

    @ApiOperation("Check payment status")
    @PostMapping(URL.POST_CHECK_PAYMENT_STATUS)
    public OperationResponse checkPaymentStatus(@RequestBody CheckPaymentStatusRequest request) {
        OperationResponse<CheckPaymentStatusResponse> response = mainService.checkPaymentStatus(request);
        LOGGER.info("checkStatusPayment. request: {}, response: {}", request, response);
        return response;
    }

    @ApiOperation("Get Security Question list")
    @GetMapping(URL.GET_SECURITY_QUESTION_LIST)
    public OperationResponse getSecurityQuestions(@PathVariable final String lang) {
        OperationResponse<SecurityQuestionsResponse> response = mainService.getSecurityQuestions(lang);
        LOGGER.info("getSecurityQuestions. lang: {}, response: {}", lang, response);
        return response;
    }

    @GetMapping(path = URL.GET_CITY_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCity(@PathVariable String contryCode) {
        return mainService.getCity(contryCode);
    }

    @GetMapping(path = URL.GET_COUNTRY_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> getCountryList() {
        return mainService.getCountryList();
    }

    @ApiOperation("Get CRS Questions list")
    @GetMapping(URL.GET_CRS_QUESTIONS)
    public List<CRSQuestion> getCRSQuestions(@PathVariable final String lang) {
        List<CRSQuestion> crsQuestionList = mainService.getCRSQuestions(lang);
        LOGGER.info("getCRSQuestions. request: {}, response: {}", lang, crsQuestionList);
        return crsQuestionList;
    }

    @ApiOperation("Get Branch list")
    @GetMapping(URL.GET_BRANCH_LIST)
    public List<Branch> getBranchList(@PathVariable final String lang) {
        List<Branch> branchList = mainService.getBranchList(lang);
        LOGGER.info("getBranchList. request: {}, response: {}", lang, branchList);
        return branchList;
    }

    @ApiOperation("Get Card Product list")
    @GetMapping(URL.GET_CARD_PRODUCTS)
    public List<CardProduct> getCardProductList(@PathVariable final String lang) {
        List<CardProduct> cardProductList = mainService.getCardProducts(lang);
        LOGGER.info("getCardProductList. request: {}, response: {}", lang, cardProductList);
        return cardProductList;
    }
}
