package jw.com.br.EasyAgro.controllers;

import jakarta.validation.Valid;
import jw.com.br.EasyAgro.dtos.OrderDTO;
import jw.com.br.EasyAgro.dtos.mercadopago.PixPaymentDTO;
import jw.com.br.EasyAgro.dtos.mercadopago.PixPaymentResponseDTO;
import jw.com.br.EasyAgro.services.PixPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process_payment")
public class PixPaymentController {
    private final PixPaymentService pixPaymentService;

    @Autowired
    public PixPaymentController(PixPaymentService pixPaymentService) {
        this.pixPaymentService = pixPaymentService;
    }

    @PostMapping
    public ResponseEntity<PixPaymentResponseDTO> processPayment(@RequestBody @Valid PixPaymentDTO pixPaymentDTO) {
        PixPaymentResponseDTO payment = pixPaymentService.processPayment(pixPaymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}