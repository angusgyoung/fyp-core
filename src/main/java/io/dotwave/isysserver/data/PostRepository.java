package io.dotwave.isysserver.data;

import io.dotwave.isysserver.model.post.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

    Post findByUsername(String username);

}
