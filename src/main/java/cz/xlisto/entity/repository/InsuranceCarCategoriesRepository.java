package cz.xlisto.entity.repository;

import cz.xlisto.dto.InsuranceCarCategoriesDTO;
import cz.xlisto.entity.InsuranceCarCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InsuranceCarCategoriesRepository extends JpaRepository<InsuranceCarCategoriesEntity, Long> {

    @Query(value = "SELECT * FROM insurance.insurance_car_categories"+
            " WHERE engine_power<=:enginePower ORDER BY engine_power DESC LIMIT 1",nativeQuery = true)
    InsuranceCarCategoriesEntity findPriceByEnginePower(@Param("enginePower") Long enginePower);
}
