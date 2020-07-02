package az.pashabank.apl.ms.thy.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Repositories {

    @Autowired
    private ThyApplicationRepo thyApplicationRepo;

    @Autowired
    private CardProductRepo cardProductRepo;

    @Autowired
    private CardPriceRepo cardPriceRepo;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private PromoCodeRepo promoCodeRepo;

    @Autowired
    private CRSQuestionRepo crsQuestionRepo;

    @Autowired
    private NetGrossIncomeRepo netGrossIncomeRepo;

    @Autowired
    private SourceOfIncomeRepo sourceOfIncomeRepo;

    @Autowired
    private UploadWrapperRepo uploadWrapperRepo;

    @Autowired
    private CouponCodeRepo couponCodeRepo;

}
