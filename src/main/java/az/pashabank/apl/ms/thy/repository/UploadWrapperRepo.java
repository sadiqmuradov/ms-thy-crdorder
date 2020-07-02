package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.UploadWrapperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadWrapperRepo extends JpaRepository<UploadWrapperEntity, Integer> {

}
