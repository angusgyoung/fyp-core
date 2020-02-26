package io.dotwave.isysserver.data;

import io.dotwave.isysserver.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

    Page<Post> findAllByUsernameOrderByTimestampDesc(String username, PageRequest pageRequest);
    Page<Post> findAllByOrderByTimestampDesc(PageRequest pageRequest);

}
