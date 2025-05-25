package twitter.challenge.espenia.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitter.challenge.espenia.core.gateway.FollowGateway;

@Component
@RequiredArgsConstructor
public class UnFollowUseCase {

    private final FollowGateway followGateway;

    @Transactional
    public void execute(String followerId, String followedId) {
        // Check if follow relationship exists
        if (!followGateway.exists(followerId, followedId)) {
            return;
        }
        followGateway.delete(followerId, followedId);
    }
}
