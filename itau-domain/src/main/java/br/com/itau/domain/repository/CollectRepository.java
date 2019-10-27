package br.com.itau.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.itau.domain.entity.CollectEntity;

public interface CollectRepository extends JpaRepository<CollectEntity, Long> {

}
