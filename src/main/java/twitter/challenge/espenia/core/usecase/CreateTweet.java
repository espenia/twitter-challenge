package twitter.challenge.espenia.core.usecase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitter.challenge.espenia.core.domain.Tweet;
import twitter.challenge.espenia.core.gateway.TweetGateway;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.core.result.TweetResponse;
import twitter.challenge.espenia.core.usecase.request.TweetRequest;

@Service
@RequiredArgsConstructor
public class CreateTweet {

    private final TweetGateway tweetGateway;
    private final UserGateway userGateway;

    @Transactional
    public TweetResponse execute(final TweetRequest tweetRequest) {
        // Verify user exists
        userGateway.findById(tweetRequest.userId());
        
        // Create tweet
        final Tweet tweet = Tweet.builder()
                .userId(tweetRequest.userId())
                .content(tweetRequest.content())
                .build();

        return mapToTweetResponse(tweetGateway.create(tweet));
    }

    private TweetResponse mapToTweetResponse(final Tweet tweet) {
        return TweetResponse.builder()
                .id(tweet.getId())
                .userId(tweet.getUserId())
                .content(tweet.getContent())
                .createdAt(tweet.getCreatedAt())
                .likeCount(tweet.getLikeCount())
                .build();
    }
}
