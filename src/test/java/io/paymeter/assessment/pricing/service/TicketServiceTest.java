package io.paymeter.assessment.pricing.service;

import io.paymeter.assessment.dto.RequestDTO;
import io.paymeter.assessment.dto.ResponseDTO;
import io.paymeter.assessment.jpa.entity.*;
import io.paymeter.assessment.jpa.repository.AlquilerRepository;
import io.paymeter.assessment.jpa.repository.EstacionamientoRepository;
import io.paymeter.assessment.service.implement.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private AlquilerRepository alquilerRepository;

    @Mock
    private EstacionamientoRepository estacionamientoRepository;

    @InjectMocks
    private TicketService ticketService;

    private EstacionamientoEntity cliente1;
    private EstacionamientoEntity cliente2;

    @BeforeEach
    void setUp() {
        cliente1 = crearEstacionamiento("P000123", 2.0, 15.0, 24, false);
        cliente2 = crearEstacionamiento("P000456", 3.0, 20.0, 12, true);
    }

    @Test
    @DisplayName("Cálculo Cliente 1: 3 horas deben costar 600EUR (6€)")
    void calcularTicket_Cliente1_CasoBasico() {
        RequestDTO request = new RequestDTO("P000123", "2024-01-01T10:00:00", "2024-01-01T13:00:00");
        when(estacionamientoRepository.findById("P000123")).thenReturn(Optional.of(cliente1));
        when(alquilerRepository.save(any(AlquilerEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        ResponseEntity<ResponseDTO> response = ticketService.calcularTicket(request);

        assertEquals("600EUR", response.getBody().price());
        assertEquals(180, response.getBody().duration());
    }

    @Test
    @DisplayName("Cálculo Cliente 1: 10 horas (20€) deben topar en 1500EUR (15€) por regla de 24h")
    void calcularTicket_Cliente1_TopeMaximo() {
        RequestDTO request = new RequestDTO("P000123", "2024-01-01T10:00:00", "2024-01-01T20:00:00");
        when(estacionamientoRepository.findById("P000123")).thenReturn(Optional.of(cliente1));
        when(alquilerRepository.save(any(AlquilerEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        ResponseEntity<ResponseDTO> response = ticketService.calcularTicket(request);

        assertEquals("1500EUR", response.getBody().price());
    }

    @Test
    @DisplayName("Cálculo Cliente 2: 2 horas con 1h de cortesía deben costar 300EUR (3€)")
    void calcularTicket_Cliente2_ConCortesia() {
        RequestDTO request = new RequestDTO("P000456", "2024-01-01T10:00:00", "2024-01-01T12:00:00");
        when(estacionamientoRepository.findById("P000456")).thenReturn(Optional.of(cliente2));
        when(alquilerRepository.save(any(AlquilerEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        ResponseEntity<ResponseDTO> response = ticketService.calcularTicket(request);
        assertEquals("300EUR", response.getBody().price());
    }

    @Test
    @DisplayName("Cálculo Cliente 2: 13 horas deben aplicar cortesía y topar en dos ciclos")
    void calcularTicket_Cliente2_MultiplesCiclos() {
        RequestDTO request = new RequestDTO("P000456", "2024-01-01T10:00:00", "2024-01-01T23:00:00");
        when(estacionamientoRepository.findById("P000456")).thenReturn(Optional.of(cliente2));
        when(alquilerRepository.save(any(AlquilerEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        ResponseEntity<ResponseDTO> response = ticketService.calcularTicket(request);
        assertEquals("2000EUR", response.getBody().price());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la fecha de salida es anterior a la entrada")
    void calcularTicket_FechaInvalida() {
        RequestDTO request = new RequestDTO("P000123", "2024-01-01T12:00:00", "2024-01-01T10:00:00");
        when(estacionamientoRepository.findById("P000123")).thenReturn(Optional.of(cliente1));

        assertThrows(IllegalArgumentException.class, () -> ticketService.calcularTicket(request));
    }
    private EstacionamientoEntity crearEstacionamiento(String cod, Double precio, Double max, Integer ciclo, boolean cortesia) {
        ReglasDescuentoEntity regla = new ReglasDescuentoEntity();
        regla.setValorMaxRegla(max);
        regla.setCicloHoraRegla(ciclo);
        regla.setCortesiaRegla(cortesia);

        PrecioEntity p = new PrecioEntity();
        p.setDetallePrecio(precio);
        p.setReglaDescuento(regla);

        EstacionamientoEntity e = new EstacionamientoEntity();
        e.setCodEstacionamiento(cod);
        e.setEstadoEstacionamiento("DISPONIBLE");
        e.setPrecio(p);
        return e;
    }

}
