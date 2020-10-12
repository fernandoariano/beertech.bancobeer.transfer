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
public class CriarOperacaoTest {

    @Autowired
    private ContaService contaService;

    @Autowired
    private GerarContas gerarContas;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Nested
    public abstract class SetupDeOperacao {

        Conta contaAtualizada;
        Transacao transacao;
        Conta conta;

        @BeforeEach
        void setup() {
            conta = gerarContas.criar();
            contaAtualizada = contaService.save(getTransacaoDto(), conta.getId());
            transacao = transacaoRepository.findByContaId(conta.getId());
        }

        protected abstract TransacaoDto getTransacaoDto();
    }


    @Nested
    public class SacarValorMenorQueSaldo extends SetupDeOperacao {

        @Override
        protected TransacaoDto getTransacaoDto() {
            TransacaoDto transacaoDto = new TransacaoDto();
            transacaoDto.setOperacao(Transacao.Operacao.SAQUE.name());
            transacaoDto.setValor(100D);
            return transacaoDto;
        }

        @Test
        void deveRetornarContaComSaldoIgualA900() {
            assertThat(contaAtualizada.getSaldo()).isEqualTo(900);
        }

        @Test
        void deveCriaTransacao() {
            assertThat(transacao).isNotNull();
            assertThat(transacao.getOperacao()).isEqualTo(Transacao.Operacao.SAQUE);
            assertThat(transacao.getValor()).isEqualTo(100);
            assertThat(transacao.getDataOperacao().truncatedTo(ChronoUnit.MINUTES)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        }
    }

    @Nested
    public class SacarValorMaiorQueSaldo extends SetupDeOperacao {

        @BeforeEach
        void setup() {

        }

        @Override
        protected TransacaoDto getTransacaoDto() {
            TransacaoDto transacaoDto = new TransacaoDto();
            transacaoDto.setOperacao(Transacao.Operacao.SAQUE.name());
            transacaoDto.setValor(2000D);
            return transacaoDto;
        }

        @Test
        void deveRetornarMensagemDeErro() {
            assertThrows(RuntimeException.class, ()->{contaService.save(getTransacaoDto(), conta.getId());} );
        }
    }

    @Nested
    public class DepositorValorPositivo extends SetupDeOperacao {

        @Override
        protected TransacaoDto getTransacaoDto() {
            TransacaoDto transacaoDto = new TransacaoDto();
            transacaoDto.setOperacao(Transacao.Operacao.DEPOSITO.name());
            transacaoDto.setValor(1000D);
            return transacaoDto;
        }

        @Test
        void deveRetornarContaComSaldoIgualA2000() {
            assertThat(contaAtualizada.getSaldo()).isEqualTo(2000);
        }

        @Test
        void deveCriaTransacao() {
            assertThat(transacao).isNotNull();
            assertThat(transacao.getOperacao()).isEqualTo(Transacao.Operacao.DEPOSITO);
            assertThat(transacao.getValor()).isEqualTo(1000);
            assertThat(transacao.getDataOperacao().truncatedTo(ChronoUnit.MINUTES)).isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        }
    }

    @Nested
    public class DepositarValorNegativo extends SetupDeOperacao {

        @BeforeEach
        void setup() {

        }

        @Override
        protected TransacaoDto getTransacaoDto() {
            TransacaoDto transacaoDto = new TransacaoDto();
            transacaoDto.setOperacao(Transacao.Operacao.DEPOSITO.name());
            transacaoDto.setValor(-500D);
            return transacaoDto;
        }

        @Test
        void deveRetornarMensagemDeErro() {
            assertThrows(RuntimeException.class, ()->{contaService.save(getTransacaoDto(), conta.getId());} );
        }
    }
}
