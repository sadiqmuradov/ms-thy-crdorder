package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.PromoCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepo extends JpaRepository<PromoCodeEntity, Integer> {

    PromoCodeEntity findPromoCodeEntityByPromoCode(String promoCode);
}
