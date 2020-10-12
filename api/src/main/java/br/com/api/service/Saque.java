package br.com.api.service;

import br.com.api.model.Conta;
import br.com.api.model.Transacao;
import br.com.api.repository.ContaRepository;
import br.com.api.repository.TransacaoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@NoArgsConstructor
public class Saque implements ExecutaOperacao {

    @Autowired
    ContaService contaService = new ContaService();
    @Autowired
    TransacaoService transacaoService;

/*
    @Autowired
    Saque (ContaRepository contaRepository,
                            TransacaoRepository transacaoRepository){
        this.contaService = contaService;
        this.transacaoService = transacaoService;
    }
*/

    @Override
    public void executa(Conta conta, Double valor) {

        if (conta.getSaldo() < valor) throw new RuntimeException("Saldo insuficiente");
        conta.saque(valor);

        Transacao transacao= new Transacao();
        transacao =
                transacao
                .builder()
                .conta(conta)
                .operacao(Transacao.Operacao.SAQUE)
                .valor(valor)
                .dataOperacao(LocalDateTime.now())
                .build();

        conta.getTransacao().add(transacao);
        //transacaoService.save(transacao);
        Conta save = contaService.save(conta);

    }

}
