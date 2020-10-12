package br.com.api.service;

import br.com.api.model.Conta;
import br.com.api.model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public class Deposito implements ExecutaOperacao {


    private ContaService contaService = new ContaService();

    private TransacaoService transacaoService = new TransacaoService();

    @Override
    public void executa(Conta conta, Double valor) {
        conta.deposito(valor);

        Transacao transacao= Transacao
                .builder()
                .conta(conta)
                .operacao(Transacao.Operacao.SAQUE)
                .valor(valor)
                .dataOperacao(LocalDateTime.now())
                .build();

        conta.getTransacao().add(transacao);
        transacaoService.save(transacao);
        contaService.save(conta);
    }
}
