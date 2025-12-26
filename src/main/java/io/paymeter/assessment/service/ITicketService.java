package io.paymeter.assessment.service;

import io.paymeter.assessment.dto.RequestDTO;
import io.paymeter.assessment.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ITicketService {

    ResponseEntity<ResponseDTO> calcularTicket(RequestDTO requestDTO);

}
