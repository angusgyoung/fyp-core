package io.dotwave.isysserver.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse  implements Serializable {
    private final String jwttoken;
}
