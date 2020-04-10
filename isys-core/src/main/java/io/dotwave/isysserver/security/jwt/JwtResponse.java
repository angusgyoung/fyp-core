package io.dotwave.isysserver.security.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.dotwave.isysserver.model.profile.Profile;

import java.io.Serializable;
import java.util.Objects;

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

    public String getJwtToken() {
        return this.jwtToken;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public long getExpiryDate() {
        return this.expiryDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof JwtResponse)) return false;
        final JwtResponse other = (JwtResponse) o;
        if (!other.canEqual(this)) return false;
        final Object this$jwtToken = this.getJwtToken();
        final Object other$jwtToken = other.getJwtToken();
        if (!Objects.equals(this$jwtToken, other$jwtToken)) return false;
        final Object this$profile = this.getProfile();
        final Object other$profile = other.getProfile();
        if (!Objects.equals(this$profile, other$profile)) return false;
        return this.getExpiryDate() == other.getExpiryDate();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof JwtResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $jwtToken = this.getJwtToken();
        result = result * PRIME + ($jwtToken == null ? 43 : $jwtToken.hashCode());
        final Object $profile = this.getProfile();
        result = result * PRIME + ($profile == null ? 43 : $profile.hashCode());
        final long $expiryDate = this.getExpiryDate();
        result = result * PRIME + (int) ($expiryDate >>> 32 ^ $expiryDate);
        return result;
    }

    public String toString() {
        return "JwtResponse(jwtToken=" + this.getJwtToken() + ", profile=" + this.getProfile() + ", expiryDate=" + this.getExpiryDate() + ")";
    }
}
