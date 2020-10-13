package br.com.api.rest;

import br.com.api.dto.ContaDto;
import br.com.api.dto.TransacaoDto;
import br.com.api.dto.TransferenciaDto;
import br.com.api.model.Transacao;
import br.com.api.service.OperacaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import br.com.api.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import br.com.api.service.ContaService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    ContaService contaService;

    @Autowired
    OperacaoService operacaoService;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Cria conta.", produces = "application/json")
    public Conta criarConta(
            @ApiParam(name = "request", required = true, value = "Conta")
            @Valid @RequestBody ContaDto request
    ) {

        return contaService.save(request);

    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Lista as contas disponíveis.", produces = "application/json")
    public List<Conta> listAll() {
        return contaService.listAll();
    }

    @PostMapping(path = "/{hashConta}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Efetua operação na conta.", produces = "application/json")
    public Conta addTransacao(
            @ApiParam(name = "hashConta", required = true, value = "Hash da conta alvo da operação")
            @PathVariable String hashConta,
            @ApiParam(name = "request", required = true, value = "Operacao e valor")
            @Valid @RequestBody TransacaoDto request
    ) {

       return operacaoService.executaTransacao(request,hashConta);

    }

    @GetMapping(path = "/{hashConta}/saldos",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Retorna saldo da conta.", produces = "application/json")
    public ContaDto getSaldo(@ApiParam(name = "hashConta", required = true, value = "Hash da conta")
                         @PathVariable String hashConta) {
        ContaDto  contaDto = new ContaDto();
        contaDto.setSaldo(contaService.getSaldo(hashConta));
        return contaDto;
    }

    @PostMapping(path = "/transferencias",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Efetua trasnferencia entre contas.", produces = "application/json")
    public Conta addTransacao(
            @ApiParam(name = "request", required = true, value = "Hash da conta de origem, o Hash da conta de destino e o valor a ser transferido")
            @Valid @RequestBody TransferenciaDto request
    ) {

         return operacaoService.executaTransacao(request);

    }


}
