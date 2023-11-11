package jw.com.br.EasyAgro.dtos.cepeaapi;

import java.util.List;


public record DataItemDTO(
        List<ProductCepeaDTO> produtos,
        String site_receipt)

{
}
