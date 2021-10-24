package cz.xlisto.entity.repository;

import cz.xlisto.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PhoneRepository extends JpaRepository<PhoneEntity,Long> {

    @Query(value = "SELECT * from phone WHERE client_id=:clientId",nativeQuery = true)
    List<PhoneEntity> findAllByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM phone WHERE client_id=:clientId",nativeQuery = true)
    void deleteByClientId(@Param("clientId") Long clientId);

    /*@Query(value = "SELECT * from phone WHERE client_id=11",nativeQuery = true)
    List<PhoneEntity> findAllByClientId(Long clinetId);*/
}
