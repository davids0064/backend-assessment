package io.paymeter.assessment.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parqueadero")
@Cacheable(value = false)
@Getter
@Setter
public class ParqueaderoEntity {

    @Id
    @Column(name = "id_parqueadero")
    private Integer idParqueadero;

    @Column(name = "nombre_parqueadero")
    private String nombreParqueadero;
    @Column(name = "ciudad_parqueadero")
    private String ciudadParqueadero;

}
