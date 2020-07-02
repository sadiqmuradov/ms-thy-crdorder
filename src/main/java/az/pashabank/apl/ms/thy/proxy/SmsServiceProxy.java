package az.pashabank.apl.ms.thy.proxy;

import az.pashabank.apl.ms.thy.constants.URL;
import az.pashabank.apl.ms.thy.model.sms.BaseResponse;
import az.pashabank.apl.ms.thy.model.sms.Sms;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ms-sms", url = "${ms-sms.rest.endpoint}")
public interface SmsServiceProxy {
    @RequestMapping(method = RequestMethod.POST, value = URL.MS_SMS_SEND_SMS, consumes = "application/json", produces = "application/json")
    BaseResponse sendSMS(@RequestBody Sms sms);
}
