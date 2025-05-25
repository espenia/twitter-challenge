package twitter.challenge.espenia.core.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitter.challenge.espenia.core.gateway.FollowGateway;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.core.result.FollowResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindFollowersUseCase {

    private final FollowGateway followGateway;
    private final UserGateway userGateway;
    
    public List<FollowResponse> getFollowers(String userId) {
        // Validate user exists
        userGateway.findById(userId);
        
        return FollowResponse.from(followGateway.getFollowers(userId));
    }
      public List<FollowResponse> getFollowing(String userId) {
        // Validate user exists
        userGateway.findById(userId);
        
        return FollowResponse.from(followGateway.getFollowing(userId));
    }
}
