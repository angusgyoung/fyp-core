package io.dotwave.isysserver.model.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor
public class Profile {

    public Profile(String username, String password) {
        this.username = username;
        this.encryptedPassword = new BCryptPasswordEncoder().encode(password);
        this.accountCreatedTimestamp = System.currentTimeMillis() / 1000L;
    }

    public Profile(String username, String password, String profileImageUrl) {
        this(username, password);
        this.profileImageUrl = profileImageUrl;
    }

    @Id
    public String id;
    private String username;
    // we should never need to send this back to a client
    // so mark it as write only to prevent serialisation
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String encryptedPassword;
    private String profileImageUrl;
    private long accountCreatedTimestamp;
}
