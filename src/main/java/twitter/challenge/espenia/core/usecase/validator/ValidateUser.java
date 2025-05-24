package twitter.challenge.espenia.core.usecase.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitter.challenge.espenia.core.exception.BadRequestException;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.core.usecase.request.UserRequest;

@RequiredArgsConstructor
@Component
public class ValidateUser {

    private final UserGateway userGateway;

    public void execute(UserRequest userRequest) {
        if (userGateway.existsByUsername(userRequest.username())) {
            throw new BadRequestException("Username already exists: " + userRequest.username());
        }

        if (userGateway.existsByEmail(userRequest.email())) {
            throw new BadRequestException("Email already exists: " + userRequest.email());
        }
    }

}
