package io.dotwave.isysserver.model.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    public String id;

    @NotNull
    private String content;
    private long timestamp;
    @NotNull
    private String username;
}
