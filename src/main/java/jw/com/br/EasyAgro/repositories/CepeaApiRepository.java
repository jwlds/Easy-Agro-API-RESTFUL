package jw.com.br.EasyAgro.repositories;


import jw.com.br.EasyAgro.domain.productCepea.ProductCepea;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CepeaApiRepository extends MongoRepository<ProductCepea, ObjectId> {
}
