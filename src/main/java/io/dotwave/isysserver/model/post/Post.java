package io.dotwave.isysserver.model.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    public String id;

    private String content;
    private long timestamp;
    //TODO implement a relationship with users here
    private String username;

    public Post(String content, String username) {
        this.content = content;
        this.timestamp = new Date().getTime();
        this.username = username;
    }

}
