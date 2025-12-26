package io.paymeter.assessment.jpa.repository;

import io.paymeter.assessment.jpa.entity.ReglasDescuentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglasDescuentoRepository extends JpaRepository<ReglasDescuentoEntity, Integer> {
}
