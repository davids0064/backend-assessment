package io.paymeter.assessment.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "alquiler")
@Cacheable(value = false)
@Getter
@Setter
public class AlquilerEntity {

    @Id
    @Column(name = "id_alquiler")
    @SequenceGenerator(name = "alquiler_seq", sequenceName = "alquiler_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alquiler_seq")
    private Integer idAlquiler;
    @JoinColumn(name = "cod_estacionamiento", referencedColumnName = "cod_estacionamiento")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private EstacionamientoEntity estacionamiento;
    @Column(name =  "fecha_entrada_alquiler")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaEntradaAlquiler;
    @Column(name =  "fecha_salida_alquiler")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaSalidaAlquiler;
    @Column(name = "tiempo_alquiler")
    private Long tiempoAlquiler;
    @Column(name = "valor_total_alquiler")
    private Double valorTotalAlquiler;

}
