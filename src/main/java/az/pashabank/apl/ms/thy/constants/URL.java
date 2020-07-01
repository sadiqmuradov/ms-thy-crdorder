package az.pashabank.apl.ms.thy.constants;

/**
 * URLs for restful API
 */
public final class URL {

    public static final String GET_MOBILE_NUMBERS_BY_PIN = "/mobileNumbers/{pin}";
    public static final String POST_SEND_OTP = "/sendOTP";
    public static final String POST_CHECK_OTP = "/checkOTP";
    public static final String GET_CHECK_TK = "/checkTK/{tk}";
    public static final String GET_CHECK_TK_UI = "/checkTKui/{tk}";
    public static final String RESEND_SMS = "/resendSms/{userId}";
    public static final String GET_USER_INFO = "/getUserInfo/{userId}";
    public static final String POST_REGISTER_CUSTOMER_IN_THY = "/registerCustomerInTHY";
    public static final String POST_REGISTER_CUSTOMER_IN_THY_UI = "/registerCustomerInTHYui";
    public static final String POST_CREATE_NEW_CUSTOMER_ORDER = "/newCustomerOrder/{lang}";
    public static final String GET_CRS_QUESTIONS = "/crsQuestions/{lang}";
    public static final String GET_BRANCH_LIST = "/getBranchList/{lang}";
    public static final String GET_CARD_PRODUCTS = "/getCardProductList/{lang}";
    public static final String GET_CITY_LIST = "/getCity/{contryCode}";
    public static final String GET_COUNTRY_LIST = "/getCountryList";
    public static final String POST_CHECK_PAYMENT_STATUS = "/checkPaymentStatus";
    public static final String GET_SECURITY_QUESTION_LIST = "/securityQuestions/{lang}";
    public static final String POST_LOGIN = "/signin";

    public static final String ECOMM_REGISTER_PAYMENT = "/registerPayment/";
    public static final String ECOMM_GET_PAYMENT_STATUS = "/getPaymentStatus/";
    public static final String THY_POST_GET_MEMBER_DETAILS = "/getMemberDetails/";
    public static final String THY_POST_MEMBER_OPERATIONS = "/memberOperations/";
    public static final String THY_POST_GET_SPECIFIC_COMBOBOX = "/getSpecificComboBox/";

    private URL() {
    }

}
