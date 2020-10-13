package br.com.api.service;

import br.com.api.dto.TransacaoDto;
import br.com.api.dto.TransferenciaDto;
import br.com.api.model.Conta;
import br.com.api.model.Transacao;
import br.com.api.repository.ContaRepository;
import br.com.api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OperacaoService {

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    ContaRepository contaRepository;

    public void criaTransacao(Conta conta, Transacao.Operacao operacao,Double valor){
        Transacao transacao= Transacao
                .builder()
                .conta(conta)
                .operacao(operacao)
                .valor(valor)
                .dataOperacao(LocalDateTime.now())
                .build();

        conta.getTransacao().add(transacao);
        transacaoRepository.save(transacao);
    }

    public Conta saque(Conta conta, Double valor ){

        return saque(conta,valor,Transacao.Operacao.SAQUE);

    }

    public Conta saque(Conta conta, Double valor,Transacao.Operacao operacao ){

        if (conta.getSaldo() < valor) throw new RuntimeException("Saldo insuficiente");
        conta.saque(valor);

        criaTransacao(conta,operacao,valor);
        return contaRepository.save(conta);

    }


    public Conta deposito(Conta conta, Double valor ) {
        return deposito(conta,valor,Transacao.Operacao.DEPOSITO);
    }
    public Conta deposito(Conta conta, Double valor ,Transacao.Operacao operacao){

        conta.deposito(valor);
        criaTransacao(conta,operacao,valor);
        return contaRepository.save(conta);

    }

    public Conta trasnferencia(Conta contaDebito, Conta contaCredito, Double valor ){

        contaDebito.saque(valor);
        saque(contaDebito,valor,Transacao.Operacao.TRANSFERENCIA_OUT);
        contaDebito= contaRepository.save(contaDebito);
        contaCredito.deposito(valor);
        saque(contaCredito,valor,Transacao.Operacao.TRANSFERENCIA_IN);
        contaCredito = contaRepository.save(contaCredito);

        return contaDebito;

    }

    public Conta executaTransacao(TransacaoDto request, String hashConta){
        Conta conta = contaRepository.findByHash(hashConta).orElseThrow(() -> new RuntimeException("Not Found"));
        Double valor = request.getValor();
        Transacao.Operacao operacao = Transacao.Operacao.valueOf(request.getOperacao().toUpperCase());

        if (operacao == Transacao.Operacao.DEPOSITO) return deposito(conta,valor);
        if (operacao == Transacao.Operacao.SAQUE) return saque(conta,valor);

        return conta;
    }

    public Conta executaTransacao(TransferenciaDto request){
        Conta contaDebito = contaRepository.findByHash(request.getHashContaDebito()).orElseThrow(() -> new RuntimeException("Not Found"));
        Conta contaCredito = contaRepository.findByHash(request.getHashContaCredito()).orElseThrow(() -> new RuntimeException("Not Found"));
        Double valor = request.getValor();

        saque(contaDebito,valor, Transacao.Operacao.TRANSFERENCIA_OUT);
        deposito(contaCredito,valor, Transacao.Operacao.TRANSFERENCIA_IN);

        return contaDebito;
    }

}
