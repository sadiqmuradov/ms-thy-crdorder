package az.pashabank.apl.ms.thy.webcontroller;

import az.pashabank.apl.ms.thy.constants.URL;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.Login;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyRequest;
import az.pashabank.apl.ms.thy.model.thy.ThyUserInfo;
import az.pashabank.apl.ms.thy.service.CardService;
import az.pashabank.apl.ms.thy.utils.Utils;
import io.swagger.annotations.Api;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@CrossOrigin
@Validated
@Api("PashaBank THY Miles & Smiles UI Api")
public class UIRestController {

    private static final MainLogger LOGGER = MainLogger.getLogger(UIRestController.class);

    @Autowired
    private CardService cardService;

    @Autowired
    private MainDao mainDao;


    @GetMapping(URL.GET_CHECK_TK_UI)
    public String checkTKUI(@PathVariable final String tk) {
        OperationResponse response = cardService.checkTK(tk);
        LOGGER.info("checkTK. TK: {}, response: {}", tk, response);
        if (response.getCode().name().equals("OK")) {
            RegisterCustomerInThyRequest registerCustomerInThyRequest = new RegisterCustomerInThyRequest();
            registerCustomerInThyRequest.setName(new JSONObject(response).getJSONObject("data").get("name").toString());
            registerCustomerInThyRequest.setSurname(new JSONObject(response).getJSONObject("data").get("surname").toString());
            String addUserToDb = cardService.addTkUserInfo(registerCustomerInThyRequest, tk);
            if (addUserToDb.equals("OK")) {
                LOGGER.info("TK added to DB during checking TK {}", tk);
            } else {
                LOGGER.error("TK coulnt be added to DB during checking TK {}", tk);
            }
        }
        JSONObject jo = new JSONObject();
        jo.put("code", response.getCode());
        jo.put("message", response.getMessage());
        jo.put("data", response.getData());
        return jo.toString();
    }


    @RequestMapping(value = URL.POST_REGISTER_CUSTOMER_IN_THY_UI, method = RequestMethod.POST)
    public String addTHYmember(@Valid @ModelAttribute("request") RegisterCustomerInThyRequest registerCustomerInThyRequest) {
        LOGGER.info(registerCustomerInThyRequest.toString());
        String randomPass = Utils.getRandomNumberString();
        LOGGER.info(randomPass);
        registerCustomerInThyRequest.setPassword(randomPass);
        registerCustomerInThyRequest.setRepeatPassword(randomPass);
        if (registerCustomerInThyRequest.getBirthDate() != null) {
            String birthDate = registerCustomerInThyRequest.getBirthDate();
            registerCustomerInThyRequest.setBirthDate(birthDate);
        }
        String addUserToDb = cardService.addTkUserInfo(registerCustomerInThyRequest, "this_tk_doesnt_exist");
        if (addUserToDb.equals("OK")) {
            OperationResponse response = cardService.registerCustomerInThyForUi(registerCustomerInThyRequest);
            LOGGER.info("registerCustomerInThy. request: {}, response: {}", registerCustomerInThyRequest, response);
            String tkNo = "";
            String email = "";
            if (response.getCode().name().equals("OK")) {
                tkNo = new JSONObject(response).getJSONObject("data").getJSONObject("memberProfileData").get("memberId").toString();
                email = new JSONObject(response).getJSONObject("data").getJSONObject("memberProfileData").get("emailAddress").toString();
                cardService.updateTHYuserInfo(registerCustomerInThyRequest.getEmail(), tkNo, 1);
            } else {
                cardService.updateTHYuserInfo(registerCustomerInThyRequest.getEmail(), "", 2);
            }
            JSONObject jo = new JSONObject();
            jo.put("code", response.getCode());
            jo.put("message", response.getMessage());
            jo.put("tk", tkNo);
            if (response.getCode().name().equals("OK")) {
                String sendSmsPasswordText = cardService.getDynamicVal("send_sms_user_password_text");
                if (sendSmsPasswordText != null) {
                    String smsText = sendSmsPasswordText.replace("_tkno_", tkNo).replace("_password_", randomPass);
                    cardService.sendPassword(registerCustomerInThyRequest.getMobileNo(), smsText);
                } else {
                    LOGGER.error("Can't key send_sms_user_password_text value from DB! ");
                }
            }
            return jo.toString();
        } else {
            JSONObject jo = new JSONObject();
            jo.put("code", "ERROR");
            jo.put("message", addUserToDb);
            jo.put("tk", "");
            LOGGER.info("Can't add thy user info row do DB.");
            return jo.toString();
        }
    }

    @RequestMapping(value = URL.POST_LOGIN, method = RequestMethod.POST)
    public RedirectView login(@Valid @ModelAttribute("request") Login login, HttpSession httpSession) {
        httpSession.setAttribute("loginStatus", "");
        if (httpSession.getAttribute("loggedIn") != null) {
            httpSession.setAttribute("loginStatus", "");
            return new RedirectView("tk");
        } else {
            String responseMessage = cardService.login(login);
            LOGGER.info("responseMessage: {}", responseMessage);
            if (responseMessage.equals("OK")) {
                httpSession.setAttribute("loggedIn", true);
                httpSession.setAttribute("username", login.getUsername());
                return new RedirectView("tk");
            } else {
                httpSession.setAttribute("loginStatus", "Yanlış istifadəçi adı və ya parol daxil etdiyiniz.");
                return new RedirectView("login");
            }
        }
    }


    @RequestMapping(value = URL.RESEND_SMS, method = RequestMethod.GET)
    public String resendSms(@PathVariable final int userId) {
        String responseCode = cardService.resendSms(userId);
        LOGGER.info("resendSms responseCode {}", responseCode);
        JSONObject jo = new JSONObject();
        jo.put("responseCode", responseCode);
        return jo.toString();
    }

    @RequestMapping(value = URL.GET_USER_INFO, method = RequestMethod.GET)
    public String getUserInfo(@PathVariable final int userId) {
        ThyUserInfo thyUserInfo = mainDao.getThyUserInfoById(userId);
        thyUserInfo.setPassword("");
        LOGGER.info("thyUserInfo {}", thyUserInfo);
        JSONObject jo = new JSONObject();
        jo.put("tknumber", thyUserInfo.getTkNumber());
        jo.put("name", thyUserInfo.getName());
        jo.put("surname", thyUserInfo.getSurName());
        jo.put("birthdate", thyUserInfo.getBirthDate());
        jo.put("address", thyUserInfo.getAddress());
        jo.put("email", thyUserInfo.getEmail());
        jo.put("phonenumber", thyUserInfo.getPhoneNumber());
        jo.put("datecreated", thyUserInfo.getDateCreated().substring(0, 16));
        return jo.toString();
    }

}
