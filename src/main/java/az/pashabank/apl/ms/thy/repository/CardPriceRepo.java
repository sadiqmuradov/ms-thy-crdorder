package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.CardPriceEntity;
import az.pashabank.apl.ms.thy.entity.CardPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardPriceRepo extends JpaRepository<CardPriceEntity, CardPriceId> {

    CardPriceEntity findCardPriceEntityByCardProductId(int cardProductId);
}
