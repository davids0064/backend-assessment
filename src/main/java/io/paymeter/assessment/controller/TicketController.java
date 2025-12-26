package io.paymeter.assessment.controller;

import io.paymeter.assessment.dto.RequestDTO;
import io.paymeter.assessment.dto.ResponseDTO;
import io.paymeter.assessment.service.ITicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ticket")
@Tag(name = "Tickets", description = "Operaciones relacionadas con el cálculo de cobros de un parqueadero")
public class TicketController {

    @Autowired
    private ITicketService iTicketService;

    @Operation(summary = "Calcular y registrar ticket",
            description = "Calcula el costo del estacionamiento basado en el parkingId y el rango de fechas, persistiendo el resultado.")
    @ApiResponse(responseCode = "200", description = "Ticket calculado exitosamente")
    @ApiResponse(responseCode = "404", description = "Estacionamiento no encontrado o reservado")
    @PostMapping("calcular")
    public ResponseEntity<ResponseDTO> calcularTicket(@RequestBody RequestDTO requestDTO){
        return iTicketService.calcularTicket(requestDTO);
    }
}
