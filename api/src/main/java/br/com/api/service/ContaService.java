package br.com.api.service;

import br.com.api.dto.ContaDto;
import br.com.api.dto.TransacaoDto;
import br.com.api.model.Conta;
import br.com.api.model.Transacao;
import br.com.api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.api.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    @Autowired
    public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public List<Conta> listAll() {
        return contaRepository.findAll();
    }

    public Conta findById(Long idConta) {
        return contaRepository.findById(idConta).orElseThrow();
    }

    public Double getSaldo(String hashConta) {
        Conta conta = contaRepository.findByHash(hashConta).orElseThrow(() -> new RuntimeException("Not Found"));

        return conta.getSaldo();
    }

    public Conta save(Conta conta) {
       return contaRepository.save(conta);
    }


    public Conta save(ContaDto request) {
        Conta conta = new Conta();
        conta.setSaldo(request.getSaldo());
        return contaRepository.save(conta);
    }

    public Conta save(TransacaoDto request, String hashConta) {
        Conta conta = contaRepository.findByHash(hashConta).orElseThrow(() -> new RuntimeException("Not Found"));

        return(save(request,conta.getId()));

    }

    public Conta save(TransacaoDto request, Long idConta) {

        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new RuntimeException("Not Found"));

        Double valor= request.getValor();

        Transacao.Operacao operacao = Transacao.Operacao.valueOf(request.getOperacao().toUpperCase());

        if(operacao == Transacao.Operacao.SAQUE) {
            if (conta.getSaldo() < valor) throw new RuntimeException("Saldo insuficiente");
            conta.saque(valor);
        }

        if (operacao == Transacao.Operacao.DEPOSITO) conta.deposito(valor);

        Transacao transacao= Transacao
                .builder()
                .conta(conta)
                .operacao(operacao)
                .valor(request.getValor())
                .dataOperacao(LocalDateTime.now())
                .build();

        conta.getTransacao().add(transacao);
        transacaoRepository.save(transacao);
        return save(conta);
    }
}
