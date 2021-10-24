package cz.xlisto.entity.repository;

import cz.xlisto.entity.InsuranceCarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCarRepository extends JpaRepository<InsuranceCarEntity,Long> {
}
