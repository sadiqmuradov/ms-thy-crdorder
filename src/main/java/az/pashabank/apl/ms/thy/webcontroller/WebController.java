package az.pashabank.apl.ms.thy.webcontroller;

import az.pashabank.apl.ms.thy.controller.CardController;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.entity.Country;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.ThyCouponCodes;
import az.pashabank.apl.ms.thy.model.thy.ThyUserInfo;
import az.pashabank.apl.ms.thy.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private static final MainLogger LOGGER = MainLogger.getLogger(CardController.class);

    @Autowired
    private CardService cardService;

    @Autowired
    private MainDao mainDao;

    @RequestMapping(value = {"/tk"}, method = RequestMethod.GET)
    public String index(ModelMap model, HttpSession httpSession) {
        if (httpSession.getAttribute("loggedIn") == null) {
            return "login";
        } else {
            List<Country> countryList = cardService.getCountryList("AZ");
            model.addAttribute("countryList", countryList);
            return "index";
        }
    }

    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public String users(ModelMap model, HttpSession httpSession, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String q) {
        if (httpSession.getAttribute("loggedIn") == null) {
            return "login";
        } else {
            if (page < 1) {
                page = 1;
            }
            int maxPageCount = 0;
            List<ThyUserInfo> userList = new ArrayList<>();
            if (q.equals("")) {
                maxPageCount = mainDao.getThyUsersListCount();
                userList = cardService.getThyUsersList(page);
            } else {
                maxPageCount = 20;
                userList = mainDao.getThyUsersListByKeyword(q);
            }
            model.addAttribute("q", q);
            model.addAttribute("userList", userList);
            model.addAttribute("currentPage", page);
            model.addAttribute("maxPageCount", maxPageCount);
            return "users";
        }
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(ModelMap model, HttpSession httpSession) {
        if (httpSession.getAttribute("loggedIn") != null) {
            List<Country> countryList = cardService.getCountryList("AZ");
            model.addAttribute("countryList", countryList);
            return "index";
        } else {
            System.out.println(httpSession.getAttribute("loginStatus"));
            model.addAttribute("loginStatus", httpSession.getAttribute("loginStatus"));
            return "login";
        }

    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(ModelMap model, HttpSession httpSession) {
        httpSession.invalidate();
        return "login";
    }

    @RequestMapping(value = {"/coupons"}, method = RequestMethod.GET)
    public String coupons(ModelMap model, HttpSession httpSession,@RequestParam(defaultValue = "") String c) {
        if (httpSession.getAttribute("loggedIn") == null) {
            return "login";
        } else {
            List<ThyCouponCodes> couponList;
            couponList = mainDao.getThyCouponCodes(c);
            model.addAttribute("c", c);
            model.addAttribute("coupon", (!couponList.isEmpty()) ? couponList.get(0) : null);
            return "coupons";
        }
    }

    @RequestMapping(value = {"/disable-coupon"},method = RequestMethod.POST)
    public String disableCoupon(ModelMap model,HttpSession httpSession,@RequestParam(defaultValue = "") String id, @RequestParam(defaultValue = "") String c){
        if(httpSession.getAttribute("loggedIn")==null){
            return "login";
        }
        else {
            String username = "";
            if (httpSession.getAttribute("username")!=null){
                username = (String) httpSession.getAttribute("username");
            }
            mainDao.updateCouponCodes(id, username);
            List<ThyCouponCodes> couponList = mainDao.getThyCouponCodes(c);

            model.addAttribute("c", c);
            model.addAttribute("coupon", (!couponList.isEmpty()) ? couponList.get(0) : null);

            return "coupons";
        }
    }

}