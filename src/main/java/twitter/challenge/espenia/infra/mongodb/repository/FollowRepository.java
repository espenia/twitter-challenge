package twitter.challenge.espenia.infra.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import twitter.challenge.espenia.infra.mongodb.document.FollowDocument;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends MongoRepository<FollowDocument, String> {

    // Find a specific follow relationship
    Optional<FollowDocument> findByFollowerIdAndFollowedId(String followerId, String followedId);
    
    // Check if a follow relationship exists
    boolean existsByFollowerIdAndFollowedId(String followerId, String followedId);
    
    // Get all users that a user is following
    List<FollowDocument> findByFollowerId(String followerId);
    
    // Get all followers of a user
    List<FollowDocument> findByFollowedId(String followedId);
    
    // Delete a follow relationship
    void deleteByFollowerIdAndFollowedId(String followerId, String followedId);
}
