package jw.com.br.EasyAgro.controllers;

import jw.com.br.EasyAgro.dtos.CepeaProductResponse;
import jw.com.br.EasyAgro.services.cepea.CepeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cepea")
public class CepeaController {

    @Autowired
    private  CepeaService apiService;


    @GetMapping("/products")
    public String makeApiRequest() {
        return apiService.fetchData();
    }


}
