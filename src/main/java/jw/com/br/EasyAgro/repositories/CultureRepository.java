package jw.com.br.EasyAgro.repositories;

import jw.com.br.EasyAgro.domain.culture.Cultura;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CultureRepository extends MongoRepository<Cultura, ObjectId> {

}
