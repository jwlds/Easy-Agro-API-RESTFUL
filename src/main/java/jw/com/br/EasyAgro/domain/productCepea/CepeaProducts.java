package jw.com.br.EasyAgro.domain.productCepea;

import jw.com.br.EasyAgro.dtos.cepeaapi.ProductCepeaDTO;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.Instant;
import java.util.List;

@Document(collection = "products_cepea")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CepeaProducts {
    @Id
    private String id;
    private Instant update_At;
    private List<ProductCepeaDTO> products;


    public CepeaProducts(Instant date,List<ProductCepeaDTO> products) {
        this.update_At = date;
        this.products = products;
    }
}
