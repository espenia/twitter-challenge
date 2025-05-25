package twitter.challenge.espenia.infra.mongodb.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import twitter.challenge.espenia.infra.mongodb.document.TweetDocument;

import java.util.List;

public interface TweetRepository extends MongoRepository<TweetDocument, String> {
    
    List<TweetDocument> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
    
    @Query("{ 'id' : { $in: ?0 } }")
    List<TweetDocument> findByIdIn(List<String> ids);
    
    @Query("{ 'userId' : { $in: ?0 } }")
    List<TweetDocument> findByUserIdInOrderByCreatedAtDesc(List<String> userIds, Pageable pageable);
}
