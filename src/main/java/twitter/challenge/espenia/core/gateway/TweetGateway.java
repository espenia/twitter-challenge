package twitter.challenge.espenia.core.gateway;

import twitter.challenge.espenia.core.domain.Tweet;

public interface TweetGateway {
    Tweet create(Tweet tweet);
}
