package cz.xlisto.entity.repository;

import cz.xlisto.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    List<ClientEntity> findAll();



    long count();
}
