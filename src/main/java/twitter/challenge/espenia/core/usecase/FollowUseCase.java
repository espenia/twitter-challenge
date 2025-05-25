package twitter.challenge.espenia.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitter.challenge.espenia.core.domain.Follow;
import twitter.challenge.espenia.core.exception.BadRequestException;
import twitter.challenge.espenia.core.gateway.FollowGateway;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.core.result.FollowResponse;
import twitter.challenge.espenia.core.usecase.request.FollowRequest;

@Component
@RequiredArgsConstructor
public class FollowUseCase {

    private final FollowGateway followGateway;
    private final UserGateway userGateway;

    @Transactional
    public FollowResponse execute(String followerId, FollowRequest request) {
        // Validate users exist
        userGateway.findById(followerId);
        userGateway.findById(request.followedId());
        
        // Prevent self-follow
        if (followerId.equals(request.followedId())) {
            throw new BadRequestException("Users cannot follow themselves");
        }
        
        // Check if already following
        if (followGateway.exists(followerId, request.followedId())) {
            return FollowResponse.from(Follow.builder()
                    .followedId(request.followedId())
                    .followerId(followerId)
                    .build());
        }
        
        // Create the follow relationship
        Follow follow = followGateway.create(followerId, request.followedId());
          return FollowResponse.from(follow);
    }
}
