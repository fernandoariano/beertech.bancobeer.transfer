package com.br.beertech.listeners;

import com.br.beertech.dto.TransferenciaDto;
import com.br.beertech.messages.TransferenciaMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransferenciaListener {

    private final RestTemplate restTemplate;

    @Autowired
    public TransferenciaListener(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RabbitListener(queues = "transferencia",containerFactory = "simpleContainerFactory")
    public void receive(@Payload TransferenciaMessage transferenciaMessage){

        System.out.println("Consumindo " + transferenciaMessage.toString());

        TransferenciaDto transferenciaDto = new TransferenciaDto(transferenciaMessage.getHashContaDebito(),transferenciaMessage.getHashContaDebito(),transferenciaMessage.getValor());
        try{
            restTemplate.postForObject("http://localhost:8080/contas/transferencias/", transferenciaDto ,Void.class);
        }catch (Exception e){
            System.out.println("Error on try request");
        }
    }
}
