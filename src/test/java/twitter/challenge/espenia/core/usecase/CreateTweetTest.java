package twitter.challenge.espenia.core.usecase;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter.challenge.espenia.core.domain.Tweet;
import twitter.challenge.espenia.core.exception.NotFoundException;
import twitter.challenge.espenia.core.gateway.TweetGateway;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.core.result.TweetResponse;
import twitter.challenge.espenia.core.usecase.request.TweetRequest;
import twitter.challenge.espenia.util.Factory;
import twitter.challenge.espenia.util.UnitTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateTweetTest extends UnitTest {
    @InjectMocks
    private CreateTweet tweetService;

    @Mock
    private TweetGateway tweetGateway;

    @Mock
    private UserGateway userGateway;


    @Test
    void testCreateTweet() {
        // Given
        TweetRequest tweetRequest = Factory.sampleTweetCreateRequest();
        when(userGateway.findById(Factory.USERID)).thenReturn(Factory.sampleUser());
        when(tweetGateway.create(any(Tweet.class))).thenReturn(Factory.sampleTweet());

        // When
        TweetResponse response = tweetService.execute(tweetRequest);

        // Then
        assertNotNull(response);
        assertEquals(Factory.TWEET_ID, response.id());
        assertEquals(Factory.USERID, response.userId());
        assertEquals(Factory.TWEET_CONTENT, response.content());
        assertEquals(0, response.likeCount());

        verify(userGateway).findById(Factory.USERID);
        verify(tweetGateway).create(any(Tweet.class));
    }

    @Test
    void testCreateTweetWithInvalidUser() {
        // Given
        TweetRequest tweetRequest = Factory.sampleTweetCreateRequest();
        when(userGateway.findById(Factory.USERID)).thenThrow(new NotFoundException("User not found"));

        // When & Then
        assertThrows(NotFoundException.class, () -> tweetService.execute(tweetRequest));
        verify(userGateway).findById(Factory.USERID);
        verify(tweetGateway, never()).create(any(Tweet.class));
    }
}
