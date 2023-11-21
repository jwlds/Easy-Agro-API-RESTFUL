package jw.com.br.EasyAgro.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private String productId;

    private Integer quantity;

    private String buyerId;

    private String sellerId;

    private BigDecimal price;
}
