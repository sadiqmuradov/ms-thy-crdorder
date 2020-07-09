package az.pashabank.apl.ms.thy.task;

import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlexScheduler {

    private static final MainLogger LOGGER = MainLogger.getLogger(FlexScheduler.class);

    @Autowired
    private MainDao mainDao;
    
//    @Scheduled(fixedDelayString = "${schedule.fixedDelay.in.milliseconds.flex}") // must be 120000
    public void makeFlexPayments() {
        try {
            List<Payment> unpaidFlexPayments = mainDao.getUnpaidFlexPayments("CARD");
            LOGGER.info("-----------------------------------------");
            LOGGER.info("Selected unpaid card order payments in FLEX");
            for(Payment payment: unpaidFlexPayments) {
                OperationResponse<String> operationResponse = mainDao.makePaymentToFlex(payment.getId(), "CARD");
                if (operationResponse.getCode() == ResultCode.OK) {
                    LOGGER.info("After card order makePaymentToFlex. [SUCCESS] Flex payment is successful. trnRefNo: {}", operationResponse.getData());
                } else {
                    LOGGER.info("After card order makePaymentToFlex. [ERROR] Flex payment is failed.");
                }
            }
            LOGGER.info("-----------------------------------------");

            unpaidFlexPayments = mainDao.getUnpaidFlexPayments("COUPON");
            LOGGER.info("-----------------------------------------");
            LOGGER.info("Selected unpaid coupon order payments in FLEX");
            for(Payment payment: unpaidFlexPayments) {
                OperationResponse<String> operationResponse = mainDao.makePaymentToFlex(payment.getId(), "COUPON");
                if (operationResponse.getCode() == ResultCode.OK) {
                    LOGGER.info("After coupon order makePaymentToFlex. [SUCCESS] Flex payment is successful. trnRefNo: {}", operationResponse.getData());
                } else {
                    LOGGER.info("After coupon order makePaymentToFlex. [ERROR] Flex payment is failed.");
                }
            }
            LOGGER.info("-----------------------------------------");
        }
        catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
