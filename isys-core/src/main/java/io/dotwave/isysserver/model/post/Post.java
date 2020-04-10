package io.dotwave.isysserver.model.post;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Post {

    @Id
    public String id;

    @NotNull
    private String content;
    private Long timestamp;
    @NotNull
    private String username;
    @NotNull
    private String signatureKey;

    public Post(String id, @NotNull String content, Long timestamp, @NotNull String username, @NotNull String signatureKey) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.username = username;
        this.signatureKey = signatureKey;
    }

    public Post() {
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

}
