package az.pashabank.apl.ms.thy.constants;

/**
 * URLs for restful API
 */
public final class URL {

    public static final String POST_CREATE_NEW_CARD_ORDER_STEP1 = "/newCardOrder/step1/{lang}";
    public static final String POST_CREATE_NEW_CARD_ORDER_STEP2 = "/newCardOrder/step2/{lang}";
    public static final String POST_CREATE_NEW_CARD_ORDER_STEP3 = "/newCardOrder/step3/{lang}";
    public static final String POST_UPLOAD_FILES = "/uploadFiles/{lang}";
    public static final String GET_PROMO_CODE = "/promoCode/{lang}";
    public static final String GET_ANNUAL_INCOME_VALUES = "/incomeValues/{lang}";

    public static final String GET_MOBILE_NUMBERS_BY_PIN = "/mobileNumbers/{pin}";
    public static final String POST_SEND_OTP = "/sendOTP";
    public static final String POST_CHECK_OTP = "/checkOTP";
    public static final String GET_CHECK_TK = "/checkTK/{tk}";
    public static final String GET_CHECK_TK_UI = "/checkTKui/{tk}";
    public static final String RESEND_SMS = "/resendSms/{userId}";
    public static final String GET_USER_INFO = "/getUserInfo/{userId}";
    public static final String POST_REGISTER_CUSTOMER_IN_THY = "/registerCustomerInTHY/{lang}";
    public static final String POST_REGISTER_CUSTOMER_IN_THY_UI = "/registerCustomerInTHYui";
    public static final String POST_CREATE_NEW_CUSTOMER_ORDER = "/newCustomerOrder/{lang}";
    public static final String POST_CHECK_PAYMENT_STATUS = "/checkPaymentStatus/{lang}";
    public static final String POST_CREATE_NEW_COUPON_ORDER_STEP1 = "/newCouponOrder/step1/{lang}";
    public static final String POST_CREATE_NEW_COUPON_ORDER_STEP2 = "/newCouponOrder/step2/{lang}";
    public static final String POST_CREATE_NEW_COUPON_ORDER_STEP3 = "/newCouponOrder/step3/{lang}";
    public static final String POST_CHECK_COUPON_PAYMENT_STATUS = "/checkCouponPaymentStatus/{lang}";
    public static final String GET_COUPON_CARD_PRODUCTS = "/getCouponCardProducts/{lang}";
    public static final String GET_CRS_QUESTIONS = "/crsQuestions/{lang}";
    public static final String GET_BRANCH_LIST = "/getBranchList/{lang}";
    public static final String GET_CARD_PRODUCTS = "/getCardProductList/{lang}";
    public static final String GET_CITY_LIST = "/getCity/{contryCode}";
    public static final String GET_COUNTRY_LIST = "/getCountryList/{lang}";
    public static final String REFRESH_COUNTRY_LIST = "/refreshCountryList/{lang}";
    public static final String GET_SECURITY_QUESTION_LIST = "/securityQuestions/{lang}";
    public static final String POST_LOGIN = "/signin";
    public static final String POST_VALIDATE_COUPON_CODE = "/validateCouponCode/{lang}";

    public static final String ECOMM_REGISTER_PAYMENT = "/registerPayment/";
    public static final String ECOMM_GET_PAYMENT_STATUS = "/getPaymentStatus/{lang}/";
    public static final String THY_POST_GET_MEMBER_DETAILS = "/getMemberDetails/";
    public static final String THY_POST_MEMBER_OPERATIONS = "/memberOperations/";
    public static final String THY_POST_GET_SPECIFIC_COMBOBOX = "/getSpecificComboBox/";
    public static final String MS_SMS_SEND_SMS = "/sendSms/";

    private URL() {
    }

}
