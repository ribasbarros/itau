package br.com.itau.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.itau.domain.entity.AgencyEntity;

public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {

	Optional<AgencyEntity> findByPlaceId(String placeId);

	@Query("FROM AgencyEntity c WHERE LOWER(c.placeId) like %:searchTerm%")
	Page<AgencyEntity> search(@Param("searchTerm") String searchTerm, Pageable pageable);

}
