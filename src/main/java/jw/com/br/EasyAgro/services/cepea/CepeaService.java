package jw.com.br.EasyAgro.services.cepea;
import com.fasterxml.jackson.databind.ObjectMapper;
import jw.com.br.EasyAgro.domain.productCepea.CepeaProducts;
import jw.com.br.EasyAgro.dtos.cepeaapi.CepeaApiResponse;
import jw.com.br.EasyAgro.dtos.cepeaapi.ProductCepeaDTO;
import jw.com.br.EasyAgro.repositories.CepeaApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;


@Service
public class CepeaService {

    private String apiToken = "1I708CsPm6u4jG6IZyzPjFveE8LHIRkRimhWkHIG";

    @Autowired
    private CepeaApiRepository cepeaApiRepository;

    public  List<ProductCepeaDTO>  fetchDataProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", apiToken);
        map.add("timeout", "300");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://api.infosimples.com/api/v2/consultas/esalq/cepea";

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CepeaApiResponse data = objectMapper.readValue(response.getBody(), CepeaApiResponse.class);
            if (!data.data().isEmpty()) {
                List<ProductCepeaDTO> list = data.data().get(0).produtos();
                if (list != null) {
                    return list;
                }
            }
            return Collections.emptyList();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void saveDataToMongoDB() {
        List<ProductCepeaDTO> productList = fetchDataProducts();
        cepeaApiRepository.save(new CepeaProducts(Instant.now(),productList));
    }

//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
//    public void scheduledTask() {
//        saveDataToMongoDB();
//    }

    public List<CepeaProducts> allProducts(){
        return cepeaApiRepository.findAll();
    }



}