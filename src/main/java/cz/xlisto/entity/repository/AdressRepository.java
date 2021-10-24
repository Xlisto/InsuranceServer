package cz.xlisto.entity.repository;

import cz.xlisto.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<AddressEntity, Long> {
}
