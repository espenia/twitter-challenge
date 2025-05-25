package twitter.challenge.espenia.infra.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import twitter.challenge.espenia.core.domain.Tweet;
import twitter.challenge.espenia.infra.mongodb.repository.TweetRepository;
import twitter.challenge.espenia.infra.mongodb.repository.UserRepository;
import twitter.challenge.espenia.util.Factory;
import twitter.challenge.espenia.util.UnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TweetGatewayImplTest extends UnitTest {
    @Autowired
    private TweetGatewayImpl tweetGateway;

    @Autowired
    private TweetRepository tweetRepository;
    
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        tweetRepository.deleteAll();
        userRepository.deleteAll();
        
        // Create user first since we need a valid userId
        userRepository.save(Factory.sampleUserDocument());
        tweetRepository.save(Factory.sampleTweetDocument());
    }

    @Test
    void testCreateTweet() {
        // Given
        Tweet newTweet = Tweet.builder()
                .userId(Factory.USERID)
                .content("This is a new test tweet")
                .likeCount(0)
                .build();
        
        // When
        Tweet createdTweet = tweetGateway.create(newTweet);
        
        // Then
        assertNotNull(createdTweet);
        assertNotNull(createdTweet.getId());
        assertEquals(Factory.USERID, createdTweet.getUserId());
        assertEquals("This is a new test tweet", createdTweet.getContent());
        assertEquals(0, createdTweet.getLikeCount());
    }
}
