package br.com.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
public class TransferenciaDto {
    private String hashContaDebito;
    private String hashContaCredito;
    @Positive(message = "valor deve ser maior que zero!")
    private Double valor;
}
