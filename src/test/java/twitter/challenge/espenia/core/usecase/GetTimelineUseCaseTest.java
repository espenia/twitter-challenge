package twitter.challenge.espenia.core.usecase;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter.challenge.espenia.core.domain.Follow;
import twitter.challenge.espenia.core.domain.TimelineCache;
import twitter.challenge.espenia.core.domain.Tweet;
import twitter.challenge.espenia.core.gateway.FollowGateway;
import twitter.challenge.espenia.core.gateway.TimelineCacheGateway;
import twitter.challenge.espenia.core.gateway.TweetGateway;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.core.result.TimelineCacheResponse;
import twitter.challenge.espenia.util.Factory;
import twitter.challenge.espenia.util.UnitTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetTimelineUseCaseTest extends UnitTest {

    @Mock
    private TweetGateway tweetGateway;
    
    @Mock
    private UserGateway userGateway;
    
    @Mock
    private FollowGateway followGateway;
    
    @Mock
    private TimelineCacheGateway timelineCacheGateway;
    
    @InjectMocks
    private GetTimelineUseCase getTimelineUseCase;
    
    @Test
    void testGetTimelineWithFreshCache() {
        // Given
        String userId = Factory.USERID;
        List<String> tweetIds = Arrays.asList(Factory.TWEET_ID, "tweet2", "tweet3");
        
        TimelineCache cache = Factory.freshTimeLineCache(userId, tweetIds);
                
        List<Tweet> tweets = Arrays.asList(
                Factory.sampleTweet(),
                Tweet.builder().id("tweet2").userId("user2").content("Content 2").createdAt(new Date()).likeCount(0).build(),
                Tweet.builder().id("tweet3").userId("user3").content("Content 3").createdAt(new Date()).likeCount(0).build()
        );
        
        // When
        when(userGateway.findById(userId)).thenReturn(Factory.sampleUser());
        when(timelineCacheGateway.existsByUserId(userId)).thenReturn(true);
        when(timelineCacheGateway.findByUserId(userId)).thenReturn(cache);
        when(tweetGateway.findByIds(tweetIds)).thenReturn(tweets);
        
        // Execute
        TimelineCacheResponse response = getTimelineUseCase.execute(userId);
        
        // Then
        assertNotNull(response);
        assertEquals(userId, response.userId());
        assertEquals(tweetIds, response.tweetIds());
        assertEquals(tweets.size(), response.tweets().size());
        
        // Verify cache was used and not regenerated
        verify(timelineCacheGateway, never()).updateTimeline(anyString(), anyList());
    }
    
    @Test
    void testGetTimelineWithNoCacheGeneratesNewTimeline() {
        // Given
        String userId = Factory.USERID;
        String followedUserId = "followed123";
        
        List<Tweet> userTweets = Collections.singletonList(Factory.sampleTweet());
        List<Tweet> followedTweets = Collections.singletonList(
                Tweet.builder().id("tweet2").userId(followedUserId).content("Content 2").createdAt(new Date()).likeCount(0).build()
        );
        
        List<Follow> following = Collections.singletonList(
                Follow.builder().id("follow1").followerId(userId).followedId(followedUserId).createdAt(new Date()).build()
        );
        
        // When
        when(userGateway.findById(userId)).thenReturn(Factory.sampleUser());
        when(timelineCacheGateway.existsByUserId(userId)).thenReturn(false);
        when(followGateway.getFollowing(userId)).thenReturn(following);
        when(tweetGateway.findByUserIdOrderByCreatedAtDesc(eq(userId), anyInt())).thenReturn(userTweets);
        when(tweetGateway.findByUserIdsOrderByCreatedAtDesc(eq(List.of(followedUserId)), anyInt())).thenReturn(followedTweets);
        
        // Execute
        TimelineCacheResponse response = getTimelineUseCase.execute(userId);
        
        // Then
        assertNotNull(response);
        assertEquals(userId, response.userId());
        assertEquals(2, response.tweets().size()); // Should have tweets from user and followed user
        
        // Verify new timeline was generated and cached
        verify(timelineCacheGateway).updateTimeline(eq(userId), anyList());
    }
}
