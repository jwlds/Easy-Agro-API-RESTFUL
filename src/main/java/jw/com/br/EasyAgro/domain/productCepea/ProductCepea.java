package jw.com.br.EasyAgro.domain.productCepea;

import jw.com.br.EasyAgro.dtos.cepeaapi.ProductCepeaDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products_cepea")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class  ProductCepea {
    @Id
    private String id;
    private String date;
    private String product;
    private String price;
    private String name;
    private String local;
    private String unit;

    public ProductCepea(ProductCepeaDTO payload) {
        this.date = payload.data();
        this.product = payload.produto();
        this.price = payload.valor();
        this.name = payload.nome();
        this.local = payload.local();
        this.unit = payload.unidade();
    }
}

