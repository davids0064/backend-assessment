package io.paymeter.assessment.pricing.controller;

import io.paymeter.assessment.dto.RequestDTO;
import io.paymeter.assessment.jpa.repository.AlquilerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Usa un archivo application-test.properties con H2 o DB de pruebas
class TicketControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Test
    void calcularTicket_DebePersistirEnBaseDeDatos() {
        // GIVEN: Un request para el parking P000456 (asegúrate que exista en tu script de carga inicial)
        RequestDTO request = new RequestDTO("P000456", "2024-02-27T09:00:00", "2024-02-27T11:00:00");
        ResponseEntity<Object> response = restTemplate.postForEntity("/api/ticket/calcular", request, Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        long count = alquilerRepository.count();
        assertTrue(count > 0, "Debería haber al menos un registro en la tabla alquiler");
    }
}