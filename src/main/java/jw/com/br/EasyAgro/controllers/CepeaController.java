package jw.com.br.EasyAgro.controllers;


import jw.com.br.EasyAgro.domain.productCepea.CepeaProducts;
import jw.com.br.EasyAgro.services.cepea.CepeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/cepea")
public class CepeaController {

    @Autowired
    private  CepeaService apiService;


    @GetMapping("/products")
    public ResponseEntity<List<CepeaProducts>> getAllProductsCepea(){
        return new ResponseEntity<List<CepeaProducts>>(apiService.allProducts(), HttpStatus.OK);
    }

}
