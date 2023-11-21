package jw.com.br.EasyAgro.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MercadoPagoResponse(
        String action,
        @JsonProperty("api_version") String apiVersion,
        Data data,
        @JsonProperty("date_created") String dateCreated,
        long id,
        @JsonProperty("live_mode") boolean liveMode,
        String type,
        @JsonProperty("user_id") String userId
) {

    public record Data(@JsonProperty("id") String id) {
    }
}