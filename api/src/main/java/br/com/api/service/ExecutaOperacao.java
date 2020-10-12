package br.com.api.service;

import br.com.api.model.Conta;

public interface ExecutaOperacao {

    void executa(Conta conta, Double valor);

}
