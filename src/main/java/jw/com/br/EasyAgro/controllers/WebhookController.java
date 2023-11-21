package jw.com.br.EasyAgro.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jw.com.br.EasyAgro.dtos.MercadoPagoResponse;
import jw.com.br.EasyAgro.dtos.OrderDTO;
import jw.com.br.EasyAgro.repositories.OrderRepository;
import jw.com.br.EasyAgro.services.PixPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebhookController {

    @Autowired
    private PixPaymentService pixPaymentService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MercadoPagoResponse mercadoPagoResponse = objectMapper.readValue(payload, MercadoPagoResponse.class);

            System.out.println(mercadoPagoResponse.toString());

            if ("payment.updated".equals(mercadoPagoResponse.action()) && mercadoPagoResponse.data() != null) {
                String transactionId = mercadoPagoResponse.data().id();
                System.out.println("ID da transação: " + transactionId);
                pixPaymentService.updateStatusPayment(transactionId);
            }

            return ResponseEntity.ok("Webhook recebido com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar o webhook");
        }
    }
}
