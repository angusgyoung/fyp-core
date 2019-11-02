package io.dotwave.isysserver.data;

import io.dotwave.isysserver.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
    boolean existsByUsername(String username);
}
