package io.dotwave.isysserver.security.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.dotwave.isysserver.model.profile.Profile;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JwtResponse  implements Serializable {

    private final String jwtToken;
    private final Profile profile;
    private final long expiryDate;

    public JwtResponse(String jwtToken, long expiryDate) {
        this.jwtToken = jwtToken;
        this.profile = null;
        this.expiryDate = expiryDate;
    }

    public JwtResponse(String jwtToken, Profile profile, long expiryDate) {
        this.jwtToken = jwtToken;
        this.profile = profile;
        this.expiryDate = expiryDate;
    }

}
