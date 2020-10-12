package br.com.api.service;

import br.com.api.model.Transacao;
import br.com.api.repository.ContaRepository;
import br.com.api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    /*@Autowired
    private TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }*/

    public Transacao save(Transacao transacao){
        return transacaoRepository.save(transacao);
    }


}
