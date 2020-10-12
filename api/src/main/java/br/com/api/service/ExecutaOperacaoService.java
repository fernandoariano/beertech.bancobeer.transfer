package br.com.api.service;

import br.com.api.model.Conta;
import br.com.api.repository.ContaRepository;
import br.com.api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutaOperacaoService {

    public void operacao(ExecutaOperacao executaOperacao,
                         Conta conta,
                         Double valor) {

        executaOperacao.executa(conta, valor);

    }

        public void saque(Conta conta, Double valor) {
        operacao(new Saque(), conta, valor);
    }

    public void deposito(Conta conta, Double valor) {
        operacao(new Deposito(), conta, valor);
    }

    public void transferencia(Conta contaOrigem, Conta contaDestino, Double valor) {

        operacao(new Saque(), contaOrigem, valor);
        operacao(new Deposito(), contaDestino, valor);

    }


}
