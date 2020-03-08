package io.dotwave.isysserver.data;

import io.dotwave.isysserver.model.profile.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    Profile findByUsername(String username);
    boolean existsByUsername(String username);
}
