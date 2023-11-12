package jw.com.br.EasyAgro.controllers;

import jw.com.br.EasyAgro.domain.culture.Cultura;
import jw.com.br.EasyAgro.dtos.CulturaDTO;
import jw.com.br.EasyAgro.dtos.NameRequestBody;
import jw.com.br.EasyAgro.repositories.CultureRepository;
import jw.com.br.EasyAgro.services.CultureService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/culture")
public class CultureController {

    @Autowired
    private CultureService cultureService;
    @GetMapping
    public ResponseEntity<List<Cultura>> getAllCultures() {
        List<Cultura> cultures = cultureService.getAllCultures();
        return new ResponseEntity<>(cultures, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cultura> getCultureById(@PathVariable String id) {
        Cultura cultura = cultureService.getCultureById(id);
        return cultura != null
                ? new ResponseEntity<>(cultura, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Cultura salvarCultura(@RequestBody CulturaDTO cultura) {
        return cultureService.addCulture(cultura);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<List<?>> getInfoByType(@PathVariable String id, @RequestParam String type) {
        try {
            List<?> infoList = cultureService.getInfoByType(id, type);
            return ResponseEntity.ok(infoList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/{id}/espec")
    public Object getInfo(@PathVariable String id, @RequestParam String type, @RequestParam String name) {
        System.out.printf(name);
        return cultureService.getInfoByElement(id, type, name);
    }


}
