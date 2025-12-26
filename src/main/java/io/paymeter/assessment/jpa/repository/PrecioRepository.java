package io.paymeter.assessment.jpa.repository;

import io.paymeter.assessment.jpa.entity.PrecioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioRepository extends JpaRepository<PrecioEntity, Integer> {
}
