package jw.com.br.EasyAgro.dtos.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jw.com.br.EasyAgro.domain.order.OrderProduct;
import jw.com.br.EasyAgro.dtos.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PixPaymentDTO {
    @NotNull
    private BigDecimal transactionAmount;

    @JsonProperty("description")
    private String productDescription;

    @NotNull
    private PayerDTO payer;

    private String buyerId;

    private List<OrderProduct> orders;

}