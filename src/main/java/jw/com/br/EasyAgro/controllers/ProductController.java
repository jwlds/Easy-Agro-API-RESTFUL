package jw.com.br.EasyAgro.controllers;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jw.com.br.EasyAgro.domain.product.Product;
import jw.com.br.EasyAgro.dtos.ProductDTO;
import jw.com.br.EasyAgro.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<List<Product>>(productService.allProducts(), HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable String code){
        return new ResponseEntity<Optional<Product>>(productService.singleProduct(code), HttpStatus.OK);
    }


    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteAllProducts() {
        productService.deleteAllProducts();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteById(@PathVariable String code){
        productService.deleteProduct(code);
        // http 204
        return ResponseEntity.noContent().build();
    }

    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO payload) {
        return new ResponseEntity<Product>(productService.createProduct(payload), HttpStatus.OK);
    }

}
