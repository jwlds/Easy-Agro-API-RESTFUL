package jw.com.br.EasyAgro.controllers;

import jw.com.br.EasyAgro.domain.user.User;
import jw.com.br.EasyAgro.dtos.UserDTO;
import jw.com.br.EasyAgro.services.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllProducts(){
        return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getProductById(@PathVariable ObjectId id){
        return new ResponseEntity<Optional<User>>(userService.singleUser(id), HttpStatus.OK);
    }


    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteAllProducts() {
        userService.deleteAllProducts();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteById(@PathVariable ObjectId id){
        userService.deleteUser(id);
        // http 204
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<User> createProduct(@RequestBody @Valid UserDTO payload) {
        return new ResponseEntity<User>(userService.createUser(payload), HttpStatus.OK);
    }

}

