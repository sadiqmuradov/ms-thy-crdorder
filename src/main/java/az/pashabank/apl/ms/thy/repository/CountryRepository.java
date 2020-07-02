package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findAllByLang(@Param("lang") String lang);
    List<Country> findAllByLangOrderByNameAsc(@Param("lang") String lang);
    Long deleteByLang(@Param("lang") String lang);
}
