package az.pashabank.apl.ms.thy.dao;

import az.pashabank.apl.ms.thy.entity.*;
import az.pashabank.apl.ms.thy.repository.LocalRepositories;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CardStepsDao {

    @Autowired
    private LocalRepositories repositories;

    public ThyApplicationEntity saveApplication(ThyApplicationEntity appEntity) {
        return repositories.getThyApplicationRepo().save(appEntity);
    }

    public ThyApplicationEntity getApplicationById(int appId) {
        ThyApplicationEntity appEntity = repositories.getThyApplicationRepo().getOne(appId);
        Hibernate.initialize(appEntity.getCrsAnswers());
        Hibernate.initialize(appEntity.getUploadWrappers());
        return appEntity;
    }

    public CardProductEntity getCardProductById(int id) {
        return repositories.getCardProductRepo().findCardProductEntityByIdAndActiveTrue(id);
    }

    public CardPriceEntity getCardPriceByCardProductId(int cardProductId) {
        return repositories.getCardPriceRepo().findCardPriceEntityByCardProductId(cardProductId);
    }

    public BranchEntity getBranchByCodeAndLang(String branchCode, String lang) {
        return repositories.getBranchRepo().findBranchEntityByCodeAndActiveTrueAndLangIgnoreCase(branchCode, lang);
    }

    public CouponCodeEntity getCouponCodeById(int id) {
        return repositories.getCouponCodeRepo().findCouponCodeEntityById(id);
    }

    public PromoCodeEntity getPromoCodeByCode(String promoCode) {
        return repositories.getPromoCodeRepo().findPromoCodeEntityByPromoCode(promoCode);
    }

    public List<CRSQuestionEntity> getCrsQuestionsByLang(String lang) {
        return repositories.getCrsQuestionRepo().findAllByLangIgnoreCaseOrderById(lang);
    }

    public List<NetGrossIncomeEntity> getNetGrossIncomesByLang(String lang) {
        return repositories.getNetGrossIncomeRepo().findNetGrossIncomeEntitiesByLangIgnoreCaseOrderById(lang);
    }

    public List<SourceOfIncomeEntity> getSourcesOfIncomeByLang(String lang) {
        return repositories.getSourceOfIncomeRepo().findSourceOfIncomeEntitiesByLangIgnoreCaseOrderById(lang);
    }

    public void deleteUploadWrappers(List<UploadWrapperEntity> wrapperEntities) {
        repositories.getUploadWrapperRepo().deleteAll(wrapperEntities);
    }

}
