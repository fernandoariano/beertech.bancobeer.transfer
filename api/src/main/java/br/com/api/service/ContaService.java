package br.com.api.service;

import br.com.api.dto.ContaDto;
import br.com.api.dto.TransacaoDto;
import br.com.api.dto.TransferenciaDto;
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
    public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository, OperacaoService operacaoService) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;

    }

    public List<Conta> listAll() {
        return contaRepository.findAll();
    }


    public Double getSaldo(String hashConta) {
        Conta conta = contaRepository.findByHash(hashConta).orElseThrow(() -> new RuntimeException("Not Found"));

        return conta.getSaldo();
    }

    public Conta save(ContaDto request) {
        Conta conta = new Conta();
        conta.setSaldo(request.getSaldo());
        return contaRepository.save(conta);
    }




}
