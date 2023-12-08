package jw.com.br.EasyAgro.repositories;


import jw.com.br.EasyAgro.domain.productCepea.CepeaProducts;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CepeaApiRepository extends MongoRepository<CepeaProducts, ObjectId> {
}
