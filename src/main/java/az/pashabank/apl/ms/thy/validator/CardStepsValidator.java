package az.pashabank.apl.ms.thy.validator;

import az.pashabank.apl.ms.thy.constants.Regex;
import az.pashabank.apl.ms.thy.dao.CardStepsDao;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.entity.BranchEntity;
import az.pashabank.apl.ms.thy.entity.CRSAnswerEntity;
import az.pashabank.apl.ms.thy.entity.CRSQuestionEntity;
import az.pashabank.apl.ms.thy.entity.CardPriceEntity;
import az.pashabank.apl.ms.thy.entity.CardProductEntity;
import az.pashabank.apl.ms.thy.entity.CouponCodeEntity;
import az.pashabank.apl.ms.thy.entity.NetGrossIncomeEntity;
import az.pashabank.apl.ms.thy.entity.SourceOfIncomeEntity;
import az.pashabank.apl.ms.thy.entity.UploadWrapperEntity;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.CRSAnswer;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderStep1Request;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderStep2Request;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderStep3Request;
import az.pashabank.apl.ms.thy.model.GetPromoCodeRequest;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.UploadFilesRequest;
import az.pashabank.apl.ms.thy.model.UploadWrapper;
import az.pashabank.apl.ms.thy.model.thy.CheckTkRequest;
import az.pashabank.apl.ms.thy.model.thy.CheckTkRestResponse;
import az.pashabank.apl.ms.thy.model.thy.MemberData;
import az.pashabank.apl.ms.thy.model.thy.MemberDetails;
import az.pashabank.apl.ms.thy.proxy.ThyServiceProxy;
import az.pashabank.apl.ms.thy.service.LocalMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class CardStepsValidator {

    private static final MainLogger LOGGER = MainLogger.getLogger(CardStepsValidator.class);

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

    @Autowired
    private LocalMessageService localMessageService;

    @Autowired
    private CardValidator cardValidator;

    @Autowired
    private ThyServiceProxy thyServiceProxy;

    @Autowired
    CardStepsDao cardStepsDao;

    public boolean isRequestValid(CreateNewCardOrderStep1Request request, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            String msg = localMessageService.get("message.errors.invalid.request", new Locale(lang));
            operationResponse.setMessage(msg);
        } else if (
                !isNameValid(request.getName(), lang, operationResponse) ||
                        !isSurnameValid(request.getSurname(), lang, operationResponse) ||
                        !isMobileNoValid(request.getMobileNo(), lang, operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    public boolean isNameValid(String name, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.name", new Locale(lang));
//        return cardValidator.validateStringLetters(name, operationResponse, msg, msg, msg);
        return !cardValidator.isStringNullOrEmpty(name, operationResponse, msg, msg);
    }

    public boolean isSurnameValid(String surname, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.surname", new Locale(lang));
//        return cardValidator.validateStringLetters(surname, operationResponse, msg, msg, msg);
        return !cardValidator.isStringNullOrEmpty(surname, operationResponse, msg, msg);
    }

    public boolean isMobileNoValid(String mobileNo, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.mobile", new Locale(lang));
        return !cardValidator.isStringNullOrEmpty(mobileNo, operationResponse, msg, msg);
    }

    public boolean isRequestValid(CreateNewCardOrderStep2Request request, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            String msg = localMessageService.get("message.errors.invalid.request", new Locale(lang));
            operationResponse.setMessage(msg);
        } else if (
                !isAppIdValid(request.getAppId(), lang, operationResponse) ||
                        !isCardTypeValid(request, lang, operationResponse) ||
                        !isBranchCodeValid(request, lang, operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isAppIdValid(Integer appId, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (appId == null || appId.intValue() == 0) {
            isValid = false;
            String msg = localMessageService.get("message.errors.invalid.app_id", new Locale(lang));
            operationResponse.setMessage(msg);
        }
        return isValid;
    }

    private boolean isCardTypeValid(CreateNewCardOrderStep2Request request, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.card_type", new Locale(lang));
        String msg2 = localMessageService.get("message.errors.global_error", new Locale(lang));
        return
                !cardValidator.isNumberNull(request.getCardType(), operationResponse, msg) &&
                        isCardTypeVerified(request, operationResponse, msg, msg2)
                ;
    }

    private boolean isCardTypeVerified(CreateNewCardOrderStep2Request request, OperationResponse operationResponse, String msg1, String msg2) {
        boolean result = true;
        CardProductEntity cardProductEntity = cardStepsDao.getCardProductById(request.getCardType());
        if (cardProductEntity == null) {
            result = false;
            operationResponse.setMessage(msg1);
        } else {
            CardPriceEntity cardPriceEntity = cardStepsDao.getCardPriceByCardProductId(request.getCardType());
            if(cardPriceEntity == null) {
                result = false;
                operationResponse.setMessage(msg2);
            } else {
                int amountToPay = request.isUrgent() ? cardProductEntity.getUrgency() : 0;
                if (request.getCouponId() != null) {
                    CouponCodeEntity couponCodeEntity = cardStepsDao.getCouponCodeById(request.getCouponId());
                    if (couponCodeEntity == null) {
                        result = false;
                        operationResponse.setMessage(msg2);
                    } else {
                        amountToPay += cardPriceEntity.getLcyAmount() > couponCodeEntity.getCouponPrice() ? cardPriceEntity.getLcyAmount() - couponCodeEntity.getCouponPrice() : 0;
                    }
                } else {
                    amountToPay += cardPriceEntity.getLcyAmount();
                }
                request.setAmountToPay(amountToPay);
            }
        }
        return result;
    }

    private boolean isBranchCodeValid(CreateNewCardOrderStep2Request request, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.branch_code", new Locale(lang));
        return
                !cardValidator.isStringNullOrEmpty(request.getBranchCode(), operationResponse, msg, msg) &&
                        isBranchCodeVerified(request, lang, operationResponse, msg)
                ;
    }

    private boolean isBranchCodeVerified(CreateNewCardOrderStep2Request request, String lang, OperationResponse operationResponse, String msg) {
        boolean result = true;
        BranchEntity branchEntity = cardStepsDao.getBranchByCodeAndLang(request.getBranchCode(), lang);
        if (branchEntity == null) {
            result = false;
            operationResponse.setMessage(msg);
        } else {
            request.setBranchName(branchEntity.getName());
        }
        return result;
    }

    public boolean isRequestValid(UploadFilesRequest request, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            String msg = localMessageService.get("message.errors.invalid.request", new Locale(lang));
            operationResponse.setMessage(msg);
        } else if (
                !isAppIdValid(request.getAppId(), lang, operationResponse) ||
                        !isFileUploadsValid(request, lang, operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isFileUploadsValid(UploadFilesRequest request, String lang, OperationResponse operationResponse) {
        return areAllUploadsValidated(request, lang, operationResponse);
    }

    private boolean areAllUploadsValidated(UploadFilesRequest request, String lang, OperationResponse operationResponse) {
        boolean result = true;
        request.setUploadWrapperEntities(new ArrayList<>());
        if (request.getFileUploads() != null && !request.getFileUploads().isEmpty()) {
            try {
                for (int i = 0; i < request.getFileUploads().size(); i++) {
                    if (request.getFileUploads().get(i) != null) {
                        UploadWrapper w = request.getFileUploads().get(i);
                        cardValidator.writeWrapperFile(w);
                        if (w.getSize() == 0 || w.getSize() > 1024 * 1024 * 3 || !w.getContentType().matches(Regex.UPLOAD_FILE)) {
                            result = false;
                            String msg = localMessageService.get("message.errors.invalid.file_upload", new Locale(lang));
                            operationResponse.setMessage(String.format(msg, i));
                            break;
                        }
                        UploadWrapperEntity wrapperEntity = new UploadWrapperEntity(
                                w.getName(), w.getContentType(), w.getSize(), w.getLocation()
                        );
                        request.getUploadWrapperEntities().add(wrapperEntity);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                result = false;
                String msg = localMessageService.get("message.errors.global_error", new Locale(lang));
                operationResponse.setMessage(msg);
            }
        }
        if (!result) {
            deleteWrapperFiles(request.getFileUploads());
            request.getUploadWrapperEntities().clear();
        }
        return result;
    }

    private void deleteWrapperFiles(List<UploadWrapper> wrappers) {
        for (int i = 0; i < wrappers.size(); i++) {
            if (wrappers.get(i) != null) {
                String wrapperLocation = wrappers.get(i).getLocation();
                if (wrapperLocation != null && !wrapperLocation.trim().isEmpty()) {
                    try {
                        Files.delete(Paths.get(wrapperLocation));
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    public boolean isRequestValid(CreateNewCardOrderStep3Request request, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            String msg = localMessageService.get("message.errors.invalid.request", new Locale(lang));
            operationResponse.setMessage(msg);
        } else if (
                !isAppIdValid(request.getAppId(), lang, operationResponse) ||
                        !isNameValid(request.getName(), lang, operationResponse) ||
                        !isSurnameValid(request.getSurname(), lang, operationResponse) ||
                        !isMobileNoValid(request.getMobileNo(), lang, operationResponse) ||
                        !isEmailValid(request.getEmail(), lang, operationResponse) ||
//                        !isPinValid(request.getPin(), lang, operationResponse) ||
                        !isBirthDateValid(request.getBirthDate(), lang, operationResponse) ||
                        !isAcceptedTermsValid(request.getAcceptedTerms(), lang, operationResponse) ||
                        !isAcceptedGsaValid(request.getAcceptedGsa(), lang, operationResponse) ||
                        !isAcceptedFatcaValid(request.getAcceptedFatca(), lang, operationResponse) ||
                        !isCrsAnswersValid(request, lang, operationResponse) ||
                        !isIpAddressValid(request.getIpAddress(), lang, operationResponse)
        ) {
            isValid = false;
        } else {
            if (!cardValidator.isStringNullOrEmpty(request.getTkNo())) {
                verifyTkNo(request);
            }
        }
        return isValid;
    }

    public boolean isEmailValid(String email, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.email", new Locale(lang));
        return cardValidator.validateStringEmail(email, operationResponse, msg, msg, msg);
    }

    private boolean isPinValid(String pin, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.pin", new Locale(lang));
        return cardValidator.validateStringAlphaNum(pin, operationResponse, msg, msg, msg);
    }

    private boolean isBirthDateValid(String birthDate, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.birth_date", new Locale(lang));
        return cardValidator.validateStringDate(birthDate, operationResponse, msg, msg, msg);
    }

    private boolean isAcceptedTermsValid(Integer acceptedTerms, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.agreement_terms", new Locale(lang));
        return
                !cardValidator.isNumberNull(acceptedTerms, operationResponse, msg) &&
                        cardValidator.isAccepted(acceptedTerms, operationResponse, msg)
                ;
    }

    private boolean isAcceptedGsaValid(Integer acceptedGsa, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.agreement_gsa", new Locale(lang));
        return
                !cardValidator.isNumberNull(acceptedGsa, operationResponse, msg) &&
                        cardValidator.isAccepted(acceptedGsa, operationResponse, msg)
                ;
    }

    private boolean isAcceptedFatcaValid(Integer acceptedFatca, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.agreement_fatca", new Locale(lang));
        return
                !cardValidator.isNumberNull(acceptedFatca, operationResponse, msg) &&
                        cardValidator.isAccepted(acceptedFatca, operationResponse, msg)
                ;
    }

    private boolean isCrsAnswersValid(CreateNewCardOrderStep3Request request, String lang, OperationResponse operationResponse) {
        return areAllCrsAnswersValidated(request, lang, operationResponse);
    }

    private boolean isIpAddressValid(String ipAddress, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.ip_address", new Locale(lang));
        return !cardValidator.isStringNullOrEmpty(ipAddress, operationResponse, msg, msg);
    }

    private void verifyTkNo(CreateNewCardOrderStep3Request request) {
        CheckTkRequest checkTkRequest = new CheckTkRequest(new MemberDetails(request.getTkNo()));
        CheckTkRestResponse checkTkRestResponse = thyServiceProxy.getMemberDetails(checkTkRequest);
        List<MemberData> memberDataKVPair = null;
        if (checkTkRestResponse != null && checkTkRestResponse.getCheckTkReturn() != null) {
            memberDataKVPair = checkTkRestResponse.getCheckTkReturn().getMemberDataKVPair();
        }
        if (memberDataKVPair != null && !memberDataKVPair.isEmpty()) {
            String passportName = "";
            String passportSurname = "";
            for (MemberData memberData: memberDataKVPair) {
                switch (memberData.getKey()) {
                    case "D_OUT_TOTAL_MILES":
                    case "D_OUT_FFID": break;
                    case "D_OUT_NAME": passportName = memberData.getValue(); break;
                    case "D_OUT_SURNAME": passportSurname = memberData.getValue(); break;
                    default: LOGGER.info("Key {} is not mapped in object MemberData", memberData.getKey());
                }
            }
            request.setPassportName(passportName);
            request.setPassportSurname(passportSurname);
        }
    }

    private boolean areAllCrsAnswersValidated(CreateNewCardOrderStep3Request request, String lang, OperationResponse operationResponse) {
        List<CRSQuestionEntity> crsQuestionEntities = cardStepsDao.getCrsQuestionsByLang(lang);
        if (crsQuestionEntities == null || crsQuestionEntities.isEmpty()) {
            LOGGER.info("Error getting CRS question list. crsQuestionEntities is NULL");
            String msg = localMessageService.get("message.errors.global_error", new Locale(lang));
            operationResponse.setMessage(msg);
            return false;
        }
        boolean result = true;
        request.setCrsAnswerEntities(new ArrayList<>());
        if (request.getCrsAnswers() == null || request.getCrsAnswers().isEmpty()) {
            for (int i = 0; i < crsQuestionEntities.size(); i++) {
                CRSQuestionEntity crsQuestionEntity = crsQuestionEntities.get(i);
                Integer questionId = crsQuestionEntity != null ? crsQuestionEntity.getId() : null;
                CRSAnswerEntity crsAnswerEntity = null;
                if (questionId <= 33) {
                    crsAnswerEntity = new CRSAnswerEntity(questionId, 2, null);
                } else if (questionId > 33 && questionId < 40) {
                    if (questionId < 36) {
                        crsAnswerEntity = new CRSAnswerEntity(questionId, 2, "10,000-dən 150,000 AZN-ə qədər");
                    } else if (questionId < 38) {
                        crsAnswerEntity = new CRSAnswerEntity(questionId, 5, "10,000 to 150,000 AZN");
                    } else {
                        crsAnswerEntity = new CRSAnswerEntity(questionId, 8, "от 10,000 до 150,000 АЗН");
                    }
                } else if (questionId >= 40) {
                    if (questionId == 40) {
                        crsAnswerEntity = new CRSAnswerEntity(questionId, 1, "Maaş (iş yeriniz varsa)");
                    } else if (questionId == 41) {
                        crsAnswerEntity = new CRSAnswerEntity(questionId, 10, "Salary (if employed)");
                    } else if (questionId == 42) {
                        crsAnswerEntity = new CRSAnswerEntity(questionId, 19, "Заработная плата (если есть место работы)");
                    }
                }
                request.getCrsAnswerEntities().add(crsAnswerEntity);
            }
        } else {
            if (crsQuestionEntities.size() != request.getCrsAnswers().size()) {
                result = false;
                String msg = localMessageService.get("message.errors.invalid.crs_answers", new Locale(lang));
                operationResponse.setMessage(msg);
            } else {
                List<NetGrossIncomeEntity> netGrossIncomeEntities = cardStepsDao.getNetGrossIncomesByLang(lang);
                List<SourceOfIncomeEntity> sourceOfIncomeEntities = cardStepsDao.getSourcesOfIncomeByLang(lang);
                for (int i = 0; i < request.getCrsAnswers().size(); i++) {
                    CRSAnswer crsAnswer = request.getCrsAnswers().get(i);
                    CRSQuestionEntity crsQuestionEntity = crsQuestionEntities.get(i);
                    String addQuestion = crsQuestionEntity != null ? crsQuestionEntity.getAddquestion() : null;
                    if (!isCrsAnswerValid(crsAnswer, addQuestion, i + 1, netGrossIncomeEntities, sourceOfIncomeEntities, lang, operationResponse)) {
                        result = false;
                        break;
                    }
                    CRSAnswerEntity crsAnswerEntity = new CRSAnswerEntity(crsAnswer.getQuestionId(), crsAnswer.getAnswer(), crsAnswer.getDescription());
                    request.getCrsAnswerEntities().add(crsAnswerEntity);
                }
                if (!result) {
                    request.getCrsAnswerEntities().clear();
                }
            }
        }
        return result;
    }

    private boolean isCrsAnswerValid(
            CRSAnswer crsAnswer,
            String addQuest,
            int index,
            List<NetGrossIncomeEntity> netGrossIncomeEntities,
            List<SourceOfIncomeEntity> sourceOfIncomeEntities,
            String lang,
            OperationResponse operationResponse
    ) {
        boolean result = true;
        if (crsAnswer == null) {
            result = false;
            String msg = localMessageService.get("message.errors.invalid.crs_answer", new Locale(lang));
            operationResponse.setMessage(String.format(msg, index));
        } else {
            Integer questionId = crsAnswer.getQuestionId();
            Integer answer = crsAnswer.getAnswer();
            String desc = crsAnswer.getDescription();
            if (answer == null || answer == 0) {
                result = false;
                String msg = localMessageService.get("message.errors.invalid.crs_answer", new Locale(lang));
                operationResponse.setMessage(String.format(msg, index));
            } else if (questionId <= 33 && answer == 1 && addQuest != null && !addQuest.trim().isEmpty()) {
                if (desc == null || desc.trim().isEmpty()) {
                    result = false;
                    String msg = localMessageService.get("message.errors.invalid.crs_answer_desc_required", new Locale(lang));
                    operationResponse.setMessage(String.format(msg, index));
                }
            } else if (questionId > 33 && questionId < 40) {
                Optional<NetGrossIncomeEntity> opt = netGrossIncomeEntities.stream().filter(e -> e.getId() == answer).findAny();
                if (!opt.isPresent()) {
                    result = false;
                    String msg = localMessageService.get("message.errors.invalid.crs_answer", new Locale(lang));
                    operationResponse.setMessage(String.format(msg, index));
                }
            } else if (questionId >= 40) {
                Optional<SourceOfIncomeEntity> opt = sourceOfIncomeEntities.stream().filter(e -> e.getId() == answer).findAny();
                if (!opt.isPresent()) {
                    result = false;
                    String msg = localMessageService.get("message.errors.invalid.crs_answer", new Locale(lang));
                    operationResponse.setMessage(String.format(msg, index));
                }
            }
        }
        return result;
    }

    public boolean isRequestValid(GetPromoCodeRequest request, String lang, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            String msg = localMessageService.get("message.errors.invalid.request", new Locale(lang));
            operationResponse.setMessage(msg);
        } else if (
                !isPromoCodeValid(request.getPromoCode(), lang, operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    public boolean isPromoCodeValid(String promoCode, String lang, OperationResponse operationResponse) {
        String msg = localMessageService.get("message.errors.invalid.promo_code", new Locale(lang));
        return !cardValidator.isStringNullOrEmpty(promoCode, operationResponse, msg, msg);
    }

}
