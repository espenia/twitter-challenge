package twitter.challenge.espenia.infra.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import twitter.challenge.espenia.infra.mongodb.document.UserDocument;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {

    Optional<UserDocument> findByUsername(String username);
    
    Optional<UserDocument> findByEmail(String email);
    
    // Check if a username exists
    boolean existsByUsername(String username);
    
    // Check if an email exists
    boolean existsByEmail(String email);
}
