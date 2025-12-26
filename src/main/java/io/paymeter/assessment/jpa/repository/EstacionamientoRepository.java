package io.paymeter.assessment.jpa.repository;

import io.paymeter.assessment.jpa.entity.EstacionamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionamientoRepository extends JpaRepository<EstacionamientoEntity, String> {
}
