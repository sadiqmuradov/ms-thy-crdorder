package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.CardProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardProductRepo extends JpaRepository<CardProductEntity, Integer> {

    CardProductEntity findCardProductEntityByIdAndActiveTrue(int id);
}
