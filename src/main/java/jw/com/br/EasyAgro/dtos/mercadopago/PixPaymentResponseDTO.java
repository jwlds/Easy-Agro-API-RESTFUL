package jw.com.br.EasyAgro.dtos.mercadopago;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PixPaymentResponseDTO {
    private Long id;
    private String status;
    private String detail;
    private String qrCodeBase64;
    private String qrCode;

}