package io.dotwave.isysserver.model.user;

import io.dotwave.isysserver.model.post.Post;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    @Id
    public String id;

    @Indexed(unique=true)
    @NotNull
    @Length(min = 3, max = 100)
    private String username;
    @NotNull
    private String auth0UserId;
    // clients should never be passing a user with
    // this collection populated
    @Null
    private List<Post> posts = new ArrayList<>();
}
