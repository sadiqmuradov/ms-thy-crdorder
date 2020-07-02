package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.CouponCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponCodeRepo extends JpaRepository<CouponCodeEntity, Integer> {

    CouponCodeEntity findCouponCodeEntityById(int id);
}
