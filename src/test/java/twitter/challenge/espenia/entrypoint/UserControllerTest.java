package twitter.challenge.espenia.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import twitter.challenge.espenia.core.result.UserResponse;
import twitter.challenge.espenia.core.usecase.request.UserRequest;
import twitter.challenge.espenia.core.usecase.request.UserUpdateRequest;
import twitter.challenge.espenia.infra.mongodb.repository.UserRepository;
import twitter.challenge.espenia.util.Factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    public UserControllerTest(final UserRepository userRepository) {
        super(userRepository);
    }

    @Test
    void testGetUserById() {
        basicEntitiesSetup();
        ResponseEntity<UserResponse> responseEntity =
                this.testRestTemplate.exchange(
                        String.format("/api/users/%s", Factory.USERID),
                        HttpMethod.GET, this.getDefaultRequestEntity(), UserResponse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Factory.sampleUserResponse(), responseEntity.getBody());
    }

    @Test
    void testCreateUser() {
        HttpEntity<UserRequest> requestEntity =
                new HttpEntity<>(Factory.sampleUserCreateRequest(), this.getDefaultHeaders());
        ResponseEntity<UserResponse> responseEntity =
                this.testRestTemplate.exchange(
                        "/api/users",
                        HttpMethod.POST, requestEntity, UserResponse.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        asserEqualsButId(Factory.sampleUserResponse(), responseEntity.getBody());
    }

    @Test
    void testUpdateUser() {
        basicEntitiesSetup();
        HttpEntity<UserUpdateRequest> requestEntity =
                new HttpEntity<>(Factory.sampleUserUpdateRequest(), this.getDefaultHeaders());
        ResponseEntity<UserResponse> responseEntity =
                this.testRestTemplate.exchange(
                        "/api/users/%s".formatted(Factory.USERID),
                        HttpMethod.PATCH, requestEntity, UserResponse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Factory.sampleUserUpdatedResponse(), responseEntity.getBody());
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<Void> responseEntity =
                this.testRestTemplate.exchange(
                        String.format("/api/users/%s", Factory.USERID),
                        HttpMethod.DELETE, this.getDefaultRequestEntity(), Void.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertFalse(userRepository.existsByUsername(Factory.USERNAME));
    }
}
