package com.br.beertech.dto;


public class TransferenciaDto {

    private String hashContaDebito;
    private String hashContaCredito;
    private Double valor;

    public TransferenciaDto() {
    }

    public TransferenciaDto(String hashContaDebito, String hashContaCredito, Double valor) {
        this.hashContaDebito = hashContaDebito;
        this.hashContaCredito = hashContaCredito;
        this.valor = valor;
    }


    public String getHashContaDebito() {
        return hashContaDebito;
    }

    public void setHashContaDebito(String hashContaDebito) {
        this.hashContaDebito = hashContaDebito;
    }

    public String getHashContaCredito() {
        return hashContaCredito;
    }

    public void setHashContaCredito(String hashContaCredito) {
        this.hashContaCredito = hashContaCredito;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }



}
