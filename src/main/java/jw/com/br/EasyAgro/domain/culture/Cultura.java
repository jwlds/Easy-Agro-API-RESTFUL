package jw.com.br.EasyAgro.domain.culture;

import jw.com.br.EasyAgro.domain.culture.my.Deficiencia;
import jw.com.br.EasyAgro.domain.culture.my.Praga;
import jw.com.br.EasyAgro.domain.culture.my.Doenca;
import jw.com.br.EasyAgro.dtos.CulturaDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "plantas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cultura {
    @Id
    private String _id;
    private String nome;
    private List<Doenca> doencas;
    private List<Praga> pragas;
    private List<Deficiencia> deficiencias;

    public Cultura(CulturaDTO data) {
        this.nome = data.nome();
        this.doencas = data.doencas();
        this.pragas = data.pragas();
        this.deficiencias = data.deficiencias();
    }
}
