package io.paymeter.assessment.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estacionamiento")
@Cacheable(value = false)
@Getter
@Setter
public class EstacionamientoEntity {

    @Id
    @Column(name = "cod_estacionamiento")
    private String codEstacionamiento;

    @JoinColumn(name = "id_parqueadero", referencedColumnName = "id_parqueadero")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private ParqueaderoEntity parqueadero;
    @JoinColumn(name = "id_precio", referencedColumnName = "id_precio")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private PrecioEntity precio;
    @Column(name = "estado_estacionamiento")
    private String estadoEstacionamiento;

}
