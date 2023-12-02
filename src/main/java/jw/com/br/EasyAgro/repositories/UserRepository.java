package jw.com.br.EasyAgro.repositories;

import jw.com.br.EasyAgro.domain.user.User;
import jw.com.br.EasyAgro.dtos.MyProductsDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    UserDetails findUserByLogin(String login);

    Optional<User> findUserByCpf(String cpf);




}
