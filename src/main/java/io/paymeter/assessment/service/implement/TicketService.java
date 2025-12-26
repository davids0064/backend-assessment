package io.paymeter.assessment.service.implement;

import io.paymeter.assessment.dto.RequestDTO;
import io.paymeter.assessment.dto.ResponseDTO;
import io.paymeter.assessment.jpa.entity.AlquilerEntity;
import io.paymeter.assessment.jpa.entity.EstacionamientoEntity;
import io.paymeter.assessment.jpa.repository.*;
import io.paymeter.assessment.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class TicketService implements ITicketService {

    @Autowired
    private AlquilerRepository alquilerRepository;
    @Autowired
    private EstacionamientoRepository estacionamientoRepository;


    public ResponseEntity<ResponseDTO> calcularTicket(RequestDTO requestDTO) {
        log.info("Calculando ticket para parkingId: {}, desde: {}, hasta: {}", requestDTO.parkingId(), requestDTO.from(), requestDTO.to());

        return estacionamientoRepository.findById(requestDTO.parkingId())
                .filter(e -> !"RESERVADO".equals(e.getEstadoEstacionamiento()))
                .map(e -> {
                    AlquilerEntity alquiler = registrarAlquiler(requestDTO.from(), requestDTO.to(), e);
                    return ResponseEntity.ok(mapearAResponse(alquiler));
                })
                .orElseGet(() -> {
                    log.error("Estacionamiento no encontrado o reservado: {}", requestDTO.parkingId());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    private AlquilerEntity registrarAlquiler(String from, String to, EstacionamientoEntity estacionamiento) {
        AlquilerEntity alquilerEntity = new AlquilerEntity();
        LocalDateTime entrada = LocalDateTime.parse(from);
        LocalDateTime salida;
        if (to == null || to.trim().isEmpty() || "null".equalsIgnoreCase(to)) {
            salida = LocalDateTime.now();
        } else {
            salida = LocalDateTime.parse(to);
        }

        if (salida.isBefore(entrada)) {
            throw new IllegalArgumentException("La fecha de salida no puede ser anterior a la de entrada");
        }

        alquilerEntity.setFechaEntradaAlquiler(entrada);
        alquilerEntity.setFechaSalidaAlquiler(salida);

        long totalMinutes = Duration.between(entrada, salida).toMinutes();
        alquilerEntity.setTiempoAlquiler(totalMinutes);

        alquilerEntity.setValorTotalAlquiler(calcularValorAlquiler(totalMinutes, estacionamiento));
        alquilerEntity.setEstacionamiento(estacionamiento);

        return alquilerRepository.save(alquilerEntity);
    }

    private Double calcularValorAlquiler(long minutosTotales, EstacionamientoEntity estacionamiento) {
        if (minutosTotales < 1) return 0.0;
        double precioHora = estacionamiento.getPrecio().getDetallePrecio();
        double maxPrecio = estacionamiento.getPrecio().getReglaDescuento().getValorMaxRegla();
        int horasCiclo = estacionamiento.getPrecio().getReglaDescuento().getCicloHoraRegla();
        boolean tienePrimeraHoraGratis = estacionamiento.getPrecio().getReglaDescuento().getCortesiaRegla();
        if (tienePrimeraHoraGratis) {
            minutosTotales = Math.max(0, minutosTotales - 60);
        }
        if (minutosTotales == 0) return 0.0;
        long minutosPorCiclo = horasCiclo * 60L;
        int numeroDeCiclos = (int) Math.ceil((double) minutosTotales / minutosPorCiclo);
        double totalAcumulado = 0.0;

        for (int i = 0; i < numeroDeCiclos; i++) {
            long minutosEnEsteCiclo = Math.min(minutosPorCiclo, minutosTotales - (i * minutosPorCiclo));
            long horasACobrarEnCiclo = (long) Math.ceil(minutosEnEsteCiclo / 60.0);
            double costoCiclo = horasACobrarEnCiclo * precioHora;
            totalAcumulado += Math.min(costoCiclo, maxPrecio);
        }

        return totalAcumulado;
    }

    private ResponseDTO mapearAResponse(AlquilerEntity alquiler) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return new ResponseDTO(
                alquiler.getEstacionamiento().getCodEstacionamiento(),
                alquiler.getFechaEntradaAlquiler().format(formatter),
                alquiler.getFechaSalidaAlquiler().format(formatter),
                alquiler.getTiempoAlquiler().intValue(),
                formaterPrecio(alquiler.getValorTotalAlquiler())
        );
    }

    private String formaterPrecio(Double valor) {
        int centimos = (int) Math.round(valor * 100);
        return centimos + "EUR";
    }
}
