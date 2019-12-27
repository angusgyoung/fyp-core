package io.dotwave.isysserver.model.profile;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Profile {
    @Id
    public String id;
    private Auth0User user;
}
