package jw.com.br.EasyAgro.dtos.cepeaapi;

import java.util.Map;

public record HeaderDTO(String api_version,
                        String api_version_full,
                        String product,
                        String service,
                        Map<String, Object> parameters,
                        String client_name,
                        String token_name,
                        boolean billable,
                        String price,
                        String requested_at,
                        int elapsed_time_in_milliseconds,
                        String remote_ip,
                        String signature) {
}
