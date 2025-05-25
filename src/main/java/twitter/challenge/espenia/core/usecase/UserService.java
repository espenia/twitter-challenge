package twitter.challenge.espenia.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import twitter.challenge.espenia.core.domain.User;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.core.usecase.request.UserRequest;
import twitter.challenge.espenia.core.result.UserResponse;
import twitter.challenge.espenia.core.usecase.request.UserUpdateRequest;
import twitter.challenge.espenia.core.usecase.validator.ValidateUser;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;
    private final ValidateUser validateUser;

    @Transactional
    public UserResponse create(final UserRequest userRequest) {
        // Check if username or email already exists
        validateUser.execute(userRequest);
        
        // Create user document
        final User user = User.builder()
                .username(userRequest.username())
                .displayName(userRequest.displayName())
                .email(userRequest.email())
                .passwordHash(passwordEncoder.encode(userRequest.password()))
                .bio(userRequest.bio())
                .build();

        return mapToUserResponse(userGateway.create(user));
    }

    public UserResponse getUserById(final String id) {
        return mapToUserResponse(userGateway.findById(id));
    }

    public UserResponse getUserByUsername(final String username) {
        return mapToUserResponse(userGateway.findByUsername(username));
    }

    @Transactional
    public UserResponse updateUser(final String id, final UserUpdateRequest updateRequest) {
        final User user = userGateway.findById(id);

        if (StringUtils.isNotBlank(updateRequest.displayName())) {
            user.setDisplayName(updateRequest.displayName());
        }
        if (StringUtils.isNotBlank(updateRequest.bio())) {
            user.setBio(updateRequest.bio());
        }
        if (StringUtils.isNotBlank(updateRequest.password())) {
            user.setPasswordHash(passwordEncoder.encode(updateRequest.password()));
        }
        
        return mapToUserResponse(userGateway.update(user));
    }

    @Transactional
    public void deleteUser(final String id) {
        userGateway.delete(id);
    }

    private UserResponse mapToUserResponse(final User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .bio(user.getBio())
                .build();
    }
}
