/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.service;

import az.pashabank.apl.ms.thy.entity.Country;
import az.pashabank.apl.ms.thy.model.Branch;
import az.pashabank.apl.ms.thy.model.CRSQuestion;
import az.pashabank.apl.ms.thy.model.CardProduct;
import az.pashabank.apl.ms.thy.model.CheckOtpRequest;
import az.pashabank.apl.ms.thy.model.CheckPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.CheckPaymentStatusResponse;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderRequest;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderResponse;
import az.pashabank.apl.ms.thy.model.Login;
import az.pashabank.apl.ms.thy.model.MobileNumbersByPin;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.SendOtpRequest;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeRequest;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeResponse;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyRequest;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyResponse;
import az.pashabank.apl.ms.thy.model.thy.SecurityQuestionsResponse;
import az.pashabank.apl.ms.thy.model.thy.ThyUserInfo;

import java.util.List;

public interface ICardService {

    OperationResponse<MobileNumbersByPin> getMobileNumbersByPin(String pin);

    OperationResponse sendOTP(SendOtpRequest sendOTPRequest);

    OperationResponse checkOTP(CheckOtpRequest request);

    OperationResponse checkTK(String tk);

    String resendSms(int userId);

    OperationResponse registerCustomerInThy(RegisterCustomerInThyRequest registerCustomerInThyRequest);

    OperationResponse<CreateNewCustomerOrderResponse> createNewCustomerOrder(CreateNewCustomerOrderRequest request, String lang);

    OperationResponse<CheckPaymentStatusResponse> checkPaymentStatus(String lang, CheckPaymentStatusRequest request);

    String addTkUserInfo(RegisterCustomerInThyRequest request, String tkNumber);

    boolean updateTHYuserInfo(String email, String tkNo, int status);

    List<CRSQuestion> getCRSQuestions(String lang);

    List<Branch> getBranchList(String lang);

    List<CardProduct> getCardProducts(String lang);

    OperationResponse<SecurityQuestionsResponse> getSecurityQuestions(String lang);

    boolean sendPassword(String phoneNumber, String smsText);

    String getDynamicVal(String key);

    String login(Login login);

    List<Country> getCountryList(String lang);

    List<ThyUserInfo> getThyUsersList(int page);

    String getCity(String contryCode);

    OperationResponse<RegisterCustomerInThyResponse> registerCustomerInThyForUi(RegisterCustomerInThyRequest request);

    OperationResponse<ValidateCouponCodeResponse> validateCouponCode(ValidateCouponCodeRequest validateCouponCodeRequest);

    void refreshCountryList(String lang);
}
