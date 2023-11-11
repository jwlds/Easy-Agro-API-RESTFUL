package jw.com.br.EasyAgro.services.cepea;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jw.com.br.EasyAgro.dtos.CepeaProductResponse;
import jw.com.br.EasyAgro.dtos.VIaCepDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;



@Service
public class CepeaService {


    private String apiToken = "1I708CsPm6u4jG6IZyzPjFveE8LHIRkRimhWkHIG";

    public String fetchData() {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", apiToken);
        map.add("timeout", "300");


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);


        RestTemplate restTemplate = new RestTemplate();


        String apiUrl = "https://api.infosimples.com/api/v2/consultas/esalq/cepea";


        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);


        return response.getBody();
    }
}