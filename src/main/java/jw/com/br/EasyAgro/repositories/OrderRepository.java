package jw.com.br.EasyAgro.repositories;

import jw.com.br.EasyAgro.domain.order.Order;
import jw.com.br.EasyAgro.domain.product.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,ObjectId> {

    List<Order> findByBuyerId(String buyerId);

}
