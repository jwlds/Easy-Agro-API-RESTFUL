package jw.com.br.EasyAgro.repositories;

import jw.com.br.EasyAgro.domain.culture.Cultura;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CultureRepository extends MongoRepository<Cultura, ObjectId> {

}
