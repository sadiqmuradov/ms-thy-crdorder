package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.CRSQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CRSQuestionRepo extends JpaRepository<CRSQuestionEntity, Integer> {

        List<CRSQuestionEntity> findAllByLangIgnoreCaseOrderById(String lang);
}
