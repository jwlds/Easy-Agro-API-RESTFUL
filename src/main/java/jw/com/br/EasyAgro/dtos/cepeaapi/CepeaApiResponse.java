package jw.com.br.EasyAgro.dtos.cepeaapi;

import java.util.List;

public record CepeaApiResponse(int code,
                               String code_message,
                               HeaderDTO header,
                               int data_count,
                               List<DataItemDTO> data,
                               List<String> errors,
                               List<String> site_receipts) {
}
