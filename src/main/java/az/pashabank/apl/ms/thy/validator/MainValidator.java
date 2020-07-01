package az.pashabank.apl.ms.thy.validator;

import az.pashabank.apl.ms.thy.constants.Regex;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.logger.UFCLogger;
import az.pashabank.apl.ms.thy.model.*;
import az.pashabank.apl.ms.thy.model.thy.CreateNewCustomerOrderRequest;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyRequest;
import az.pashabank.apl.ms.thy.utils.ContentTypeUtils;
import az.pashabank.apl.ms.thy.utils.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class MainValidator {

    private static final UFCLogger LOGGER = UFCLogger.getLogger(MainValidator.class);

    private static final String WRONG_REQUEST = "WRONG REQUEST: Mandatory request parameter is null";
    private static final String WRONG_PIN_1 = "WRONG PIN: Pin is null";
    private static final String WRONG_PIN_2 = "WRONG PIN: Pin is empty";
    private static final String WRONG_PIN_3 = "WRONG PIN: Pin is not alpha numeric";
    private static final String WRONG_MOBILE_NO_1 = "WRONG MOBILE NO: Mobile number is null";
    private static final String WRONG_MOBILE_NO_2 = "WRONG MOBILE NO: Mobile number is empty";
    private static final String WRONG_MOBILE_NO_3 = "WRONG MOBILE NO: Mobile number is invalid (correct format: +994XXXXXXXXX)";
    private static final String WRONG_LANG_1 = "WRONG LANG: Language is null";
    private static final String WRONG_LANG_2 = "WRONG LANG: Language is empty";
    private static final String WRONG_OTP_1 = "WRONG OTP: OTP code is null";
    private static final String WRONG_OTP_2 = "WRONG OTP: OTP code is empty";
    private static final String WRONG_OTP_3 = "WRONG OTP: OTP code is not valid";
    private static final String WRONG_TK_1 = "WRONG TK: TK number is null";
    private static final String WRONG_TK_2 = "WRONG TK: TK number is empty";
    private static final String WRONG_TK_3 = "WRONG TK: TK number is not alphanumeric";
    private static final String WRONG_NAME_1 = "WRONG NAME: Name is null";
    private static final String WRONG_NAME_2 = "WRONG NAME: Name is empty";
    private static final String WRONG_NAME_3 = "WRONG NAME: Name contains invalid characters";
    private static final String WRONG_SURNAME_1 = "WRONG SURNAME: Surname is null";
    private static final String WRONG_SURNAME_2 = "WRONG SURNAME: Surname is empty";
    private static final String WRONG_SURNAME_3 = "WRONG SURNAME: Surname contains invalid characters";
    private static final String WRONG_BIRTH_DATE_1 = "WRONG BIRTH DATE: Birth date is null";
    private static final String WRONG_BIRTH_DATE_2 = "WRONG BIRTH DATE: Birth date is empty";
    private static final String WRONG_BIRTH_DATE_3 = "WRONG BIRTH DATE: Birth date is invalid (correct format: dd.mm.yyyy)";
    private static final String WRONG_EMAIL_1 = "WRONG EMAIL: Email is null";
    private static final String WRONG_EMAIL_2 = "WRONG EMAIL: Email is empty";
    private static final String WRONG_EMAIL_3 = "WRONG EMAIL: Email is invalid";
    private static final String WRONG_SECRET_CODE_1 = "WRONG SECRET CODE: Secret code is null";
    private static final String WRONG_SECRET_CODE_2 = "WRONG SECRET CODE: Secret code length is less than 5 allowed";
    private static final String WRONG_SECRET_CODE_3 = "WRONG SECRET CODE: Secret code length is greater than 8 allowed";
    private static final String WRONG_SECRET_CODE_4 = "WRONG SECRET CODE: Secret code is not numeric";
    private static final String WRONG_PASSPORT_NAME_1 = "WRONG PASSPORT NAME: Passport name is null";
    private static final String WRONG_PASSPORT_NAME_2 = "WRONG PASSPORT NAME: Passport name is empty";
    private static final String WRONG_PASSPORT_SURNAME_1 = "WRONG PASSPORT SURNAME: Passport surname is null";
    private static final String WRONG_PASSPORT_SURNAME_2 = "WRONG PASSPORT SURNAME: Passport surname is empty";
    private static final String WRONG_SECURITY_QUESTION_1 = "WRONG SECURITY QUESTION: Security question is null";
    private static final String WRONG_SECURITY_QUESTION_2 = "WRONG SECURITY QUESTION: Security question is empty";
    private static final String WRONG_SECURITY_ANSWER_1 = "WRONG SECURITY ANSWER: Security answer is null";
    private static final String WRONG_SECURITY_ANSWER_2 = "WRONG EMAIL: Security answer is empty";
    private static final String WRONG_PASSWORD_1 = "WRONG PASSWORD: Password is null";
    private static final String WRONG_PASSWORD_2 = "WRONG PASSWORD: Password is empty";
    private static final String WRONG_PASSWORD_3 = "WRONG PASSWORD: Password is not numeric";
    private static final String WRONG_REPEAT_PASSWORD_1 = "WRONG REPEAT PASSWORD: Password repetition is null";
    private static final String WRONG_REPEAT_PASSWORD_2 = "WRONG REPEAT PASSWORD: Password repetition is empty";
    private static final String WRONG_REPEAT_PASSWORD_3 = "WRONG REPEAT PASSWORD: Password repetition is not numeric";
    private static final String UNMATCHED_PASSWORDS = "UNMATCHED PASSWORDS: Passwords are different";
    private static final String WRONG_RESIDENCY_1 = "WRONG RESIDENCY: Residency is null";
    private static final String WRONG_RESIDENCY_2 = "WRONG RESIDENCY: Residency is empty";
    private static final String WRONG_NATIONALITY_1 = "WRONG NATIONALITY: Nationality is null";
    private static final String WRONG_NATIONALITY_2 = "WRONG NATIONALITY: Nationality is empty";
    private static final String WRONG_MIDDLE_NAME_1 = "WRONG MIDDLE NAME: Middle name is null";
    private static final String WRONG_MIDDLE_NAME_2 = "WRONG MIDDLE NAME: Middle name is empty";
    private static final String WRONG_MIDDLE_NAME_3 = "WRONG MIDDLE NAME: Middle name contains invalid characters";
    private static final String WRONG_GENDER_1 = "WRONG GENDER: Gender is null";
    private static final String WRONG_GENDER_2 = "WRONG GENDER: Gender length is not 1 allowed (correct format: M or F)";
    private static final String WRONG_REGISTRATION_CITY_1 = "WRONG REGISTRATION CITY: Registration city is null";
    private static final String WRONG_REGISTRATION_CITY_2 = "WRONG REGISTRATION CITY: Registration city is empty";
    private static final String WRONG_REGISTRATION_ADDRESS_1 = "WRONG REGISTRATION ADDRESS: Registration address is null";
    private static final String WRONG_REGISTRATION_ADDRESS_2 = "WRONG REGISTRATION ADDRESS: Registration address is empty";
    private static final String WRONG_DOMICILE_CITY_1 = "WRONG DOMICILE CITY: Domicile city is null";
    private static final String WRONG_DOMICILE_CITY_2 = "WRONG DOMICILE CITY: Domicile city is empty";
    private static final String WRONG_DOMICILE_ADDRESS_1 = "WRONG DOMICILE ADDRESS: Domicile address is null";
    private static final String WRONG_DOMICILE_ADDRESS_2 = "WRONG DOMICILE ADDRESS: Domicile address is empty";
    private static final String WRONG_FILE_UPLOADS_1 = "WRONG FILE UPLOADS: File uploads list is null";
    private static final String WRONG_FILE_UPLOADS_2 = "WRONG FILE UPLOADS: File uploads list is empty";
    private static final String INTERNAL_ERROR_1 = "INTERNAL SERVER ERROR: Writing uploaded files is unsuccessful";
    private static final String INTERNAL_ERROR_2 = "INTERNAL SERVER ERROR: Deleting uploaded files is unsuccessful";
    private static final String WRONG_FILE_UPLOADS_3 = "WRONG FILE UPLOADS: Size of file %d is 0";
    private static final String WRONG_FILE_UPLOADS_4 = "WRONG FILE UPLOADS: Size of file %d is greater than 3M";
    private static final String WRONG_FILE_UPLOADS_5 = "WRONG FILE UPLOADS: Extension of file %d is invalid";
    private static final String WRONG_WORKPLACE_1 = "WRONG WORKPLACE: Workplace is null";
    private static final String WRONG_WORKPLACE_2 = "WRONG WORKPLACE: Workplace is empty";
    private static final String WRONG_WORKPLACE_3 = "WRONG WORKPLACE: Workplace contains invalid characters";
    private static final String WRONG_POSITION_1 = "WRONG POSITION: Position is null";
    private static final String WRONG_POSITION_2 = "WRONG POSITION: Position is empty";
    private static final String WRONG_POSITION_3 = "WRONG POSITION: Position contains invalid characters";
    private static final String WRONG_TK_AND_REGISTER_CUSTOMER_IN_THY = "WRONG TK AND THY CUSTOMER REGISTRATION INFO: Both TK number and THY customer registration info fields are null or empty";
    private static final String WRONG_CRS_ANSWERS_1 = "WRONG CRS ANSWERS: CRS answers is null";
    private static final String WRONG_CRS_ANSWERS_2 = "WRONG CRS ANSWERS: CRS answers is empty";
    private static final String WRONG_CRS_ANSWER = "WRONG CRS ANSWER: CRS answer %d choice is null";
    private static final String WRONG_CRS_ANSWER_DESC_1 = "WRONG CRS ANSWER DESCRIPTION: CRS answer %d description is null";
    private static final String WRONG_CRS_ANSWER_DESC_2 = "WRONG CRS ANSWER DESCRIPTION: CRS answer %d description is empty";
    private static final String WRONG_ACCEPTED_TERMS_1 = "WRONG ACCEPTED TERMS: Accepted terms is null";
    private static final String WRONG_ACCEPTED_TERMS_3 = "WRONG ACCEPTED TERMS: Accepted terms is false";
    private static final String WRONG_ACCEPTED_GSA_1 = "WRONG ACCEPTED GSA: Accepted GSA is null";
    private static final String WRONG_ACCEPTED_GSA_3 = "WRONG ACCEPTED GSA: Accepted GSA is false";
    private static final String WRONG_CURRENCY_1 = "WRONG CURRENCY: Currency is null";
    private static final String WRONG_CURRENCY_2 = "WRONG CURRENCY: Currency is empty";
    private static final String WRONG_CARD_TYPE_1 = "WRONG CARD TYPE: Card type is null";
    private static final String WRONG_CARD_TYPE_2 = "WRONG CARD TYPE: Card type is incorrect";
    private static final String WRONG_PERIOD = "WRONG PERIOD: Period is null";
    private static final String WRONG_BRANCH_CODE_1 = "WRONG BRANCH CODE: Branch code is null";
    private static final String WRONG_BRANCH_CODE_2 = "WRONG BRANCH CODE: Branch code is empty";
    private static final String WRONG_BRANCH_CODE_3 = "WRONG BRANCH CODE: Branch code is incorrect";
    private static final String WRONG_TRANSACTION_ID_1 = "WRONG TRANSACTION ID: Transaction id is null";
    private static final String WRONG_TRANSACTION_ID_2 = "WRONG TRANSACTION ID: Transaction id is empty";
    private static final String WRONG_IP_ADDRESS_1 = "WRONG IP ADDRESS: Ip address is null";
    private static final String WRONG_IP_ADDRESS_2 = "WRONG IP ADDRESS: Ip address is empty";

    @Value("${upload.folder.thy_applications}")
    protected String uploadFolder;

    @Value("${upload.folder.thy_applications}")
    public void createUploadFolder(String uploadFolder) {
        try {
            Path uploadFolderPath = Paths.get(uploadFolder);
            if (!uploadFolderPath.toFile().exists()) {
                Files.createDirectories(uploadFolderPath);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Autowired
    private MainDao mainDao;

    public boolean isRequestValid(SendOtpRequest request, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                !isPinValid(request.getPin(), operationResponse) ||
                        !isMobileNoValid(request.getMobileNo(), operationResponse) ||
                        !isLangValid(request.getLang(), operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    public boolean isPinValid(String pin, OperationResponse operationResponse) {
        return validateStringAlphaNum(pin, operationResponse, WRONG_PIN_1, WRONG_PIN_2, WRONG_PIN_3);
    }

    private boolean isMobileNoValid(String mobileNo, OperationResponse operationResponse) {
        return validateStringPhone(mobileNo, operationResponse, WRONG_MOBILE_NO_1, WRONG_MOBILE_NO_2, WRONG_MOBILE_NO_3);
    }

    private boolean isLangValid(String lang, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(lang, operationResponse, WRONG_LANG_1, WRONG_LANG_2);
    }

    public boolean isRequestValid(CheckOtpRequest request, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                !isOtpValid(request.getOtp(), operationResponse) || !isPinValid(request.getPin(), operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isOtpValid(String otp, OperationResponse operationResponse) {
        return validateStringNumber(otp, operationResponse, WRONG_OTP_1, WRONG_OTP_2, WRONG_OTP_3);
    }

    public boolean isTkValid(String tk, OperationResponse operationResponse) {
        return validateStringAlphaNum(tk, operationResponse, WRONG_TK_1, WRONG_TK_2, WRONG_TK_3);
    }

    public boolean isRequestValid(RegisterCustomerInThyRequest request, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                !isNameValid(request.getName(), operationResponse) ||
                        !isSurnameValid(request.getSurname(), operationResponse) ||
                        !isNationalityValid(request.getNationality(), operationResponse) ||
                        !isBirthDateValid(request.getBirthDate(), operationResponse) ||
                        !isEmailValid(request.getEmail(), operationResponse) ||
                        !isMobileNoValid(request.getMobileNo(), operationResponse) ||
                        !areThyRegistrationSpecificFieldsValid(
                                request.getName(), request.getSurname(),
                                request.getSecurityQuestion(), request.getSecurityAnswer(),
                                request.getPassword(), request.getRepeatPassword(),
                                operationResponse
                        )
        ) {
            isValid = false;
        }
        return isValid;
    }

    public boolean isRequestValidForUi(RegisterCustomerInThyRequest request, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                !isNameValid(request.getName(), operationResponse) ||
                        !isSurnameValid(request.getSurname(), operationResponse) ||
                        !isNationalityValid(request.getNationality(), operationResponse) ||
                        !isBirthDateValid(request.getBirthDate(), operationResponse) ||
                        !isEmailValid(request.getEmail(), operationResponse) ||
                        !isMobileNoValid(request.getMobileNo(), operationResponse) ||
                        !areThyRegistrationSpecificFieldsValidForUi(
                                request.getName(), request.getSurname(),
                                request.getPassword(), request.getRepeatPassword(),
                                operationResponse
                        )
        ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean areThyRegistrationSpecificFieldsValidForUi(
            String passportName, String passportSurname,
            String password, String repeatPassword,
            OperationResponse operationResponse
    ) {
        boolean result = true;
        if (
                !isPassportNameValid(passportName, operationResponse) ||
                        !isPassportSurnameValid(passportSurname, operationResponse) ||
//                        !isSecurityQuestionValid(securityQuestion, operationResponse) ||
//                        !isSecurityAnswerValid(securityAnswer, operationResponse) ||
                        !isPasswordValid(password, operationResponse) ||
                        !isRepeatPasswordValid(repeatPassword, operationResponse) ||
                        !arePasswordsSame(password, repeatPassword, operationResponse)
        ) {
            result = false;
        }
        return result;
    }

    private boolean isNameValid(String name, OperationResponse operationResponse) {
        return validateStringLetters(name, operationResponse, WRONG_NAME_1, WRONG_NAME_2, WRONG_NAME_3);
    }

    private boolean isSurnameValid(String surname, OperationResponse operationResponse) {
        return validateStringLetters(surname, operationResponse, WRONG_SURNAME_1, WRONG_SURNAME_2, WRONG_SURNAME_3);
    }

    private boolean isNationalityValid(String nationality, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(nationality, operationResponse, WRONG_NATIONALITY_1, WRONG_NATIONALITY_2);
    }

    private boolean isBirthDateValid(String birthDate, OperationResponse operationResponse) {
        return validateStringDate(birthDate, operationResponse, WRONG_BIRTH_DATE_1, WRONG_BIRTH_DATE_2, WRONG_BIRTH_DATE_3);
    }

    private boolean isEmailValid(String email, OperationResponse operationResponse) {
        return validateStringEmail(email, operationResponse, WRONG_EMAIL_1, WRONG_EMAIL_2, WRONG_EMAIL_3);
    }

    private boolean isSecretCodeValid(String secretCode, OperationResponse operationResponse) {
        return validateStringLengthRangeNumber(
                secretCode, 5, 8, operationResponse,
                WRONG_SECRET_CODE_1, WRONG_SECRET_CODE_2, WRONG_SECRET_CODE_3, WRONG_SECRET_CODE_4
        );
    }

    private boolean areThyRegistrationSpecificFieldsValid(
            String passportName, String passportSurname,
            String securityQuestion, String securityAnswer,
            String password, String repeatPassword,
            OperationResponse operationResponse
    ) {
        boolean result = true;
        if (
                !isPassportNameValid(passportName, operationResponse) ||
                        !isPassportSurnameValid(passportSurname, operationResponse) ||
                        !isSecurityQuestionValid(securityQuestion, operationResponse) ||
                        !isSecurityAnswerValid(securityAnswer, operationResponse) ||
                        !isPasswordValid(password, operationResponse) ||
                        !isRepeatPasswordValid(repeatPassword, operationResponse) ||
                        !arePasswordsSame(password, repeatPassword, operationResponse)
        ) {
            result = false;
        }
        return result;
    }

    private boolean isPassportNameValid(String passportName, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(passportName, operationResponse, WRONG_PASSPORT_NAME_1, WRONG_PASSPORT_NAME_2);
    }

    private boolean isPassportSurnameValid(String passportSurname, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(passportSurname, operationResponse, WRONG_PASSPORT_SURNAME_1, WRONG_PASSPORT_SURNAME_2);
    }

    private boolean isSecurityQuestionValid(String securityQuestion, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(securityQuestion, operationResponse, WRONG_SECURITY_QUESTION_1, WRONG_SECURITY_QUESTION_2);
    }

    private boolean isSecurityAnswerValid(String securityAnswer, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(securityAnswer, operationResponse, WRONG_SECURITY_ANSWER_1, WRONG_SECURITY_ANSWER_2);
    }

    private boolean isPasswordValid(String password, OperationResponse operationResponse) {
        return validateStringNumber(password, operationResponse, WRONG_PASSWORD_1, WRONG_PASSWORD_2, WRONG_PASSWORD_3);
    }

    private boolean isRepeatPasswordValid(String repeatPassword, OperationResponse operationResponse) {
        return validateStringNumber(repeatPassword, operationResponse, WRONG_REPEAT_PASSWORD_1, WRONG_REPEAT_PASSWORD_2, WRONG_REPEAT_PASSWORD_3);
    }

    private boolean arePasswordsSame(String password, String repeatPassword, OperationResponse operationResponse) {
        boolean result = true;
        if (!password.equals(repeatPassword)) {
            result = false;
            operationResponse.setMessage(UNMATCHED_PASSWORDS);
        }
        return result;
    }

    public boolean isRequestValid(CreateNewCustomerOrderRequest request, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                !isResidencyValid(request.getResidency(), operationResponse) ||
                        !isNationalityValid(request.getNationality(), operationResponse) ||
                        !isNameValid(request.getName(), operationResponse) ||
                        !isSurnameValid(request.getSurname(), operationResponse) ||
//                        !isMiddleNameValid(request.getMiddleName(), operationResponse) ||
                        !isGenderValid(request.getGender(), operationResponse) ||
                        !isBirthDateValid(request.getBirthDate(), operationResponse) ||
                        !isFileUploadsValid(request.getFileUploads(), operationResponse) ||
                        !isRegistrationCityValid(request.getRegistrationCity(), operationResponse) ||
                        !isRegistrationAddressValid(request.getRegistrationAddress(), operationResponse) ||
                        //!isDomicileCityValid(request.getDomicileCity(), operationResponse) ||
                        //!isDomicileAddressValid(request.getDomicileAddress(), operationResponse) ||
                        !isMobileNoValid(request.getMobileNo(), operationResponse) ||
                        !isEmailValid(request.getEmail(), operationResponse) ||
                        !isSecretCodeValid(request.getSecretCode(), operationResponse) ||
//                        !isWorkplaceValid(request.getWorkplace(), operationResponse) ||
//                        !isPositionValid(request.getPosition(), operationResponse) ||
                        !areTkAndThyCustomerRegistrationFieldsValid(
                                request.getTkNo(),
                                request.getPassportName(), request.getPassportSurname(),
                                request.getSecurityQuestion(), request.getSecurityAnswer(),
                                request.getPassword(), request.getRepeatPassword(),
                                operationResponse
                        ) ||
                        !isCrsAnswersValid(request.getCrsAnswers(), operationResponse) ||
                        !isAcceptedTermsValid(request.getAcceptedTerms(), operationResponse) ||
                        !isAcceptedGsaValid(request.getAcceptedGsa(), operationResponse) ||
                        !isCurrencyValid(request.getCurrency(), operationResponse) ||
                        !isCardTypeValid(request.getCardType(), request.getCurrency(), request.getPeriod(), operationResponse) ||
                        !isPeriodValid(request.getPeriod(), operationResponse) ||
                        !isBranchCodeValid(request.getBranchCode(), lang, operationResponse)
        ) {
            isValid = false;
        }
        if (!isValid)
            return false;
        if (!isStringNullOrEmpty(request.getTkNo())) {
            if (!isStringAlphaNum(request.getTkNo(), operationResponse, WRONG_TK_3)) {
                isValid = false;
            }
        } else if (
                !areThyRegistrationSpecificFieldsValid(
                        request.getPassportName(), request.getPassportSurname(),
                        request.getSecurityQuestion(), request.getSecurityAnswer(),
                        request.getPassword(), request.getRepeatPassword(),
                        operationResponse
                )
        ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isResidencyValid(String residency, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(residency, operationResponse, WRONG_RESIDENCY_1, WRONG_RESIDENCY_2);
    }

    /*private boolean isMiddleNameValid(String middleName, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(middleName, operationResponse, WRONG_MIDDLE_NAME_1, WRONG_MIDDLE_NAME_2);
    }*/

    private boolean isGenderValid(String gender, OperationResponse operationResponse) {
        return !isStringNullOrDifferentLength(gender, 1, operationResponse, WRONG_GENDER_1, WRONG_GENDER_2);
    }

    private boolean isFileUploadsValid(List<UploadWrapper> fileUploads, OperationResponse operationResponse) {
        return validateListUploadFile(
                fileUploads, operationResponse,
                WRONG_FILE_UPLOADS_1, WRONG_FILE_UPLOADS_2, WRONG_FILE_UPLOADS_3, WRONG_FILE_UPLOADS_4, WRONG_FILE_UPLOADS_5
        );
    }

    private boolean isRegistrationCityValid(String registrationCity, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(registrationCity, operationResponse, WRONG_REGISTRATION_CITY_1, WRONG_REGISTRATION_CITY_2);
    }

    private boolean isRegistrationAddressValid(String registrationAddress, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(registrationAddress, operationResponse, WRONG_REGISTRATION_ADDRESS_1, WRONG_REGISTRATION_ADDRESS_2);
    }

    /*private boolean isDomicileCityValid(String domicileCity, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(domicileCity, operationResponse, WRONG_DOMICILE_CITY_1, WRONG_DOMICILE_CITY_2);
    }

    private boolean isDomicileAddressValid(String domicileAddress, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(domicileAddress, operationResponse, WRONG_DOMICILE_ADDRESS_1, WRONG_DOMICILE_ADDRESS_2);
    }*/

    private boolean isWorkplaceValid(String workplace, OperationResponse operationResponse) {
        return validateStringLetterSpaces(workplace, operationResponse, WRONG_WORKPLACE_1, WRONG_WORKPLACE_2, WRONG_WORKPLACE_3);
    }

    private boolean isPositionValid(String position, OperationResponse operationResponse) {
        return validateStringLetterSpaces(position, operationResponse, WRONG_POSITION_1, WRONG_POSITION_2, WRONG_POSITION_3);
    }

    private boolean areTkAndThyCustomerRegistrationFieldsValid(
            String tkNo,
            String passportName, String passportSurname,
            String securityQuestion, String securityAnswer,
            String password, String repeatPassword,
            OperationResponse operationResponse
    ) {
        boolean result = true;
        if (
                isStringNullOrEmpty(tkNo) &&
                        isStringNullOrEmpty(passportName) &&
                        isStringNullOrEmpty(passportSurname) &&
                        isStringNullOrEmpty(securityQuestion) &&
                        isStringNullOrEmpty(securityAnswer) &&
                        isStringNullOrEmpty(password) &&
                        isStringNullOrEmpty(repeatPassword)
        ) {
            result = false;
            operationResponse.setMessage(WRONG_TK_AND_REGISTER_CUSTOMER_IN_THY);
        }
        return result;
    }

    private boolean isCrsAnswersValid(List<CRSAnswer> crsAnswers, OperationResponse operationResponse) {
        return
                !isListNullOrEmpty(crsAnswers, operationResponse, WRONG_CRS_ANSWERS_1, WRONG_CRS_ANSWERS_2) &&
                        validateAllCrsAnswers(crsAnswers, operationResponse)
                ;
    }

    private boolean isAcceptedTermsValid(Integer acceptedTerms, OperationResponse operationResponse) {
        return
                !isNumberNull(acceptedTerms, operationResponse, WRONG_ACCEPTED_TERMS_1) &&
                        isAccepted(acceptedTerms, operationResponse, WRONG_ACCEPTED_TERMS_3)
                ;
    }

    private boolean isAcceptedGsaValid(Integer acceptedGsa, OperationResponse operationResponse) {
        return
                !isNumberNull(acceptedGsa, operationResponse, WRONG_ACCEPTED_GSA_1) &&
                        isAccepted(acceptedGsa, operationResponse, WRONG_ACCEPTED_GSA_3)
                ;
    }

    private boolean isCurrencyValid(String currency, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(currency, operationResponse, WRONG_CURRENCY_1, WRONG_CURRENCY_2);
    }

    private boolean isCardTypeValid(Integer cardType, String currency, Integer period, OperationResponse operationResponse) {
        return !isNumberNull(cardType, operationResponse, WRONG_CARD_TYPE_1) && verifyCard(cardType, currency, period, operationResponse);
    }

    private boolean isPeriodValid(Integer period, OperationResponse operationResponse) {
        return !isNumberNull(period, operationResponse, WRONG_PERIOD);
    }

    private boolean isBranchCodeValid(String branchCode, String lang, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(branchCode, operationResponse, WRONG_BRANCH_CODE_1, WRONG_BRANCH_CODE_2) && verifyBranch(branchCode, lang, operationResponse);
    }

    public boolean isRequestValid(CheckPaymentStatusRequest request, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                !isTransactionIdValid(request.getTransactionId(), operationResponse) ||
                        !isIpAddressValid(request.getIpAddress(), operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isTransactionIdValid(String transactionId, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(transactionId, operationResponse, WRONG_TRANSACTION_ID_1, WRONG_TRANSACTION_ID_2);
    }

    private boolean isIpAddressValid(String ipAddress, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(ipAddress, operationResponse, WRONG_IP_ADDRESS_1, WRONG_IP_ADDRESS_2);
    }

    private boolean validateAllCrsAnswers(List<CRSAnswer> crsAnswers, OperationResponse operationResponse) {
        boolean result = true;
        int anketsCount = crsAnswers.size();
        for (int i = 0; i < anketsCount; i++) {
            if (!isCrsAnswerValid(crsAnswers.get(i), i, operationResponse)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private boolean isCrsAnswerValid(CRSAnswer crsAnswer, int index, OperationResponse operationResponse) {
        boolean result = true;
        Integer answer = crsAnswer.getAnswer();
        String desc = crsAnswer.getDescription();
        if (answer == null) {
            result = false;
            operationResponse.setMessage(String.format(WRONG_CRS_ANSWER, index));
        } else if (answer == 1 && desc == null) {
            result = false;
            operationResponse.setMessage(String.format(WRONG_CRS_ANSWER_DESC_1, index));
        } else if (answer == 1 && desc.trim().isEmpty()) {
            result = false;
            operationResponse.setMessage(String.format(WRONG_CRS_ANSWER_DESC_2, index));
        }
        return result;
    }

    private boolean isAccepted(Integer acceptedTerms, OperationResponse operationResponse, String msg) {
        boolean result = true;
        if (acceptedTerms == 0) {
            result = false;
            operationResponse.setMessage(msg);
        }
        return result;
    }

    private boolean verifyCard(Integer cardType, String currency, Integer period, OperationResponse operationResponse) {
        boolean result = false;
        int cardTypeValue = cardType.intValue();
        List<Card> cards = mainDao.getActiveCards(currency, period);
        for (Card card : cards) {
            if (card.getId() == cardTypeValue) {
                result = true;
                break;
            }
        }
        if (!result) {
            operationResponse.setMessage(WRONG_CARD_TYPE_2);
        }
        return result;
    }

    private boolean verifyBranch(String branchCode, String lang, OperationResponse operationResponse) {
        boolean result = false;
        List<Branch> branches = mainDao.getBranchList(lang);
        for (Branch branch : branches) {
            if (Objects.equals(branch.getBranchCode(), branchCode)) {
                result = true;
                break;
            }
        }
        if (!result) {
            operationResponse.setMessage(WRONG_BRANCH_CODE_3);
        }
        return result;
    }

    private boolean validateStringAlphaNum(String field, OperationResponse operationResponse, String... messages) {
        return
                !isStringNullOrEmpty(field, operationResponse, messages[0], messages[1]) &&
                        isStringAlphaNum(field, operationResponse, messages[2])
                ;
    }

    private boolean validateStringPhone(String field, OperationResponse operationResponse, String... messages) {
        return
                !isStringNullOrEmpty(field, operationResponse, messages[0], messages[1]) &&
                        isStringPhone(field, operationResponse, messages[2])
                ;
    }

    private boolean validateStringNumber(String field, OperationResponse operationResponse, String... messages) {
        return
                !isStringNullOrEmpty(field, operationResponse, messages[0], messages[1]) &&
                        isStringNumber(field, operationResponse, messages[2])
                ;
    }

    private boolean validateStringLengthRangeNumber(String field, int lowerBound, int upperBound, OperationResponse operationResponse, String... messages) {
        return
                !isStringNullOrOutRange(field, lowerBound, upperBound, operationResponse, messages[0], messages[1], messages[2]) &&
                        isStringNumber(field, operationResponse, messages[3])
                ;
    }

    private boolean validateStringLetters(String field, OperationResponse operationResponse, String... messages) {
        return
                !isStringNullOrEmpty(field, operationResponse, messages[0], messages[1]) &&
                        isStringLetters(field, operationResponse, messages[2])
                ;
    }

    private boolean validateStringDate(String field, OperationResponse operationResponse, String... messages) {
        return
                !isStringNullOrEmpty(field, operationResponse, messages[0], messages[1]) &&
                        isStringDate(field, operationResponse, messages[2])
                ;
    }

    private boolean validateStringEmail(String field, OperationResponse operationResponse, String... messages) {
        return
                !isStringNullOrEmpty(field, operationResponse, messages[0], messages[1]) &&
                        isStringEmail(field, operationResponse, messages[2])
                ;
    }

    private boolean validateListUploadFile(List<UploadWrapper> list, OperationResponse operationResponse, String... messages) {
        return
                !isListNullOrEmpty(list, operationResponse, messages[0], messages[1]) &&
                        isListUploadFile(list, operationResponse, messages[2], messages[3], messages[4])
                ;
    }

    private boolean validateStringLetterSpaces(String field, OperationResponse operationResponse, String... messages) {
        return
                !isStringNullOrEmpty(field, operationResponse, messages[0], messages[1]) &&
                        isStringLetterSpaces(field, operationResponse, messages[2])
                ;
    }

    public boolean isStringNullOrEmpty(String field) {
        boolean result = false;
        if (field == null || field.trim().isEmpty()) {
            result = true;
        }
        return result;
    }

    private boolean isStringNullOrEmpty(String field, OperationResponse operationResponse, String msg1, String msg2) {
        boolean result = false;
        if (field == null) {
            result = true;
            operationResponse.setMessage(msg1);
        } else if (field.trim().isEmpty()) {
            result = true;
            operationResponse.setMessage(msg2);
        }
        return result;
    }

    private boolean isStringNullOrOutRange(String field, int lowerBound, int upperBound, OperationResponse operationResponse, String msg1, String msg2, String msg3) {
        boolean result = false;
        if (field == null) {
            result = true;
            operationResponse.setMessage(msg1);
        } else if (field.trim().length() < lowerBound) {
            result = true;
            operationResponse.setMessage(msg2);
        } else if (field.trim().length() > upperBound) {
            result = true;
            operationResponse.setMessage(msg3);
        }
        return result;
    }

    private boolean isStringNullOrDifferentLength(String field, int length, OperationResponse operationResponse, String msg1, String msg2) {
        boolean result = false;
        if (field == null) {
            result = true;
            operationResponse.setMessage(msg1);
        } else if (field.trim().length() != length) {
            result = true;
            operationResponse.setMessage(msg2);
        }
        return result;
    }

    private boolean isListNullOrEmpty(List list, OperationResponse operationResponse, String msg1, String msg2) {
        boolean result = false;
        if (list == null) {
            result = true;
            operationResponse.setMessage(msg1);
        } else if (list.isEmpty()) {
            result = true;
            operationResponse.setMessage(msg2);
        }
        return result;
    }

    private boolean isNumberNull(Number field, OperationResponse operationResponse, String msg) {
        boolean result = false;
        if (field == null) {
            result = true;
            operationResponse.setMessage(msg);
        }
        return result;
    }

    private boolean isStringAlphaNum(@NotNull String field, OperationResponse operationResponse, String msg) {
        boolean result = true;
        if (!field.matches(Regex.ALPHANUM)) {
            result = false;
            operationResponse.setMessage(msg);
        }
        return result;
    }

    private boolean isStringPhone(@NotNull String field, OperationResponse operationResponse, String msg) {
        boolean result = true;
        if (!field.matches(Regex.PHONE)) {
            result = false;
            operationResponse.setMessage(msg);
        }
        return result;
    }

    private boolean isStringNumber(@NotNull String field, OperationResponse operationResponse, String msg) {
        boolean result = true;
        if (!field.matches(Regex.NUMBER)) {
            result = false;
            operationResponse.setMessage(msg);
        }
        return result;
    }

    private boolean isStringLetters(@NotNull String field, OperationResponse operationResponse, String msg) {
        boolean result = true;
        if (!field.matches(Regex.LETTERS)) {
            result = false;
            operationResponse.setMessage(msg);
        }
        return result;
    }

    private boolean isStringDate(@NotNull String field, OperationResponse operationResponse, String msg) {
        boolean result = true;
        if (!field.matches(Regex.DATE)) {
            result = false;
            operationResponse.setMessage(msg);
        }
        return result;
    }

    private boolean isStringEmail(@NotNull String field, OperationResponse operationResponse, String msg) {
        boolean result = true;
        if (!field.matches(Regex.EMAIL)) {
            result = false;
            operationResponse.setMessage(msg);
        }
        return result;
    }

    private boolean isListUploadFile(@NotNull List<UploadWrapper> uploadWrappers, OperationResponse operationResponse, String... msg) {
        boolean result = writeWrapperFiles(uploadWrappers, operationResponse);
        if (!result) {
            deleteWrapperFiles(uploadWrappers, operationResponse);
            return false;
        }
        int uploadWrappersSize = uploadWrappers.size();
        for (int i = 0; i < uploadWrappersSize; i++) {
            UploadWrapper uw = uploadWrappers.get(i);
            if (uw.getSize() == 0) {
                result = false;
                operationResponse.setMessage(String.format(msg[0], i));
                break;
            } else if (uw.getSize() > 1024 * 1024 * 3) {
                result = false;
                operationResponse.setMessage(String.format(msg[1], i));
                break;
            } else if (!uw.getContentType().matches(Regex.UPLOAD_FILE)) {
                result = false;
                operationResponse.setMessage(String.format(msg[2], i));
                break;
            }
        }
        if (!result) {
            deleteWrapperFiles(uploadWrappers, operationResponse);
            return false;
        }
        return true;
    }

    private boolean writeWrapperFiles(List<UploadWrapper> wrappers, OperationResponse operationResponse) {
        boolean result = true;
        try {
            for (UploadWrapper w : wrappers) {
                String fileName = Crypto.getDoubleUuid() + ContentTypeUtils.getExtension(w.getContentType());
                String location = uploadFolder + "/" + fileName;
                Path locationPath = Paths.get(location);
                Files.write(locationPath, w.getBytes());
                w.setSize(Files.size(locationPath));
                w.setName(fileName);
                w.setLocation(location);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            operationResponse.setMessage(INTERNAL_ERROR_1);
            result = false;
        }
        return result;
    }

    public void deleteWrapperFiles(List<UploadWrapper> wrappers, OperationResponse operationResponse) {
        int wrappersSize = wrappers.size();
        for (int i = 0; i < wrappersSize; i++) {
            String wrapperLocation = wrappers.get(i).getLocation();
            if (wrapperLocation != null && !wrapperLocation.trim().isEmpty()) {
                try {
                    Files.delete(Paths.get(wrapperLocation));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    operationResponse.setMessage(INTERNAL_ERROR_2);
                }
            }
        }
    }

    private boolean isStringLetterSpaces(@NotNull String field, OperationResponse operationResponse, String msg) {
        boolean result = true;
        if (!field.matches(Regex.LETTER_SPACES)) {
            result = false;
            operationResponse.setMessage(msg);
        }
        return result;
    }

    private boolean isStringCurrency(@NotNull String field, OperationResponse operationResponse, String msg) {
        boolean result = true;
        if (!field.matches(Regex.CURRENCY)) {
            result = false;
            operationResponse.setMessage(msg);
        }
        return result;
    }

}
