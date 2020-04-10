package io.dotwave.isysserver.security.jwt;

import java.io.Serializable;
import java.util.Objects;

public class JwtRequest implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof JwtRequest)) return false;
        final JwtRequest other = (JwtRequest) o;
        if (!other.canEqual(this)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (!Objects.equals(this$username, other$username)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        return Objects.equals(this$password, other$password);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof JwtRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        return result;
    }

    public String toString() {
        return "JwtRequest(username=" + this.getUsername() + ", password=" + this.getPassword() + ")";
    }
}
