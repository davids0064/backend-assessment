package io.paymeter.assessment.jpa.repository;

import io.paymeter.assessment.jpa.entity.ParqueaderoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParqueaderoRepository extends JpaRepository<ParqueaderoEntity, Integer> {
}
