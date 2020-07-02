package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.BranchEntity;
import az.pashabank.apl.ms.thy.entity.BranchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepo extends JpaRepository<BranchEntity, BranchId> {

    List<BranchEntity> findAllByActiveTrueAndLangIgnoreCaseOrderByOrderby(String lang);
    BranchEntity findBranchEntityByCodeAndActiveTrueAndLangIgnoreCase(String code, String lang);
}
