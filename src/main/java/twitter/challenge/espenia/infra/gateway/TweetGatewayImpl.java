package twitter.challenge.espenia.infra.gateway;

import org.springframework.stereotype.Component;
import twitter.challenge.espenia.core.domain.Tweet;
import twitter.challenge.espenia.core.gateway.TweetGateway;
import twitter.challenge.espenia.infra.gateway.mapper.TweetMapper;
import twitter.challenge.espenia.infra.mongodb.document.TweetDocument;
import twitter.challenge.espenia.infra.mongodb.repository.TweetRepository;

@Component
public class TweetGatewayImpl extends BaseMongoGateway<TweetDocument, String, TweetRepository> implements TweetGateway {

    private final TweetMapper tweetMapper;

    public TweetGatewayImpl(final TweetRepository repository, final TweetMapper tweetMapper) {
        super(repository);
        this.tweetMapper = tweetMapper;
    }

    @Override
    public Tweet create(Tweet tweet) {
        return tweetMapper.toDomain(repository.save(tweetMapper.toEntity(tweet)));
    }
}
