package az.pashabank.apl.ms.thy.controller;

import az.pashabank.apl.ms.thy.constants.URL;
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
import az.pashabank.apl.ms.thy.model.UploadFilesRequest;
import az.pashabank.apl.ms.thy.service.CardStepsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Validated
@Api("PashaBank THY Miles & Smiles Card order Api")
public class CardStepsController {

    private static final MainLogger LOGGER = MainLogger.getLogger(CardStepsController.class);

    @Autowired
    private CardStepsService cardStepsService;

    @ApiOperation("Create a new card order first step")
    @PostMapping(URL.POST_CREATE_NEW_CARD_ORDER_STEP1)
    public OperationResponse<CreateNewCardOrderResponse> createNewCardOrderStep1(
            @RequestBody final CreateNewCardOrderStep1Request request,
            @PathVariable final String lang
    ) {
        LOGGER.info("createNewCardOrderStep1. request: {}, lang: {}", request, lang);
        OperationResponse<CreateNewCardOrderResponse> response = cardStepsService.createNewCardOrderStep1(request, lang);
        LOGGER.info("createNewCardOrderStep1. response: {}", response);
        return response;
    }

    @ApiOperation("Create a new card order second step")
    @PostMapping(URL.POST_CREATE_NEW_CARD_ORDER_STEP2)
    public OperationResponse createNewCardOrderStep2(
            @RequestBody final CreateNewCardOrderStep2Request request,
            @PathVariable final String lang
    ) {
        LOGGER.info("createNewCardOrderStep2. request: {}, lang: {}", request, lang);
        OperationResponse response = cardStepsService.createNewCardOrderStep2(request, lang);
        LOGGER.info("createNewCardOrderStep2. response: {}", response);
        return response;
    }

    @ApiOperation("Create a new card order third step")
    @PostMapping(URL.POST_CREATE_NEW_CARD_ORDER_STEP3)
    public OperationResponse<CreateNewCustomerOrderResponse> createNewCardOrderStep3(
            @RequestBody final CreateNewCardOrderStep3Request request,
            @PathVariable final String lang
    ) {
        LOGGER.info("createNewCardOrderStep3. request: {}, lang: {}", request, lang);
        OperationResponse<CreateNewCustomerOrderResponse> response = cardStepsService.createNewCardOrderStep3(request, lang);
        LOGGER.info("createNewCardOrderStep3. response: {}", response);
        return response;
    }

    @ApiOperation("Upload files to a new card order")
    @PostMapping(URL.POST_UPLOAD_FILES)
    public OperationResponse uploadFiles(
            @RequestBody final UploadFilesRequest request,
            @PathVariable final String lang
    ) {
        LOGGER.info("uploadFiles. request: {}, lang: {}", request, lang);
        OperationResponse response = cardStepsService.uploadFiles(request, lang);
        LOGGER.info("uploadFiles. response: {}", response);
        return response;
    }

    @ApiOperation("Get a promotion code by code")
    @PostMapping(URL.GET_PROMO_CODE)
    public OperationResponse<GetPromoCodeResponse> getPromoCode(
            @RequestBody final GetPromoCodeRequest request,
            @PathVariable final String lang
    ) {
        LOGGER.info("getPromoCode. request: {}, lang: {}", request, lang);
        OperationResponse<GetPromoCodeResponse> response = cardStepsService.getPromoCode(request, lang);
        LOGGER.info("getPromoCode. response: {}", response);
        return response;
    }

    @ApiOperation("Get annual source and income values")
    @GetMapping(URL.GET_ANNUAL_INCOME_VALUES)
    public OperationResponse<GetAnnualIncomeValuesResponse> getAnnualIncomeValues(@PathVariable final String lang) {
        LOGGER.info("getAnnualIncomeValues. lang: {}", lang);
        OperationResponse<GetAnnualIncomeValuesResponse> response = cardStepsService.getAnnualIncomeValues(lang);
        LOGGER.info("getAnnualIncomeValues. response: {}", response);
        return response;
    }

}
