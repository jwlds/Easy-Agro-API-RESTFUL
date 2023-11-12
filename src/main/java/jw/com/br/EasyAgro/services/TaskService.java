package jw.com.br.EasyAgro.services;

import jw.com.br.EasyAgro.domain.product.Product;
import jw.com.br.EasyAgro.domain.review.Review;
import jw.com.br.EasyAgro.domain.user.User;
import jw.com.br.EasyAgro.domain.user.my.MyProducts;
import jw.com.br.EasyAgro.domain.user.my.Task;
import jw.com.br.EasyAgro.dtos.ProductDTO;
import jw.com.br.EasyAgro.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.FindAndModifyOptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<List<Task>> myTasks(String userId) {

            ObjectId objectId = new ObjectId(userId);
            Optional<User> userOptional = userRepository.findById(objectId);
            return userOptional.map(user -> user.getMyTask());
    }

    public Optional<Task> addTask(String userId, Task task) {
        ObjectId objectId = new ObjectId(userId);

        Query query = new Query(Criteria.where("_id").is(objectId));
        Update update = new Update().addToSet("myTask", task);

        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);

        User updatedUser = mongoTemplate.findAndModify(query, update, options, User.class);

        if (updatedUser != null) {
            List<Task> updatedTasks = updatedUser.getMyTask();
            if (updatedTasks != null && !updatedTasks.isEmpty()) {
                return Optional.of(updatedTasks.get(updatedTasks.size() - 1)); // Retorna a última tarefa adicionada
            }
        }

        return Optional.empty();
    }

}
