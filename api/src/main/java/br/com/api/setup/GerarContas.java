package br.com.api.setup;

import br.com.api.model.Conta;
import br.com.api.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GerarContas {


    private final ContaRepository contaRepository;

    @Autowired
    public GerarContas(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta criar() {
        Conta conta = new Conta();
        conta.setSaldo(1000d);
        return contaRepository.save(conta);
    }
}
