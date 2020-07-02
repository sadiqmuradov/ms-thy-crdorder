package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.ThyApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThyApplicationRepo extends JpaRepository<ThyApplicationEntity, Integer> {

    List<ThyApplicationEntity> findAllByPaymentCompletedTrueAndMailSentFalseOrderByCreateDate();
}
