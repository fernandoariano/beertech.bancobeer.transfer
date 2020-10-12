package br.com.api.rest;

import br.com.api.dto.ContaDto;
import br.com.api.dto.TransacaoDto;
import br.com.api.model.Conta;
import br.com.api.service.ContaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;


    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Cria Conta", produces = "application/json")
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

        return contaService.executaOperacao(request, hashConta);

    }

    @PostMapping(path = "/{hashContaOrigem}/{hashContaDestino}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Efetua transferencia entre contas.", produces = "application/json")
    public Conta addTransacao(
            @ApiParam(name = "hashContaOrigem", required = true, value = "Hash da conta de origem da transferência")
            @PathVariable String hashContaOrigem,
            @ApiParam(name = "hashContaDestino", required = true, value = "Hash da conta de destino da transferência")
            @PathVariable String hashContaDestino,
            @ApiParam(name = "request", required = true, value = "Operacao e valor")
            @Valid @RequestBody TransacaoDto request
    ) {

        return contaService.executaOperacao(request,hashContaOrigem,hashContaDestino);

    }


    @GetMapping(path = "/{hashConta}/saldos",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Retorna saldo da conta.", produces = "application/json")
    public Double getSaldo(@ApiParam(name = "hashConta", required = true, value = "Hash da conta")
                         @PathVariable String hashConta) {
        return contaService.getSaldo(hashConta);
    }

    @DeleteMapping(path = "/{hashConta}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Retorna saldo da conta.", produces = "application/json")
    public void deteleConta(@ApiParam(name = "hashConta", required = true, value = "Hash da conta")
                         @PathVariable String hashConta) {
        contaService.deleteConta(hashConta);
    }

}
