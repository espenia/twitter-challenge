package twitter.challenge.espenia.core.gateway;

import twitter.challenge.espenia.core.domain.Tweet;
import java.util.List;

public interface TweetGateway {
    Tweet create(Tweet tweet);
    
    Tweet findById(String id);
    
    List<Tweet> findByUserIdOrderByCreatedAtDesc(String userId, int limit);
    
    List<Tweet> findByIds(List<String> tweetIds);
    
    List<Tweet> findByUserIdsOrderByCreatedAtDesc(List<String> userIds, int limit);
}
