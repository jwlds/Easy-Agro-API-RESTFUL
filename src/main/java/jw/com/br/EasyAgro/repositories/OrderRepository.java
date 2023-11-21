package jw.com.br.EasyAgro.repositories;

import jw.com.br.EasyAgro.domain.order.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,ObjectId> {

}
