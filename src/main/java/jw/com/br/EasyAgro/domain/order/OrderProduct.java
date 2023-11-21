package jw.com.br.EasyAgro.domain.order;

import jw.com.br.EasyAgro.dtos.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProduct {
    private String productId;

    private Integer quantity;

    private String buyerId;

    private String sellerId;

    private BigDecimal price;

    public OrderProduct(OrderDTO order) {
        this.productId = order.getProductId();
        this.quantity = order.getQuantity();
        this.buyerId = order.getBuyerId();
        this.sellerId = order.getSellerId();
        this.price = order.getPrice();
    }
}

