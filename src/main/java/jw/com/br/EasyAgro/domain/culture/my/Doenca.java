package jw.com.br.EasyAgro.domain.culture.my;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Doenca {
    private String nome;
    private String agenteCausal;
    private String descricao;
    private String disseminacao;
    private String condicoesFavoraveis;
    private String controle;

}
