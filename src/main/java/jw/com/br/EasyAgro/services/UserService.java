package jw.com.br.EasyAgro.services;
import jw.com.br.EasyAgro.domain.product.Product;
import jw.com.br.EasyAgro.domain.user.User;
import jw.com.br.EasyAgro.domain.user.my.Cart;
import jw.com.br.EasyAgro.domain.user.my.Favorites;
import jw.com.br.EasyAgro.domain.user.my.MyProducts;
import jw.com.br.EasyAgro.domain.user.my.Task;
import jw.com.br.EasyAgro.dtos.*;
import jw.com.br.EasyAgro.repositories.ProductRepository;
import jw.com.br.EasyAgro.repositories.UserRepository;
import jw.com.br.EasyAgro.serversocket.Cliente;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private Cliente clienteService;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Optional<User> singleUser(String userId) {
        ObjectId objectId = new ObjectId(userId);
        return userRepository.findById(objectId);
    }


    public void deleteUser(ObjectId userId) {
        userRepository.deleteById(userId);
    }

    public void deleteAllProducts() {
        userRepository.deleteAll();
    }

    public User createUser(UserDTO user)  {
        String encodedPassword = passwordEncoder.encode(user.password());
        User newUser = new User(user);
        newUser.setPassword(encodedPassword);
        return userRepository.insert(newUser);
    }

    public User updateUser(String userId, UserUpdateDTO dataUpdate) {
        ObjectId objectId = new ObjectId(userId);

        Query query = new Query(Criteria.where("_id").is(objectId));
        Update update = new Update()
                .set("name", dataUpdate.name())
                .set("phoneNumber", dataUpdate.phoneNumber())
                .set("nickname", dataUpdate.nickname())
                .set("cpf", dataUpdate.cpf());

        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);

        return mongoTemplate.findAndModify(query, update, options, User.class);
    }

    public Optional<Cart> addProductCart(ItemUserDTO data) {
        ObjectId objectId = new ObjectId(data.userId());

        Query query = new Query(Criteria.where("_id").is(objectId));
        Update update = new Update().addToSet("myCart", new Cart(data.itemId()));

        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);

        User updatedUser = mongoTemplate.findAndModify(query, update, options, User.class);

        if (updatedUser != null) {
            List<Cart> updatedCart = updatedUser.getMyCart();
            if (updatedCart != null && !updatedCart.isEmpty()) {
                return Optional.of(updatedCart.get(updatedCart.size() - 1));
            }
        }

        return Optional.empty();
    }

    public Optional<Favorites> addProductFavorites(ItemUserDTO data) {
        ObjectId objectId = new ObjectId(data.userId());

        Query query = new Query(Criteria.where("_id").is(objectId));
        Update update = new Update().addToSet("myFavorites", new Favorites(data.itemId()));

        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);

        User updatedUser = mongoTemplate.findAndModify(query, update, options, User.class);

        if (updatedUser != null) {
            List<Favorites> updatedFavorites = updatedUser.getMyFavorites();
            if (updatedFavorites != null && !updatedFavorites.isEmpty()) {
                return Optional.of(updatedFavorites.get(updatedFavorites.size() - 1));
            }
        }

        return Optional.empty();
    }

    public List<Product> getFavoriteProductIdsByUserId(String userId) {
        ObjectId objectId = new ObjectId(userId);
        Optional<User> userOptional = userRepository.findById(objectId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();


            List<Favorites> favoriteList = user.getMyFavorites();

            List<String> favoriteProductIds = favoriteList.stream()
                    .map(Favorites::get_id)
                    .collect(Collectors.toList());

            if (!favoriteProductIds.isEmpty()) {
                return productRepository.findByIdIn(favoriteProductIds);
            }
            return Collections.emptyList();
        } else {

            return Collections.emptyList();
        }
    }

    public List<Product> getCartProductIdsByUserId(String userId) {
        ObjectId objectId = new ObjectId(userId);
        Optional<User> userOptional = userRepository.findById(objectId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();


            List<Cart> cartList = user.getMyCart();

            List<String> cartProductIds = cartList.stream()
                    .map(Cart::get_id)
                    .collect(Collectors.toList());

            if (!cartProductIds.isEmpty()) {
                return productRepository.findByIdIn(cartProductIds);
            }
            return Collections.emptyList();
        } else {

            return Collections.emptyList();
        }
    }

    public void clearCart(String userId) {
        ObjectId objectId = new ObjectId(userId);

        Query query = new Query(Criteria.where("_id").is(objectId));
        Update update = new Update().unset("myCart");

        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void clearMyFavorites(String userId) {
        ObjectId objectId = new ObjectId(userId);

        Query query = new Query(Criteria.where("_id").is(objectId));
        Update update = new Update().unset("myFavorites");

        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void removeFromMyFavorites(String userId, String productId) {
        ObjectId objectId = new ObjectId(userId);

        Query query = new Query(Criteria.where("_id").is(objectId));
        Update update = new Update().pull("myFavorites", new Favorites(productId));

        mongoTemplate.updateFirst(query, update, User.class);
    }



    public FavoriteResponseDTO isProductInFavorites(String userId, String productId) {
        ObjectId objectId = new ObjectId(userId);
        Optional<User> userOptional = userRepository.findById(objectId);

            User user = userOptional.get();
            List<Favorites> favoriteList = user.getMyFavorites();

            boolean result = favoriteList.stream()
                    .anyMatch(favorites -> favorites.get_id().equals(productId));
           return new FavoriteResponseDTO(result);

    }


    // check
    public boolean isUserExistsByCpfAndEmail(String cpf, String login) {
        Optional<User> userByCpf = userRepository.findUserByCpf(cpf);
        UserDetails userByEmail = userRepository.findUserByLogin(login);
        return userByCpf.isPresent() && userByEmail != null;
    }




}
