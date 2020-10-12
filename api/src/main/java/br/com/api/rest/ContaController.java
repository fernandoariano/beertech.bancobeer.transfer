package br.com.api.rest;

import br.com.api.dto.TransacaoDto;
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


    @GetMapping(produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Lista as contas disponíveis.", produces="application/json")
    public List<Conta> listAll(){
        return contaService.listAll();
    }

    @PostMapping(path = "/{hashConta}",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Insere operação a conta.", produces="application/json")

    public Conta addTransacao(
            @ApiParam(name="hashConta", required=true, value="Hash da conta alvo da operação", example="1")
            @PathVariable String hashConta,
            @ApiParam(name="request", required=true, value="Operacao e valor")
            @Valid @RequestBody TransacaoDto request
            ){

            return contaService.save(request,hashConta);

    }


    @GetMapping(path = "/{hashConta}/saldos",
            consumes={MediaType.APPLICATION_JSON_VALUE},
            produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value="Retorna.", produces="application/json")
    public void getSaldo(@ApiParam(name="hashConta", required=true, value="Hash da conta", example="1") @PathVariable String hashConta){
        contaService.getSaldo(hashConta);
    }

}
