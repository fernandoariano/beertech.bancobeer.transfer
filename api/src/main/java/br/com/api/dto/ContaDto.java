package br.com.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ContaDto {

    @Min(value = 0, message = "Valor minímo é 0 ")
    private double saldo;


}
