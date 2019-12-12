package io.dotwave.isysserver.model.profile;

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
    }

    @Id
    public String id;
    private String username;
    private String encryptedPassword;
}
