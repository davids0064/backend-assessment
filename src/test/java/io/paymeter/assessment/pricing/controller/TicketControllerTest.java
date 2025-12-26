package io.paymeter.assessment.pricing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.paymeter.assessment.controller.TicketController;
import io.paymeter.assessment.dto.RequestDTO;
import io.paymeter.assessment.dto.ResponseDTO;
import io.paymeter.assessment.service.ITicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debe retornar 200 OK y el JSON de respuesta cuando el request es válido")
    void calcularTicket_DebeRetornarRespuestaValida() throws Exception {
        RequestDTO requestDTO = new RequestDTO(
                "P000456",
                "2024-02-27T09:00:00",
                "2024-02-27T11:00:00"
        );

        ResponseDTO responseDTO = new ResponseDTO(
                "P000456",
                "2024-02-27T09:00:00",
                "2024-02-27T11:00:00",
                120,
                "300EUR"
        );
        Mockito.when(ticketService.calcularTicket(any(RequestDTO.class)))
                .thenReturn(ResponseEntity.ok(responseDTO));

        mockMvc.perform(post("/api/ticket/calcular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))) // Convierte objeto a JSON
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.parkingId").value("P000456"))
                .andExpect(jsonPath("$.duration").value(120))
                .andExpect(jsonPath("$.price").value("300EUR"));

        Mockito.verify(ticketService, Mockito.times(1)).calcularTicket(any(RequestDTO.class));
    }

    @Test
    @DisplayName("Debe retornar 404 Not Found si el servicio no encuentra el estacionamiento")
    void calcularTicket_DebeRetornar404CuandoNoExiste() throws Exception {
        RequestDTO requestDTO = new RequestDTO("INVALID_ID", "2024-02-27T09:00:00", null);

        Mockito.when(ticketService.calcularTicket(any(RequestDTO.class)))
                .thenReturn(ResponseEntity.notFound().build());
        mockMvc.perform(post("/api/ticket/calcular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

}
