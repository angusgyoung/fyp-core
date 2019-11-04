package io.dotwave.isysserver.model.profile;

import lombok.Data;

@Data
public class Auth0User {
    private String tenant;
    private String username;
    private String email;
    private boolean emailVerified;
    private String phoneNumber;
    private boolean phoneNumberVerified;
}
