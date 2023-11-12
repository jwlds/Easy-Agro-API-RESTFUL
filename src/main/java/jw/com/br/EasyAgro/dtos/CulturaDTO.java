package jw.com.br.EasyAgro.dtos;

import jw.com.br.EasyAgro.domain.culture.my.Deficiencia;
import jw.com.br.EasyAgro.domain.culture.my.Doenca;
import jw.com.br.EasyAgro.domain.culture.my.Praga;

import java.util.List;

public record CulturaDTO(
        String nome,
        List<Doenca> doencas,
        List<Praga> pragas,
        List<Deficiencia> deficiencias
) {
}