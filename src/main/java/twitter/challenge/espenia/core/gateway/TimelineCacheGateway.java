package twitter.challenge.espenia.core.gateway;

import twitter.challenge.espenia.core.domain.TimelineCache;
import java.util.List;

public interface TimelineCacheGateway {

    TimelineCache findByUserId(String userId);
    
    TimelineCache save(TimelineCache timelineCache);
    
    boolean existsByUserId(String userId);
    
    void updateTimeline(String userId, List<String> tweetIds);
}
