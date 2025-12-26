package io.paymeter.assessment.jpa.repository;

import io.paymeter.assessment.jpa.entity.AlquilerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepository extends JpaRepository<AlquilerEntity, Integer> {
}
