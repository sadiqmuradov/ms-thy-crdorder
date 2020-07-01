package az.pashabank.apl.ms.thy.proxy;

import az.pashabank.apl.ms.thy.constants.URL;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentRequest;
import az.pashabank.apl.ms.thy.model.ecomm.ECommRegisterPaymentResponse;
import az.pashabank.apl.ms.thy.model.ecomm.EcommPaymentStatusRequest;
import az.pashabank.apl.ms.thy.model.ecomm.EcommPaymentStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "ms-ecomm", url = "${ecomm.rest.endpoint}")
public interface ECommServiceProxy {

    @RequestMapping(method = RequestMethod.POST, value = URL.ECOMM_REGISTER_PAYMENT, consumes = "application/json", produces = "application/json")
    ECommRegisterPaymentResponse registerPayment(@RequestBody ECommRegisterPaymentRequest eCommRegisterPaymentRequest);

    @RequestMapping(method = RequestMethod.POST, value = URL.ECOMM_GET_PAYMENT_STATUS, consumes = "application/json", produces = "application/json")
    EcommPaymentStatusResponse getPaymentStatus(@RequestBody EcommPaymentStatusRequest ecommPaymentStatusRequest);
}

