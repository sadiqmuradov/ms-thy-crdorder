package az.pashabank.apl.ms.thy.task;

import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.logger.UFCLogger;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlexScheduler {

    private static final UFCLogger LOGGER = UFCLogger.getLogger(FlexScheduler.class);

    @Autowired
    private MainDao mainDao;
    
//    @Scheduled(fixedDelayString = "${schedule.fixedDelay.in.milliseconds.flex}") // must be 120000
    public void makeFlexPayments() {
        try {
            List<Payment> unpaidFlexPayments = mainDao.getUnpaidFlexPayments();

            LOGGER.info("-----------------------------------------");
            LOGGER.info("Selected unpaid payments in FLEX");

            for(Payment payment: unpaidFlexPayments) {
                OperationResponse<String> operationResponse = mainDao.makePaymentToFlex(payment.getId());
                if (operationResponse.getCode() == ResultCode.OK) {
                    LOGGER.info("After makePaymentToFlex. [SUCCESS] Flex payment is successful.");
                } else {
                    LOGGER.info("After makePaymentToFlex. [ERROR] Flex payment is failed.");
                }
            }

            LOGGER.info("-----------------------------------------");
        }
        catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
