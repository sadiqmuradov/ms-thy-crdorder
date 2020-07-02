package az.pashabank.apl.ms.thy.repository;

import az.pashabank.apl.ms.thy.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByCountryCode(@Param("countryCode") String countryCode);
    List<City> findAllByCountryCodeOrderByNameAsc(@Param("countryCode") String countryCode);
}
