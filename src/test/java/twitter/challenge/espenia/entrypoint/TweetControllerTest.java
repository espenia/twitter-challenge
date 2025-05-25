package twitter.challenge.espenia.entrypoint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import twitter.challenge.espenia.core.result.TweetResponse;
import twitter.challenge.espenia.core.usecase.request.TweetRequest;
import twitter.challenge.espenia.entrypoint.exception.response.dto.ApiError;
import twitter.challenge.espenia.infra.mongodb.repository.TweetRepository;
import twitter.challenge.espenia.infra.mongodb.repository.UserRepository;
import twitter.challenge.espenia.util.Factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TweetControllerTest extends BaseControllerTest {

    @Autowired
    public TweetControllerTest(final UserRepository userRepository) {
        super(userRepository);
    }

    @Test
    void testCreateTweet() {
        // Setup user first since tweet requires valid user
        basicEntitiesSetup();
        
        // Create a tweet request
        HttpEntity<TweetRequest> requestEntity =
                new HttpEntity<>(Factory.sampleTweetCreateRequest(), this.getDefaultHeaders());
        
        // Make the request
        ResponseEntity<TweetResponse> responseEntity =
                this.testRestTemplate.exchange(
                        "/api/tweets",
                        HttpMethod.POST, requestEntity, TweetResponse.class);

        // Assert the response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        asserEqualsButId(responseEntity.getBody(), Factory.sampleTweetResponse());
    }

    @Test
    void testCreateTweetWithTooLongContent() {
        // Setup user first
        basicEntitiesSetup();
        
        // Create a tweet with content that exceeds 280 characters
        String longContent = "a".repeat(281);
        TweetRequest invalidRequest = new TweetRequest(Factory.USERID, longContent);
        
        HttpEntity<TweetRequest> requestEntity =
                new HttpEntity<>(invalidRequest, this.getDefaultHeaders());
        
        // Make the request
        ResponseEntity<ApiError> responseEntity =
                this.testRestTemplate.exchange(
                        "/api/tweets",
                        HttpMethod.POST, requestEntity, ApiError.class);

        // Assert that the request is rejected with a bad request status
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals("Tweet content must be between 1 and 280 characters", responseEntity.getBody().getMessage());
    }
}
