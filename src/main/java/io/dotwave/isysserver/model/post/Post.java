package io.dotwave.isysserver.model.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    public String id;

    private String content;
    private long timestamp;
    private String username;
}
