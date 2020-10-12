package br.com.api;

import br.com.api.dto.TransacaoDto;
import br.com.api.model.Conta;
import br.com.api.model.Transacao;
import br.com.api.repository.TransacaoRepository;
import br.com.api.service.ContaService;
import br.com.api.setup.GerarContas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetSaldoTest {

    @Autowired
    private ContaService contaService;

    @Autowired
    private GerarContas gerarContas;

    @Nested
    public abstract class SetupGetSaldo {

        Conta conta;
        Double saldo;

        @BeforeEach
        void setup() {
            conta = gerarContas.criar();
            setupConta();
            saldo = contaService.getSaldo(conta.getHash());
        }

        protected void setupConta() {
            TransacaoDto transacaoDto = new TransacaoDto();
            transacaoDto.setOperacao(Transacao.Operacao.SAQUE.name());
            transacaoDto.setValor(100D);

            TransacaoDto transacaoDto1 = new TransacaoDto();
            transacaoDto1.setOperacao(Transacao.Operacao.DEPOSITO.name());
            transacaoDto1.setValor(50d);

            contaService.save(transacaoDto, conta.getId());
            contaService.save(transacaoDto1, conta.getId());
        }
    }


    @Nested
    public class GetSaldo extends SetupGetSaldo {

        @Test
        void DeveRetornarSadoIgualA950() {
            assertThat(saldo).isEqualTo(950);
        }
    }


}
