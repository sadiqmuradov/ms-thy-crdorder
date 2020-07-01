/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.dao;

import az.pashabank.apl.ms.thy.model.*;
import az.pashabank.apl.ms.thy.model.thy.CreateNewCustomerOrderRequest;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyRequest;
import az.pashabank.apl.ms.thy.model.thy.ThyUserInfo;

import java.util.List;

public interface IMainDao {

    MobileNumbersByPin getMobileNumbersByPin(String pin, OperationResponse operationResponse);

    boolean insertOtpCode(int otpCode, String pinCode);

    boolean checkOtpCode(String otpCode, String pin, OperationResponse operationResponse);

    void insertUploads(List<UploadWrapper> crsAnswerList, Integer newAppId);

    List<CRSQuestion> getCRSQuestions(String lang);

    void insertCRSAnswers(List<CRSAnswer> crsAnswerList, Integer newAppId);

    List<Card> getActiveCards(String currency, Integer period);

    Integer addNewApplication(CreateNewCustomerOrderRequest request, OperationResponse operationResponse);

    String registerPayment(Payment payment);

    Payment getNewPaymentByEcommTransId(String ecommTransId);

    Payment getPaymentByEcommTransId(String ecommTransId);

    Payment getPaymentByAppId(int appId);

    void insertPaymentFields(List<PaymentField> paymentFieldList, Integer paymentId);

    List<PaymentField> getPaymentFieldsByPaymentId(int paymentId);

    List<Payment> getUnpaidFlexPayments();

    OperationResponse makePaymentToFlex(int paymentId);

    boolean markApplicationAsPaid(int appId);

    boolean markApplicationAsSent(int appId);

    void updatePaymentStatus(Payment payment, boolean isSuccessful, String errorDescription);

    boolean updateFlexPaymentDesc(int appId, String errorCode);

    ThyApplication getThyApplication(int appId);

    String getEmailFromApp(int appId);

    List<ThyApplication> getUnsentApplications();

    List<UploadWrapper> getUploads(Integer appId);

    List<CRSAnswer> getCrsAnswers(Integer appId);

    List<String> getActiveMails();

    String addUserInfo(RegisterCustomerInThyRequest request, String tkNumber);

    boolean updateTHYuserInfo(String email, String tkNo, int status);

    List<Branch> getBranchList(String lang);

    List<CardProduct> getCardProducts(String lang);

    String getDynamicVal(String key);

    CardProduct getCardProductById(int productId);

    List<ThyUserInfo> getThyUsersList(int page);

    List<ThyUserInfo> getThyUsersListByKeyword(String q);

    ThyUserInfo getThyUserInfoById(int userId);

    int getThyUsersListCount();
}
