package jw.com.br.EasyAgro.services;

import jw.com.br.EasyAgro.domain.product.Product;
import jw.com.br.EasyAgro.domain.review.Review;
import jw.com.br.EasyAgro.domain.user.User;
import jw.com.br.EasyAgro.domain.user.my.MyProducts;
import jw.com.br.EasyAgro.dtos.ProductDTO;
import jw.com.br.EasyAgro.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Product> allProducts(){
        return productRepository.findAll();
    }

    public List<Product> allProductsByUser(String userId) {
        return productRepository.findByUserId(userId);
    }

    public Optional<Product> singleProduct(String productId){
        ObjectId objectId = new ObjectId(productId);
        return productRepository.findById(objectId);
    }


    public void deleteProduct(String code){ productRepository.deleteByCode(code);}

    public void deleteAllProducts() { productRepository.deleteAll(); }
    public Product createProduct(ProductDTO productdto){
        List<Review> reviewsIds = new ArrayList<Review>();
        Instant createdAt = Instant.now();
        Product product = new Product(productdto,createdAt,reviewsIds);

        mongoTemplate.update(User.class)
                .matching(Criteria.where("id").is(productdto.userId()))
                .apply(new Update().push("myAdverts").value(new MyProducts(product.getId())))
                .first();

        return productRepository.insert(product);
    }



}
