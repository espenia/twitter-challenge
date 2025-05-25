package twitter.challenge.espenia.core.gateway;

import twitter.challenge.espenia.core.domain.Follow;
import java.util.List;

public interface FollowGateway {

    Follow create(String followerId, String followedId);
    
    void delete(String followerId, String followedId);
    
    boolean exists(String followerId, String followedId);
    
    List<Follow> getFollowers(String userId);
    
    List<Follow> getFollowing(String userId);
    
    Follow findById(String id);
}
