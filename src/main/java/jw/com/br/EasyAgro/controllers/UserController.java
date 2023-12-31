package jw.com.br.EasyAgro.controllers;

import jw.com.br.EasyAgro.domain.order.Order;
import jw.com.br.EasyAgro.domain.order.OrderProduct;
import jw.com.br.EasyAgro.domain.product.Product;
import jw.com.br.EasyAgro.domain.user.User;
import jw.com.br.EasyAgro.domain.user.my.Cart;
import jw.com.br.EasyAgro.domain.user.my.Favorites;
import jw.com.br.EasyAgro.domain.user.my.MyProducts;
import jw.com.br.EasyAgro.domain.user.my.Task;
import jw.com.br.EasyAgro.dtos.*;
import jw.com.br.EasyAgro.repositories.OrderRepository;
import jw.com.br.EasyAgro.serversocket.Cliente;
import jw.com.br.EasyAgro.services.PixPaymentService;
import jw.com.br.EasyAgro.services.TaskService;
import jw.com.br.EasyAgro.services.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.OpenFilesEvent;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;


    @Autowired
    private PixPaymentService pixPaymentService;

    @Autowired
    private Cliente clienteService;

    @GetMapping
    public ResponseEntity<List<User>> getAllProducts(){
        return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getProductById(@PathVariable String id){
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
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody @Valid UserDTO payload) {
        try {
            boolean isCpfValid = clienteService.iniciarCliente(payload.cpf());
            if (isCpfValid) {
                if (!userService.isUserExistsByCpfAndEmail(payload.cpf(), payload.login())) {
                    System.out.println(payload.address());
                    User createdUser = userService.createUser(payload);
                    return new ResponseEntity<>(createdUser, HttpStatus.OK);
                } else {
                    System.out.println("cpf ja ");
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body("User with the given CPF and e-mail already exists.");
                }
            } else {
                System.out.println("cpf ja 2");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Invalid CPF. User creation rejected.");
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing the request.");
        }
    }


    @GetMapping("/myTasks/{userId}")
    public ResponseEntity<List<Task>> getMyTasks(@PathVariable String userId) {
        Optional<List<Task>> myTasks = taskService.myTasks(userId);
        if (myTasks.isPresent()) {
            return new ResponseEntity<>(myTasks.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/myTasks/add/{userId}")
    public ResponseEntity<String> addTask(@RequestParam String userId, @RequestBody Task task) {

        Optional<Task> addedTask = taskService.addTask(userId, task);

        return addedTask.map(t -> ResponseEntity.status(HttpStatus.CREATED).body("Tarefa adicionada com sucesso"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar tarefa"));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody UserUpdateDTO dataUpdate) {
        User updatedUser = userService.updateUser(userId, dataUpdate);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cart/add")
    public ResponseEntity<Cart> addProductToCart(@RequestBody ItemUserDTO data) {
        Optional<Cart> addProduct = userService.addProductCart(data);

        return addProduct.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cart/{userId}")
    public List<Product> getCartProductIds(@PathVariable String userId) {
        return userService.getCartProductIdsByUserId(userId);
    }

    @PostMapping("/favorite/add")
    public ResponseEntity<Favorites> addProductToFavorite(@RequestBody ItemUserDTO data) {
        Optional<Favorites> addProduct = userService.addProductFavorites(data);

        return addProduct.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/myFavorites/{userId}")
    public List<Product> getFavoriteProductIds(@PathVariable String userId) {
        return userService.getFavoriteProductIdsByUserId(userId);
    }

    @GetMapping("/myFavorites/{userId}/check/{productId}")
    public FavoriteResponseDTO getStatusFavorite(@PathVariable String userId, @PathVariable String productId) {
        return userService.isProductInFavorites(userId,productId);
    }

    @DeleteMapping("/cart/clean/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable String userId) {
        userService.clearCart(userId);
        return ResponseEntity.ok("Carrinho limpo com sucesso.");
    }

    @DeleteMapping("/myFavorites/clean/{userId}")
    public ResponseEntity<String> clearMyFavorites(@PathVariable String userId) {
        userService.clearMyFavorites(userId);
        return ResponseEntity.ok("Favoritos limpo com sucesso.");
    }

    @DeleteMapping("/myFavorites/user/{userId}/remove/{productId}")
    public ResponseEntity<String> removeFavorite(@PathVariable String userId,@PathVariable String productId) {
        userService.removeFromMyFavorites(userId,productId);
        return ResponseEntity.ok("Favoritos limpo com sucesso.");
    }

    @GetMapping("/myOrders/{id}")
    public List<Order> getMyOrdersById(@PathVariable String id) {
        return pixPaymentService.myOrders(id);
    }

    @GetMapping("/mySellerProducts/{sellerId}")
    public List<OrderProduct> getSellerProducts(@PathVariable String sellerId) {
        return pixPaymentService.mySellerProducts(sellerId);
    }









}

