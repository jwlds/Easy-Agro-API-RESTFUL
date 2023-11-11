package jw.com.br.EasyAgro.controllers;

import jw.com.br.EasyAgro.dtos.VIaCepDTO;
import jw.com.br.EasyAgro.services.viacep.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private CepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<VIaCepDTO> getAddressByCep(@PathVariable String cep) {
        VIaCepDTO address = null;
        try {
             address = cepService.findAddressByCep(cep);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return address != null ? ResponseEntity.ok().body(address) : ResponseEntity.notFound().build();

    }

}