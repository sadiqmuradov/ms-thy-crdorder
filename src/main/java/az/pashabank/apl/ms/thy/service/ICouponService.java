/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.service;

import az.pashabank.apl.ms.thy.model.CardProduct;
import az.pashabank.apl.ms.thy.model.CheckCouponPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.CheckCouponPaymentStatusResponse;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder1Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder2Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder3Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrderStep1Response;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrderStep3Response;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.ThyCouponCodes;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeRequest;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeResponse;

import java.util.List;

public interface ICouponService {

    OperationResponse<CreateNewCouponOrderStep1Response> createNewCouponOrderStep1(CreateNewCouponOrder1Request request);

    OperationResponse createNewCouponOrderStep2(CreateNewCouponOrder2Request request, String lang);

    OperationResponse<CreateNewCouponOrderStep3Response> createNewCouponOrderStep3(CreateNewCouponOrder3Request request, String lang);

    OperationResponse<CheckCouponPaymentStatusResponse> checkCouponPaymentStatus(String lang, CheckCouponPaymentStatusRequest request);

    List<ThyCouponCodes> getThyCouponCodes(String serialNo);

    void updateCouponCodes(String id, String username);

    OperationResponse<ValidateCouponCodeResponse> validateCouponCode(ValidateCouponCodeRequest validateCouponCodeRequest);

    List<CardProduct> getCouponCardProducts(String lang);

}
