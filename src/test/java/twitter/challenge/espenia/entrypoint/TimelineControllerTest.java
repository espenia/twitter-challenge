package twitter.challenge.espenia.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import twitter.challenge.espenia.core.result.TimelineCacheResponse;
import twitter.challenge.espenia.infra.mongodb.repository.TimelineCacheRepository;
import twitter.challenge.espenia.infra.mongodb.repository.UserRepository;
import twitter.challenge.espenia.util.Factory;
import twitter.challenge.espenia.util.UnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TimelineControllerTest extends BaseControllerTest {

    @Autowired
    public TimelineControllerTest(final UserRepository userRepository) {
        super(userRepository);
    }    
    
    @Test
    void testGetTimeline() {
        // Setup
        basicEntitiesSetup();
        
        // Execute
        ResponseEntity<TimelineCacheResponse> response = testRestTemplate
                .exchange("/api/timeline/" + Factory.USERID, 
                          HttpMethod.GET, 
                          null, 
                          TimelineCacheResponse.class);
        
        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Factory.USERID, response.getBody().userId());
        assertEquals(2, response.getBody().tweets().size());
        assertEquals(2, response.getBody().tweetIds().size());
    }
}
