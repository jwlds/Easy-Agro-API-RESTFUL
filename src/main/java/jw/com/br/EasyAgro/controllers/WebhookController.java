package jw.com.br.EasyAgro.controllers;

import jw.com.br.EasyAgro.dtos.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mercado-pago")
public class WebhookController {

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody OrderDTO payload) {
        System.out.println("Payload recebido do MercadoPago: " + payload);
        return ResponseEntity.ok("Webhook recebido com sucesso");
    }
}
