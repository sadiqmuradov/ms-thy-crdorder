package az.pashabank.apl.ms.thy.validator;

import az.pashabank.apl.ms.thy.constants.CouponType;
import az.pashabank.apl.ms.thy.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CouponValidator {

    private static final String WRONG_REQUEST = "WRONG REQUEST: Mandatory request parameter is null";
    private static final String WRONG_STEP_NO_1 = "WRONG STEP NO: Step number is null";
    private static final String WRONG_STEP_NO_2 = "WRONG STEP NO: Step number is other than 1, 2, 3 allowed";
    private static final String WRONG_CONFIRM_EMAIL_1 = "WRONG CONFIRM EMAIL: Confirm email is null";
    private static final String WRONG_CONFIRM_EMAIL_2 = "WRONG CONFIRM EMAIL: Confirm email is empty";
    private static final String WRONG_CONFIRM_EMAIL_3 = "WRONG CONFIRM EMAIL: Confirm email is invalid";
    private static final String UNMATCHED_EMAILS = "UNMATCHED EMAILS: Emails are different";
//    private static final String WRONG_COUPON_TYPE_1 = "WRONG COUPON TYPE: Coupon type is null";
//    private static final String WRONG_COUPON_TYPE_2 = "WRONG COUPON TYPE: Coupon type is empty";
//    private static final String WRONG_COUPON_TYPE_3 = "WRONG COUPON TYPE: Coupon type is incorrect";
    private static final String WRONG_APP_ID_1 = "WRONG APP ID: App id is null";
    private static final String WRONG_APP_ID_2 = "WRONG APP ID: App id is not greater than zero";
    private static final String WRONG_CARD_PRODUCT_REQUESTS_1 = "WRONG CARD PRODUCT REQUESTS: Card product requests is null";
    private static final String WRONG_CARD_PRODUCT_REQUESTS_2 = "WRONG CARD PRODUCT REQUESTS: Card product requests is empty";
    private static final String WRONG_CARD_COUNT_1 = "WRONG CARD COUNT: Card count is null";
    private static final String WRONG_CARD_COUNT_2 = "WRONG CARD COUNT: Card count is not greater than zero";
    private static final String WRONG_TRANSACTION_ID_1 = "WRONG TRANSACTION ID: E-comm transaction ID is null";
    private static final String WRONG_TRANSACTION_ID_2 = "WRONG TRANSACTION ID: E-comm transaction ID is empty";
    private static final String WRONG_SHIPPING_ADDRESS_1 = "WRONG SHIPPING ADDRESS: Shipping address is null";
    private static final String WRONG_SHIPPING_ADDRESS_2 = "WRONG SHIPPING ADDRESS: Shipping address is empty";

    @Autowired
    CardValidator cardValidator;

    public boolean isRequestValid(CreateNewCouponOrder1Request request, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        }  else if (
                        !cardValidator.isNameValid(request.getName(), operationResponse) ||
                                !cardValidator.isSurnameValid(request.getSurname(), operationResponse) ||
                                !cardValidator.isMobileNoValid(request.getMobileNo(), operationResponse) ||
                                !cardValidator.isEmailValid(request.getEmail(), operationResponse) ||
                                !isConfirmEmailValid(request.getConfirmEmail(), operationResponse) ||
                                !areEmailsSame(request.getEmail(), request.getConfirmEmail(), operationResponse)

        ) {
            isValid = false;
        }
        return isValid;
    }

    public boolean isRequestValid(CreateNewCouponOrder2Request request, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                        !isAppIdValid(request.getAppId(), operationResponse) ||
                                (request.getCouponType() == CouponType.BRANCH && !cardValidator.isBranchCodeValid(request.getBranchCode(), lang, operationResponse)) ||
                                (request.getCouponType() == CouponType.COURIER && !isShippingAddressValid(request.getShippingAddress(), operationResponse)) ||
                                !isCardProductRequestsValid(request.getCardProductRequests(), operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    public boolean isShippingAddressValid(String shippingAddress, OperationResponse operationResponse) {
        return !cardValidator.isStringNullOrEmpty(shippingAddress, operationResponse, WRONG_SHIPPING_ADDRESS_1, WRONG_SHIPPING_ADDRESS_2);
    }

    public boolean isRequestValid(CreateNewCouponOrder3Request request, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                        !isAppIdValid(request.getAppId(), operationResponse) ||
                                !cardValidator.isIpAddressValid(request.getIpAddress(), operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    public boolean isConfirmEmailValid(String confirmEmail, OperationResponse operationResponse) {
        return cardValidator.validateStringEmail(confirmEmail, operationResponse, WRONG_CONFIRM_EMAIL_1, WRONG_CONFIRM_EMAIL_2, WRONG_CONFIRM_EMAIL_3);
    }

    private boolean areEmailsSame(String email, String confirmEmail, OperationResponse operationResponse) {
        boolean result = true;
        if (!email.equals(confirmEmail)) {
            result = false;
            operationResponse.setMessage(UNMATCHED_EMAILS);
        }
        return result;
    }

    private boolean isAppIdValid(Integer appId, OperationResponse operationResponse) {
        return !isNumberNullOrLEZero(appId, operationResponse, WRONG_APP_ID_1, WRONG_APP_ID_2);
    }

    private boolean isCardProductRequestsValid(List<CardProductRequest> cardProductRequests, OperationResponse operationResponse) {
        return
                !cardValidator.isListNullOrEmpty(cardProductRequests, operationResponse, WRONG_CARD_PRODUCT_REQUESTS_1, WRONG_CARD_PRODUCT_REQUESTS_2) &&
                        verifyCardProductRequests(cardProductRequests, operationResponse);
    }

    private boolean verifyCardProductRequests(List<CardProductRequest> cardProductRequests, OperationResponse operationResponse) {
        boolean result = true;
        int sz = cardProductRequests.size();
        for (int i = 0; i < sz; i++) {
            CardProductRequest request = cardProductRequests.get(i);
            if (request != null) {
                if (
                        !cardValidator.isCardTypeValid(request.getCardType(), "AZN", 3, operationResponse) ||
                                !isCardCountValid(request.getCardCount(), operationResponse)
                ) {
                    result = false;
                    operationResponse.setMessage(operationResponse.getMessage() + ", index[0-based]: " + i);
                    break;
                }
            }
        }
        return result;
    }

    private boolean isCardCountValid(Integer cardCount, OperationResponse operationResponse) {
        return !isNumberNullOrLEZero(cardCount, operationResponse, WRONG_CARD_COUNT_1, WRONG_CARD_COUNT_2);
    }

    public boolean isRequestValid(CheckCouponPaymentStatusRequest request, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                !isTransactionIdValid(request.getTransactionId(), operationResponse) ||
                        !cardValidator.isIpAddressValid(request.getIpAddress(), operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isTransactionIdValid(String transactionId, OperationResponse operationResponse) {
        return !cardValidator.isStringNullOrEmpty(transactionId, operationResponse, WRONG_TRANSACTION_ID_1, WRONG_TRANSACTION_ID_2);
    }

    private boolean isNumberNullOrDifferentList(Number field, List<Number> numbersToCompare, OperationResponse operationResponse, String msg1, String msg2) {
        boolean result = false;
        if (field == null) {
            result = true;
            operationResponse.setMessage(msg1);
        } else {
            Optional<Number> res = numbersToCompare.stream().filter(number -> number.equals(field)).findAny();
            if (!res.isPresent()) {
                result = true;
                operationResponse.setMessage(msg2);
            }
        }
        return result;
    }

    private boolean isNumberNullOrLEZero(Number field, OperationResponse operationResponse, String msg1, String msg2) {
        boolean result = false;
        if (field == null) {
            result = true;
            operationResponse.setMessage(msg1);
        } else if (field.intValue() <= 0) {
            result = true;
            operationResponse.setMessage(msg2);
        }
        return result;
    }

}
