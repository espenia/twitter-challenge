package twitter.challenge.espenia.infra.gateway;

import org.springframework.stereotype.Component;
import twitter.challenge.espenia.core.domain.Follow;
import twitter.challenge.espenia.core.exception.NotFoundException;
import twitter.challenge.espenia.core.gateway.FollowGateway;
import twitter.challenge.espenia.infra.gateway.mapper.FollowMapper;
import twitter.challenge.espenia.infra.mongodb.document.FollowDocument;
import twitter.challenge.espenia.infra.mongodb.repository.FollowRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FollowGatewayImpl extends BaseMongoGateway<FollowDocument, String, FollowRepository> implements FollowGateway {
    
    private final FollowMapper followMapper;

    public FollowGatewayImpl(final FollowRepository repository, final FollowMapper followMapper) {
        super(repository);
        this.followMapper = followMapper;
    }

    @Override
    public Follow create(String followerId, String followedId) {
        FollowDocument followDocument = FollowDocument.builder()
                .followerId(followerId)
                .followedId(followedId)
                .build();
                
        return followMapper.toDomain(repository.save(followDocument));
    }

    @Override
    public void delete(String followerId, String followedId) {
        repository.deleteByFollowerIdAndFollowedId(followerId, followedId);
    }

    @Override
    public boolean exists(String followerId, String followedId) {
        return repository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }

    @Override
    public List<Follow> getFollowers(String userId) {
        return repository.findByFollowedId(userId)
                .stream()
                .map(followMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Follow> getFollowing(String userId) {
        return repository.findByFollowerId(userId)
                .stream()
                .map(followMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Follow findById(String id) {
        return repository.findById(id)
                .map(followMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Follow relationship not found with id: " + id));
    }
}
