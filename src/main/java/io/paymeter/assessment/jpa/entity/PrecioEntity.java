package io.paymeter.assessment.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "precio")
@Cacheable(value = false)
@Getter
@Setter
public class PrecioEntity {

    @Id
    @Column(name = "id_precio")
    private Integer idPrecio;

    @Column(name = "detalle_precio")
    private Double detallePrecio;
    @Column(name = "estado_precio")
    private String estadoPrecio;
    @JoinColumn(name = "id_regla", referencedColumnName = "id_regla")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private ReglasDescuentoEntity reglaDescuento;

}
