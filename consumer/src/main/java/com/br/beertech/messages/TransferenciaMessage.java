package com.br.beertech.messages;

public class TransferenciaMessage {
    private String hashContaDebito;
    private String hashContaCredito;
    private Double valor;

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


    @Override
    public String toString() {
        return "TransferenciaMessage{" +
                "hashContaDebito='" + hashContaDebito + '\'' +
                ", hashContaCredito='" + hashContaCredito + '\'' +
                ", valor=" + valor +
                '}';
    }
}
