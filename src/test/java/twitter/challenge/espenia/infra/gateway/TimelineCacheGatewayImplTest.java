package twitter.challenge.espenia.infra.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import twitter.challenge.espenia.core.domain.TimelineCache;
import twitter.challenge.espenia.infra.mongodb.repository.TimelineCacheRepository;
import twitter.challenge.espenia.infra.mongodb.repository.UserRepository;
import twitter.challenge.espenia.util.Factory;
import twitter.challenge.espenia.util.UnitTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TimelineCacheGatewayImplTest extends UnitTest {
    
    @Autowired
    private TimelineCacheGatewayImpl timelineCacheGateway;
    
    @Autowired
    private TimelineCacheRepository timelineCacheRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
        timelineCacheRepository.deleteAll();
        userRepository.deleteAll();
        
        // Create user first since we need a valid userId
        userRepository.save(Factory.sampleUserDocument());
    }
    
    @Test
    void testSaveTimelineCache() {
        // Given
        List<String> tweetIds = Arrays.asList("tweet1", "tweet2", "tweet3");
        TimelineCache timelineCache = TimelineCache.builder()
                .userId(Factory.USERID)
                .tweetIds(tweetIds)
                .lastUpdated(new Date())
                .build();
        
        // When
        TimelineCache savedCache = timelineCacheGateway.save(timelineCache);
        
        // Then
        assertNotNull(savedCache);
        assertNotNull(savedCache.getId());
        assertEquals(Factory.USERID, savedCache.getUserId());
        assertEquals(tweetIds, savedCache.getTweetIds());
        assertNotNull(savedCache.getLastUpdated());
    }
    
    @Test
    void testUpdateTimeline() {
        // Given
        List<String> initialTweetIds = Arrays.asList("tweet1", "tweet2");
        List<String> updatedTweetIds = Arrays.asList("tweet3", "tweet4", "tweet5");
        
        // When
        timelineCacheGateway.updateTimeline(Factory.USERID, initialTweetIds);
        
        // Then - First update creates a new document
        TimelineCache initialCache = timelineCacheGateway.findByUserId(Factory.USERID);
        assertNotNull(initialCache);
        assertEquals(Factory.USERID, initialCache.getUserId());
        assertEquals(initialTweetIds, initialCache.getTweetIds());
        
        // When - Update the existing document
        timelineCacheGateway.updateTimeline(Factory.USERID, updatedTweetIds);
        
        // Then - Verify update was successful
        TimelineCache updatedCache = timelineCacheGateway.findByUserId(Factory.USERID);
        assertNotNull(updatedCache);
        assertEquals(Factory.USERID, updatedCache.getUserId());
        assertEquals(updatedTweetIds, updatedCache.getTweetIds());
    }
}
