package io.paymeter.assessment.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "reglas_descuento")
@Cacheable(value = false)
@Getter
@Setter
public class ReglasDescuentoEntity {

    @Id
    @Column(name = "id_regla")
    private Integer idRegla;

    @Column(name = "valor_max_regla")
    private Double valorMaxRegla;
    @Column(name = "ciclo_hora_regla")
    private Integer cicloHoraRegla;
    @Column(name = "cortesia_regla")
    private Boolean cortesiaRegla;
    @Column(name = "estado_regla")
    private String estadoRegla;

}
