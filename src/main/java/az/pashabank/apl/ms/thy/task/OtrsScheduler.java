package az.pashabank.apl.ms.thy.task;

import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.dao.MainDao;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.model.*;
import az.pashabank.apl.ms.thy.service.MailService;
import az.pashabank.apl.ms.thy.validator.CardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class OtrsScheduler {

    private static final MainLogger LOGGER = MainLogger.getLogger(OtrsScheduler.class);

    @Autowired
    private MainDao mainDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private CardValidator validator;

//    @Scheduled(fixedDelayString = "${schedule.fixedDelay.in.milliseconds.otrs}") // must be 180000
    public void sendCardOrderMails() {
        try {
            List<ThyApplication> unsentApplications = mainDao.getUnsentApplications();
            List<String> emails = mainDao.getActiveMails();

            LOGGER.info("-----------------------------------------");
            LOGGER.info("Selected unsent applications and emails");

            for (ThyApplication app : unsentApplications) {
                emailUnsentApplication(app, emails);
            }

            LOGGER.info("-----------------------------------------");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void emailUnsentApplication(ThyApplication app, List<String> emails) {
        try {
            List<Resource> resources = new ArrayList<>();
            addFileUploads(app, resources);
            app.setCardProduct(mainDao.getCardProductById(app.getCardProduct().getId()));
            List<CRSQuestion> crsQuestions = mainDao.getCRSQuestions("en");
            Payment payment = mainDao.getPaymentByAppId(app.getId(), "CARD");

            OperationResponse operationResponse = mailService.sendEmail(app, crsQuestions, payment, emails, resources);

            if (operationResponse.getCode() == ResultCode.OK) {
                mainDao.markApplicationAsSent(app.getId(), "CARD");
                validator.deleteWrapperFiles(app.getFileUploads(), operationResponse);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void addFileUploads(@NotNull ThyApplication app, List<Resource> resources) {
        for (UploadWrapper uw : app.getFileUploads()) {
            resources.add(new FileSystemResource(uw.getLocation()));
        }
    }

}
