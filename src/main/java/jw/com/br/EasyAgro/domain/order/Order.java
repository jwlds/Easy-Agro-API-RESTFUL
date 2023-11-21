package jw.com.br.EasyAgro.domain.order;

import jw.com.br.EasyAgro.dtos.OrderDTO;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private String id;
    private String buyerId;
    private BigDecimal totalPrice;
    private Instant createdAt;
    private String status;
    private List<OrderProduct> products;

    public Order(BigDecimal totalPrice,String status,List<OrderProduct> products){
        this.products = products;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = Instant.now();
    }
}


