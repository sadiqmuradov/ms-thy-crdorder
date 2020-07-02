/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.dao;

import az.pashabank.apl.ms.thy.model.Branch;
import az.pashabank.apl.ms.thy.model.CRSAnswer;
import az.pashabank.apl.ms.thy.model.CRSQuestion;
import az.pashabank.apl.ms.thy.model.Card;
import az.pashabank.apl.ms.thy.model.CardProduct;
import az.pashabank.apl.ms.thy.model.CardProductView;
import az.pashabank.apl.ms.thy.model.CouponCode;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder1Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder2Request;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderRequest;
import az.pashabank.apl.ms.thy.model.MobileNumbersByPin;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.Payment;
import az.pashabank.apl.ms.thy.model.PaymentField;
import az.pashabank.apl.ms.thy.model.ThyApplication;
import az.pashabank.apl.ms.thy.model.ThyCouponApplication;
import az.pashabank.apl.ms.thy.model.ThyCouponCodes;
import az.pashabank.apl.ms.thy.model.UploadWrapper;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeRequest;
import az.pashabank.apl.ms.thy.model.thy.CheckTkResponse;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyRequest;
import az.pashabank.apl.ms.thy.model.thy.ThyUserInfo;

import java.util.List;

public interface IMainDao {

    MobileNumbersByPin getMobileNumbersByPin(String pin, OperationResponse operationResponse);

    boolean insertOtpCode(int otpCode, String pinCode);

    boolean checkOtpCode(String otpCode, String pin);

    void insertUploads(List<UploadWrapper> crsAnswerList, Integer newAppId);

    List<CRSQuestion> getCRSQuestions(String lang);

    void insertCRSAnswers(List<CRSAnswer> crsAnswerList, Integer newAppId);

    List<Card> getActiveCards(String currency, Integer period);

    Integer addNewApplication(CreateNewCustomerOrderRequest request, OperationResponse operationResponse, CheckTkResponse checkTkResponse);

    Integer addNewCouponApplication(CreateNewCouponOrder1Request request, OperationResponse operationResponse);

    boolean updateCouponApplicationStep1(CreateNewCouponOrder1Request request, OperationResponse operationResponse);

    boolean updateCouponApplicationStep2(CreateNewCouponOrder2Request request, OperationResponse operationResponse);

    boolean updateCouponApplicationStep3(int appId, OperationResponse operationResponse);

    String registerPayment(Payment payment, String orderType);

    Payment getNewPaymentByEcommTransId(String ecommTransId);

    Payment getPaymentByEcommTransId(String ecommTransId, String orderType);

    Payment getPaymentByAppId(int appId, String orderType);

    void insertPaymentFields(List<PaymentField> paymentFieldList, Integer paymentId);

    List<PaymentField> getPaymentFieldsByPaymentId(int paymentId);

    List<Payment> getUnpaidFlexPayments(String orderType);

    OperationResponse makePaymentToFlex(int paymentId, String orderType);

    boolean markApplicationAsPaid(int appId, String orderType);

    boolean markApplicationAsSent(int appId, String orderType);

    void updatePaymentStatus(Payment payment, boolean isSuccessful, String errorDescription, String orderType);

    boolean updateFlexPaymentDesc(int appId, String errorCode);

    ThyApplication getThyApplication(int appId);

    ThyCouponApplication getThyCouponApplication(int appId);

    ThyCouponApplication getActiveCouponApplication(int appId);

    List<CardProductView> getCardProductViews(int appId);

    CouponCode getElectronCouponCode(int cardProductId);

    void updateElectronCouponCode(int id);

    String getValueFromEmailConfig(String key);

    String getEmailFromApp(int appId);

    List<ThyApplication> getUnsentApplications();

    List<UploadWrapper> getUploads(Integer appId);

    List<CRSAnswer> getCrsAnswers(Integer appId);

    List<String> getActiveMails();

    String addUserInfo(RegisterCustomerInThyRequest request, String tkNumber);

    boolean updateTHYuserInfo(String email, String tkNo, int status);

    List<Branch> getBranchList(String lang);

    List<CardProduct> getCardProducts(String lang, String orderType);

    String getDynamicVal(String key);

    CardProduct getCardProductById(int productId);

    List<ThyUserInfo> getThyUsersList(int page);

    List<ThyUserInfo> getThyUsersListByKeyword(String q);

    ThyUserInfo getThyUserInfoById(int userId);

    int getThyUsersListCount();

    CouponCode getCouponCode(int couponId);
    boolean updateCouponAppAndStatus(int couponId, int appId);
    String getCouponSerialNo(int appId);
    String getPromoCodeById(int promoCodeId);
    CouponCode getCouponCode(ValidateCouponCodeRequest validateCouponCodeRequest);
    List<ThyCouponCodes> getThyCouponCodes(String serialNo);
    void updateCouponCodes(String id, String username);
}
