package io.dotwave.isysserver.model.user;

import io.dotwave.isysserver.model.post.Post;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    @Id
    public String id;

    @Indexed(unique=true)
    private String username;
    private String auth0UserId;
    private List<Post> posts = new ArrayList<>();
}
