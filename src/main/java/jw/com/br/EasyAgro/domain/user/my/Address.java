package jw.com.br.EasyAgro.domain.user.my;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Address {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String numero;
}
