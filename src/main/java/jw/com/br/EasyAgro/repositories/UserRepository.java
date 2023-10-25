package jw.com.br.EasyAgro.repositories;

import jw.com.br.EasyAgro.domain.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    UserDetails findUserByLogin(String login);

}
