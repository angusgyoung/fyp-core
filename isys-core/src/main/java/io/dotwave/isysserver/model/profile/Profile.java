package io.dotwave.isysserver.model.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

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

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public long getAccountCreatedTimestamp() {
        return this.accountCreatedTimestamp;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Profile)) return false;
        final Profile other = (Profile) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (!Objects.equals(this$username, other$username)) return false;
        final Object this$encryptedPassword = this.getEncryptedPassword();
        final Object other$encryptedPassword = other.getEncryptedPassword();
        if (!Objects.equals(this$encryptedPassword, other$encryptedPassword))
            return false;
        final Object this$profileImageUrl = this.getProfileImageUrl();
        final Object other$profileImageUrl = other.getProfileImageUrl();
        if (!Objects.equals(this$profileImageUrl, other$profileImageUrl))
            return false;
        return this.getAccountCreatedTimestamp() == other.getAccountCreatedTimestamp();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Profile;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $encryptedPassword = this.getEncryptedPassword();
        result = result * PRIME + ($encryptedPassword == null ? 43 : $encryptedPassword.hashCode());
        final Object $profileImageUrl = this.getProfileImageUrl();
        result = result * PRIME + ($profileImageUrl == null ? 43 : $profileImageUrl.hashCode());
        final long $accountCreatedTimestamp = this.getAccountCreatedTimestamp();
        result = result * PRIME + (int) ($accountCreatedTimestamp >>> 32 ^ $accountCreatedTimestamp);
        return result;
    }

    public String toString() {
        return "Profile(id=" + this.getId() + ", username=" + this.getUsername() + ", encryptedPassword=" + this.getEncryptedPassword() + ", profileImageUrl=" + this.getProfileImageUrl() + ", accountCreatedTimestamp=" + this.getAccountCreatedTimestamp() + ")";
    }
}
