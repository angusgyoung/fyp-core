package io.dotwave.isysserver.security.jwt;

import io.dotwave.isysserver.model.profile.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse  implements Serializable {
    private final String jwtToken;
    private final Profile profile;
}
