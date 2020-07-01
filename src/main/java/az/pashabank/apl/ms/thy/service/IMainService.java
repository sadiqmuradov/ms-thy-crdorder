/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.service;

import az.pashabank.apl.ms.thy.model.*;
import az.pashabank.apl.ms.thy.model.thy.*;

import java.util.List;

public interface IMainService {

    OperationResponse<MobileNumbersByPin> getMobileNumbersByPin(String pin);

    OperationResponse sendOTP(SendOtpRequest sendOTPRequest);

    OperationResponse checkOTP(CheckOtpRequest request);

    OperationResponse checkTK(String tk);

    String resendSms(int userId);

    OperationResponse registerCustomerInThy(RegisterCustomerInThyRequest registerCustomerInThyRequest);

    OperationResponse createNewCustomerOrder(CreateNewCustomerOrderRequest request, String lang);

    OperationResponse<CheckPaymentStatusResponse> checkPaymentStatus(CheckPaymentStatusRequest request);

    String addTkUserInfo(RegisterCustomerInThyRequest request, String tkNumber);

    boolean updateTHYuserInfo(String email, String tkNo, int status);

    List<CRSQuestion> getCRSQuestions(String lang);

    List<Branch> getBranchList(String lang);

    List<CardProduct> getCardProducts(String lang);

    OperationResponse<SecurityQuestionsResponse> getSecurityQuestions(String lang);

    boolean sendPassword(String phoneNumber, String smsText);

    String getDynamicVal(String key);

    String login(Login login);

    List<Country> getCountryList();

    List<ThyUserInfo> getThyUsersList(int page);

    String getCity(String contryCode);

    OperationResponse<RegisterCustomerInThyResponse> registerCustomerInThyForUi(RegisterCustomerInThyRequest request);
}
