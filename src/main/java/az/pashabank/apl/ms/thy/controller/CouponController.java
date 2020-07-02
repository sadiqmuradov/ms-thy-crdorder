package az.pashabank.apl.ms.thy.controller;

import az.pashabank.apl.ms.thy.constants.URL;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.CardProduct;
import az.pashabank.apl.ms.thy.model.CheckCouponPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.CheckCouponPaymentStatusResponse;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder1Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder2Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder3Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrderStep1Response;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrderStep3Response;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.service.CouponService;
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

import java.util.List;

@RestController
@CrossOrigin
@Validated
@Api("PashaBank THY Miles & Smiles Coupon order Api")
public class CouponController {

    private static final MainLogger LOGGER = MainLogger.getLogger(CouponController.class);

    @Autowired
    private CouponService couponService;

    @ApiOperation("Create a new coupon order first step")
    @PostMapping(URL.POST_CREATE_NEW_COUPON_ORDER_STEP1)
    public OperationResponse createNewCouponOrderStep1(
            @RequestBody final CreateNewCouponOrder1Request request,
            @PathVariable final String lang
    ) {
        LOGGER.info("createNewCouponOrderStep1. request: {}, lang {}", request, lang);
        OperationResponse<CreateNewCouponOrderStep1Response> response = couponService.createNewCouponOrderStep1(request);
        LOGGER.info("createNewCouponOrderStep1. response: {}, lang {}", response, lang);
        return response;
    }

    @ApiOperation("Create a new coupon order second step")
    @PostMapping(URL.POST_CREATE_NEW_COUPON_ORDER_STEP2)
    public OperationResponse createNewCouponOrderStep2(
            @RequestBody final CreateNewCouponOrder2Request request,
            @PathVariable final String lang
    ) {
        LOGGER.info("createNewCouponOrderStep2. request: {}, lang: {}", request, lang);
        OperationResponse response = couponService.createNewCouponOrderStep2(request, lang);
        LOGGER.info("createNewCouponOrderStep2. response: {}", response);
        return response;
    }

    @ApiOperation("Create a new coupon order third step")
    @PostMapping(URL.POST_CREATE_NEW_COUPON_ORDER_STEP3)
    public OperationResponse createNewCouponOrderStep3(
            @RequestBody final CreateNewCouponOrder3Request request,
            @PathVariable final String lang
    ) {
        LOGGER.info("createNewCouponOrderStep3. request: {}, lang: {}", request, lang);
        OperationResponse<CreateNewCouponOrderStep3Response> response = couponService.createNewCouponOrderStep3(request, lang);
        LOGGER.info("createNewCouponOrderStep3. response: {}", response);
        return response;
    }

    @ApiOperation("Check coupon payment status")
    @PostMapping(URL.POST_CHECK_COUPON_PAYMENT_STATUS)
    public OperationResponse checkCouponPaymentStatus(@PathVariable final String lang,
                                                      @RequestBody CheckCouponPaymentStatusRequest request) {
        LOGGER.info("checkCouponPaymentStatus. request: {}, lang: {}", request, lang);
        OperationResponse<CheckCouponPaymentStatusResponse> response = couponService.checkCouponPaymentStatus(lang, request);
        LOGGER.info("checkCouponPaymentStatus. response: {}", response);
        return response;
    }

    @ApiOperation("Get Card Product list for Coupon Order")
    @GetMapping(URL.GET_COUPON_CARD_PRODUCTS)
    public List<CardProduct> getCouponCardProductList(@PathVariable final String lang) {
        List<CardProduct> couponCardProductList = couponService.getCouponCardProducts(lang);
        LOGGER.info("getCouponCardProductList. lang: {}, response: {}", lang, couponCardProductList);
        return couponCardProductList;
    }
}
