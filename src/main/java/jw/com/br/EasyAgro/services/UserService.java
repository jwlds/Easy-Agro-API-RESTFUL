package jw.com.br.EasyAgro.services;
import jw.com.br.EasyAgro.domain.user.User;
import jw.com.br.EasyAgro.dtos.UserDTO;
import jw.com.br.EasyAgro.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public Optional<User> singleUser(ObjectId userId){
        return userRepository.findById(userId);
    }


    public void deleteUser(ObjectId userId){ userRepository.deleteById(userId);}

    public void deleteAllProducts() { userRepository.deleteAll(); }
    public User createUser(UserDTO user){
        return userRepository.insert(new User(user));
    }


}
