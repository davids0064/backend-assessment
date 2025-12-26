package io.paymeter.assessment.dto;

public record ResponseDTO(String parkingId, String from, String to, Integer duration, String price) {
}
