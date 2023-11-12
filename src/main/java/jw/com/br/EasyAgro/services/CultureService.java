package jw.com.br.EasyAgro.services;

import jw.com.br.EasyAgro.domain.culture.Cultura;
import jw.com.br.EasyAgro.domain.culture.my.Deficiencia;
import jw.com.br.EasyAgro.domain.culture.my.Doenca;
import jw.com.br.EasyAgro.domain.culture.my.Praga;
import jw.com.br.EasyAgro.dtos.CulturaDTO;
import jw.com.br.EasyAgro.dtos.InfoDTO;
import jw.com.br.EasyAgro.repositories.CultureRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CultureService {
    @Autowired
    private CultureRepository cultureRepository;

    public List<Cultura> getAllCultures() {
        return cultureRepository.findAll();
    }

    public Cultura getCultureById(String id) {
        ObjectId objectId = new ObjectId(id);
        return cultureRepository.findById(objectId).orElse(null);
    }

    public Cultura addCulture(CulturaDTO cultura) {
        return cultureRepository.save(new Cultura(cultura));
    }


    public List<?> getInfoByType(String id, String type) {
        ObjectId objectId = new ObjectId(id);
        Cultura cultura = cultureRepository.findById(objectId).orElse(null);

        if (cultura != null) {
            switch (type.toLowerCase()) {
                case "doencas":
                    return cultura.getDoencas();
                case "pragas":
                    return cultura.getPragas();
                case "deficiencias":
                    return cultura.getDeficiencias();
                default:
                    throw new IllegalArgumentException("Invalid type: " + type);
            }
        } else {
            System.out.printf("erro");
        }
        throw new IllegalArgumentException("Invalid type: " + type);
    }

    public Object getInfoByElement(String id, String type, String name) {
        var list = getInfoByType(id, type);

        if (list != null) {
            if (list.stream().allMatch(element -> element instanceof Doenca)) {
                var result = list.stream()
                        .map(element -> (Doenca) element)
                        .filter(data -> data.getNome().toLowerCase().equals(name.toLowerCase()))
                        .findFirst();
                System.out.printf(result.toString());
                return result;
            } else if (list.stream().allMatch(element -> element instanceof Deficiencia)) {
                var result = list.stream()
                        .map(element -> (Deficiencia) element)
                        .filter(data -> data.getNome().toLowerCase().equals(name.toLowerCase()))
                        .findFirst();
                return result;
            } else if (list.stream().allMatch(element -> element instanceof Praga)) {
                var result = list.stream()
                        .map(element -> (Praga) element)
                        .filter(data -> data.getNome().toLowerCase().equals(name.toLowerCase()))
                        .findFirst();
                return result;
            } else {
                return null;
            }
        }
        return null;
    }





}
