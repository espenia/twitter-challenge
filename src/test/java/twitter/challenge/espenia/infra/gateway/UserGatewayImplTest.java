package twitter.challenge.espenia.infra.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import twitter.challenge.espenia.infra.mongodb.repository.UserRepository;
import twitter.challenge.espenia.util.Factory;
import twitter.challenge.espenia.util.UnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserGatewayImplTest extends UnitTest {
    @Autowired
    private UserGatewayImpl userGateway;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userGateway.save(Factory.sampleUserDocument());
    }

    @Test
    void testGetUserById() {
        // When
        var userResponse = userGateway.findById(Factory.USERID);

        // Then
        assertNotNull(userResponse);
        assertEquals(Factory.USERID, userResponse.getId());
    }

    @Test
    void testGetUserByUsername() {

        // When
        var userResponse = userGateway.findByUsername(Factory.USERNAME);

        // Then
        assertNotNull(userResponse);
        assertEquals(Factory.USERNAME, userResponse.getUsername());
    }

    @Test
    void testGetUserByEmail() {
        // When
        var userResponse = userGateway.findByEmail(Factory.EMAIL);
        // then
        assertNotNull(userResponse);
        assertEquals(Factory.EMAIL, userResponse.getEmail());
    }


}
