package cz.xlisto.entity.repository;

import cz.xlisto.entity.ClientEntity;
import cz.xlisto.entity.ClientFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PageableClientRepository extends PagingAndSortingRepository<ClientEntity, Long> {

    Page<ClientEntity> findAll(Pageable pageable);

    @Query(value = "SELECT c FROM clients c WHERE"
            +" (c.firstName = :#{#filter.getFirstName()} OR :#{#filter.getFirstName()} is null) AND"
            +" (c.lastName = :#{#filter.getLastName()} OR :#{#filter.getLastName()} is null) AND"
            +" (c.pin = :#{#filter.getPin()} OR :#{#filter.getPin()} is null)"
    )
    Page<ClientEntity> findFilteredAll(ClientFilter filter, Pageable pageable);
}
