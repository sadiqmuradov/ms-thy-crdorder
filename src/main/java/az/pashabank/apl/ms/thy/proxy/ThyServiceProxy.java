package az.pashabank.apl.ms.thy.proxy;

import az.pashabank.apl.ms.thy.constants.URL;
import az.pashabank.apl.ms.thy.model.thy.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ms-thy", url = "${thy.rest.endpoint}")
public interface ThyServiceProxy {

    @RequestMapping(method = RequestMethod.POST, value = URL.THY_POST_GET_MEMBER_DETAILS, consumes = "application/json", produces = "application/json")
    CheckTkRestResponse getMemberDetails(@RequestBody CheckTkRequest request);

    @RequestMapping(method = RequestMethod.POST, value = URL.THY_POST_MEMBER_OPERATIONS, consumes = "application/json", produces = "application/json")
    MemberOperationsResponse createMember(@RequestBody MemberOperationsRequest request);

    @RequestMapping(method = RequestMethod.POST, value = URL.THY_POST_GET_SPECIFIC_COMBOBOX, consumes = "application/json", produces = "application/json")
    SecurityQuestionsRestResponse getSecurityQuestions(@RequestBody SecurityQuestionsRequest request);

}
