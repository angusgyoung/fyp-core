package io.dotwave.isysserver.security.jwt;

import io.dotwave.isysserver.data.ProfileRepository;
import io.dotwave.isysserver.model.profile.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public JwtUserDetailsService(@Autowired ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByUsername(username);
        if (profile == null) {
            throw new UsernameNotFoundException("User not found with username " + username);
        }

        return new org.springframework.security.core.userdetails.User(profile.getUsername(),
                profile.getEncryptedPassword(),
                new ArrayList<>());
    }
}
