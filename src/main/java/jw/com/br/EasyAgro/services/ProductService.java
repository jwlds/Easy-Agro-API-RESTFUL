package jw.com.br.EasyAgro.services;

import jw.com.br.EasyAgro.domain.product.Product;
import jw.com.br.EasyAgro.domain.review.Review;
import jw.com.br.EasyAgro.dtos.ProductDTO;
import jw.com.br.EasyAgro.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    public List<Product> allProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> singleProduct(String code){
        return productRepository.findProductByCode(code);
    }


    public void deleteProduct(String code){ productRepository.deleteByCode(code);}

    public void deleteAllProducts() { productRepository.deleteAll(); }
    public Product createProduct(ProductDTO product){
        List<Review> reviewsIds = new ArrayList<Review>();
        Instant createdAt = Instant.now();
        return productRepository.insert(new Product(product,createdAt,reviewsIds));
    }



}
