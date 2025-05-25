package twitter.challenge.espenia.infra.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import twitter.challenge.espenia.infra.mongodb.document.TimelineCacheDocument;

import java.util.Optional;

@Repository
public interface TimelineCacheRepository extends MongoRepository<TimelineCacheDocument, String> {
    
    Optional<TimelineCacheDocument> findByUserId(String userId);
    
    boolean existsByUserId(String userId);
}
