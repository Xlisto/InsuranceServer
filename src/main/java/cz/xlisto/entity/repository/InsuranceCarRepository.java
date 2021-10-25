package cz.xlisto.entity.repository;

import cz.xlisto.entity.InsuranceCarEntity;
import cz.xlisto.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceCarRepository extends JpaRepository<InsuranceCarEntity,Long> {

    @Query(value = "SELECT * FROM insurance_car WHERE client_id=:clientId",nativeQuery = true)
    List<InsuranceCarEntity> findAllByClientId(@Param("clientId") Long clientId);
}
