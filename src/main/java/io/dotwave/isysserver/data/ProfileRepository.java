package io.dotwave.isysserver.data;

import io.dotwave.isysserver.model.profile.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {

    Profile findByUser_Username(String username);
    boolean existsByUser_Username(String username);
}
