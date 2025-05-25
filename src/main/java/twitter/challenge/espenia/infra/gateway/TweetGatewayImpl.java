package twitter.challenge.espenia.infra.gateway;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import twitter.challenge.espenia.core.domain.Tweet;
import twitter.challenge.espenia.core.exception.NotFoundException;
import twitter.challenge.espenia.core.gateway.TweetGateway;
import twitter.challenge.espenia.infra.gateway.mapper.TweetMapper;
import twitter.challenge.espenia.infra.mongodb.document.TweetDocument;
import twitter.challenge.espenia.infra.mongodb.repository.TweetRepository;

import java.util.List;
import java.util.stream.Collectors;

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
    
    @Override
    public Tweet findById(String id) {
        return repository.findById(id)
                .map(tweetMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Tweet not found with id: " + id));
    }
    
    @Override
    public List<Tweet> findByUserIdOrderByCreatedAtDesc(String userId, int limit) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(0, limit))
                .stream()
                .map(tweetMapper::toDomain)
                .toList();
    }
    
    @Override
    public List<Tweet> findByIds(List<String> tweetIds) {
        return repository.findByIdIn(tweetIds)
                .stream()
                .map(tweetMapper::toDomain)
                .toList();
    }
    
    @Override
    public List<Tweet> findByUserIdsOrderByCreatedAtDesc(List<String> userIds, int limit) {
        return repository.findByUserIdInOrderByCreatedAtDesc(userIds, PageRequest.of(0, limit))
                .stream()
                .map(tweetMapper::toDomain)
                .toList();
    }
}
